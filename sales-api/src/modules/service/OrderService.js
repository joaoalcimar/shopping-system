import OrderRepository from "../repository/OrderRepository.js";
import {sendMessageToProductStockUpdateQueue} from "../product/rabbitmq/productStockUpdateSender.js";
import * as httpStatus from "../../config/constants/httpStatus.js";
import {APPROVED, PENDING, REJECTED} from "../status/OrderStatus.js";
import OrderException from "../exception/OrderException.js";
import orderRepository from "../repository/OrderRepository.js";

class OrderService {
    async createOrder(req) {
        try{
            let {orderData} = req.body;
            const {authUser} = req;

            this.validateOrder(orderData);

            let order = {
                products: orderData,
                user: authUser,
                status: PENDING,
                createdAt: new Date(),
                updatedAt: new Date()
            };

            let createdOrder = await orderRepository.save(order);
            sendMessageToProductStockUpdateQueue(createdOrder.products);

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

    validateOrder(data){
        if(!data || !data.products){
            throw new OrderException(httpStatus.BAD_REQUEST, "The products must be informed.");
        }
    }
}

export default new OrderService();