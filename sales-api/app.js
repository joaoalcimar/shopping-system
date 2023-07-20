import express from 'express';
import { connectMongoDB } from './src/config/db/mongoDBConfig.js';
import {createInitialData} from "./src/config/db/initialData.js";
import checkToken from "./src/config/auth/checkToken.js";
import {connectRabbitMQ} from "./src/config/rabbitmq/rabbitConfig.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connectMongoDB();
createInitialData();
connectRabbitMQ();

//app.use(checkToken);

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Sales API',
        status: "up",
        httpStatus: 200
    })
})

app.listen(PORT, () => {
    console.info(`Server started succcessfuly at port ${PORT}`);
})


