import OrderRepository from "../repository/OrderRepository.js";
import {sendMessageToProductStockUpdateQueue} from "../product/rabbitmq/productStockUpdateSender.js";
import * as httpStatus from "../../config/constants/httpStatus.js";
import {APPROVED, PENDING, REJECTED} from "../status/OrderStatus.js";
import OrderException from "../exception/OrderException.js";
import orderRepository from "../repository/OrderRepository.js";
import {BAD_REQUEST} from "../../config/constants/httpStatus.js";
import ProductClient from "../product/client/ProductClient.js";

class OrderService {
    async createOrder(req) {
        try{
            let {orderData} = req.body;
            const {authUser} = req;
            const {authorization} = req.headers;

            this.validateOrder(orderData);

            let order = this.createInitialOrderData(orderData, authUser);

            await this.validateProductStock(order, authorization);

            let createdOrder = await orderRepository.save(order);

            this.sendMessage(createdOrder);

            return {
                status: httpStatus.SUCCESS,
                createdOrder
            }
            
        }catch (err) {
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    createInitialOrderData(orderData, authUser){
        return {
            products: orderData,
            user: authUser,
            status: PENDING,
            createdAt: new Date(),
            updatedAt: new Date()
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

    async validateProductStock(order, token){
        let stockIsOut = await ProductClient.checkProductStock(order, token);
        if(stockIsOut){
            throw new OrderException(BAD_REQUEST, 'The stock is out for the products.');
        }
    }

    sendMessage(createdOrder){
        const message = {
            salesId: createdOrder.salesId,
            products: createdOrder.products
        }
        sendMessageToProductStockUpdateQueue(message);

    }
}

export default new OrderService();