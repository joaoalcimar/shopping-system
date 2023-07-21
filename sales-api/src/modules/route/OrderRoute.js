import {Router} from "express";
import OrderController from "../sales/controller/OrderController.js";

const router = new Router();

router.post('/api/order/create', OrderController.createOrder);
router.get('/api/orders', OrderController.findAll);
router.get('/api/orders/:id', OrderController.findById);
router.get('/api/orders/product/:productId', OrderController.findByProductId);

export default router;
