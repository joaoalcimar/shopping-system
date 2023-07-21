import {Router} from "express";
import OrderController from "../sales/controller/OrderController.js";
import checkToken from "../../config/auth/checkToken.js";

const router = new Router();

router.post('/api/order/create', OrderController.createOrder);
router.get('/api/order/:id', OrderController.findById);

export default router;
