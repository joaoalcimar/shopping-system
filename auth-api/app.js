import express from "express";
import { createInitialData } from "./src/config/db/initialData.js";
import userRoute from "./src/modules/user/route/UserRoute.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;


createInitialData();

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth API',
        status: "up",
        httpStatus: 200
    })
})

app.use(express.json());

app.use(userRoute);

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
})