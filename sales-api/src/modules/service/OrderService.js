import OrderRepository from "../repository/OrderRepository.js";
import {sendMessageToProductStockUpdateQueue} from "../product/rabbitmq/productStockUpdateSender.js";
import * as httpStatus from "../../config/constants/httpStatus.js";
import {APPROVED, PENDING, REJECTED} from "../status/OrderStatus.js";
import OrderException from "../exception/OrderException.js";
import {BAD_REQUEST, NOT_FOUND} from "../../config/constants/httpStatus.js";
import ProductClient from "../product/client/ProductClient.js";

class OrderService {
    async createOrder(req) {
        try{
            let orderData = req.body;

            const {transactionid, serviceid} = req.headers;
            console.info(
                `Request to POST new order with data ${JSON.stringify(orderData)} 
                | [transactionalId: ${transactionid} 
                | serviceId: ${serviceid}]`
            );

            const {authUser} = req;
            const {authorization} = req.headers;

            this.validateOrder(orderData);

            let order =
                this.createInitialOrderData(orderData, authUser, transactionid, serviceid);

            await this.validateProductStock(order, authorization, transactionid);

            let createdOrder = await OrderRepository.save(order);

            this.sendMessage(createdOrder);

            let response = {
                status: httpStatus.SUCCESS,
                createdOrder
            }

            console.info(
                `Request to POST new order with data ${JSON.stringify(response)} 
                | transactionalId: ${transactionid} 
                | serviceId: ${serviceid}`
            );
            return response;

        }catch (err) {
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    createInitialOrderData(orderData, authUser, transactionid, serviceid){
        return {
            products: orderData.products,
            user: authUser,
            status: PENDING,
            createdAt: new Date(),
            updatedAt: new Date(),
            transactionid: transactionid,
            serviceid: serviceid
        };
    }

    async updateOrder(orderMessage){
        try{

            const order = JSON.parse(orderMessage);
            if (order.salesId && order.status){

                let existingOrder = await OrderRepository.findById(order.salesId);
                existingOrder.updatedAt = new Date();

                if(existingOrder && order.status !== existingOrder.status){
                    existingOrder.status = order.status;
                    await OrderRepository.save(existingOrder);
                }
            }else{
                console.warn("The order message was not complete.");
            }

        }catch (err) {
            console.error("Could not parse order message from queue");
            console.error(err.message);
        }
    }

    validateOrder(data){
        if(!data || !data.products){
            throw new OrderException(httpStatus.BAD_REQUEST, "The products must be informed.");
        }
    }

    async validateProductStock(order, token, transactionid){
        let isProductStockAvailable = await ProductClient.checkProductStock(order, token, transactionid);
        if(isProductStockAvailable){
            throw new OrderException(BAD_REQUEST, 'The stock is out for the products.');
        }
    }

    sendMessage(createdOrder){
        const message = {
            salesId: createdOrder.id,
            products: createdOrder.products
        }
        sendMessageToProductStockUpdateQueue(message);

    }

    async findAll(req){
        try{

            const {transactionid, serviceid} = req.headers;
            console.info(
                `Request to GET all orders
                | [transactionalId: ${transactionid} 
                | serviceId: ${serviceid}]`
            );

            const allOrders = await OrderRepository.findAll();

            if(!allOrders){
                throw new OrderException(NOT_FOUND, "No orders were not found.");
            }

            console.info(
                `Request to GET all orders
                | transactionalId: ${transactionid} 
                | serviceId: ${serviceid}`
            );

            let response = {
                status: httpStatus.SUCCESS,
                allOrders
            }

            console.info(
                `Request to GET all orders
                | ${JSON.stringify(response)}
                | transactionalId: ${transactionid} 
                | serviceId: ${serviceid}`
            );

            return response;

        }catch (err) {
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    async findById(req){

        try{
            const {id} = req.params;

            const {transactionid, serviceid} = req.headers;
            console.info(
                `Request to GET an order by id: ${id}
                | [transactionalId: ${transactionid} 
                | serviceId: ${serviceid}]`
            );

            this.validateInformedId(id);
            const existingOrder = await OrderRepository.findById(id);

            if(!existingOrder){
                throw new OrderException(NOT_FOUND, "The order was not found.");
            }

            let response = {
                status: httpStatus.SUCCESS,
                existingOrder
            }

            console.info(
                `Request to GET an order by id: ${id}
                | ${JSON.stringify(response)}
                | transactionalId: ${transactionid} 
                | serviceId: ${serviceid}`
            );

            return response;

        }catch (err) {
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    async findByProductId(req){

        try{
            const {productId} = req.params;

            const {transactionid, serviceid} = req.headers;
            console.info(
                `Request to GET an order by product id: ${productId}
                | [transactionalId: ${transactionid} 
                | serviceId: ${serviceid}]`
            );

            this.validateInformedId(productId);
            const orders = await OrderRepository.findByProductId(productId);

            if(!orders){
                throw new OrderException(NOT_FOUND, "The product was not found.");
            }

            let response = {
                status: httpStatus.SUCCESS,
                salesIds: orders.map((order) => {
                    return order.id;
                })
            }

            console.info(
                `Request to GET an order by product id: ${productId}
                | ${JSON.stringify(response)}
                | transactionalId: ${transactionid} 
                | serviceId: ${serviceid}`
            );

            return response;

        }catch (err) {
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    validateInformedId(id){
        if(!id){
            throw new OrderException(BAD_REQUEST, "The id must be informed");
        }
    }
}

export default new OrderService();