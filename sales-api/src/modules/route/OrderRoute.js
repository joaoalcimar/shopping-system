import {Router} from "express";
import OrderController from "../sales/controller/OrderController.js";

const router = new Router();

router.post('/api/order/create', OrderController.createOrder);
router.get('/api/order/:id', OrderController.findById);

export default router;
