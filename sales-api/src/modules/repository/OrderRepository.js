import Order from "../sales/models/Order.js";

class OrderRepository {

    async save(order){
        try{
            return await Order.create(order);
        } catch (error) {
            console.error(error.message);
            return null;
        }
    }

    async findById(id){
        try{
            return await Order.findById(id);
        } catch (error) {
            console.error(error.message);
            return null;
        }
    }

    async findAll(){
        try{
            return await Order.findAll();
        } catch (error) {
            console.error(error.message);
            return null;
        }
    }




}

export default new OrderRepository();