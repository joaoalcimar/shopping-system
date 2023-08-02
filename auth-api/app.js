import express from "express";
import { createInitialData } from "./src/config/db/initialData.js";
import userRoute from "./src/modules/user/route/UserRoute.js";
import tracing from "./src/config/tracing/tracing.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;
const CONTAINER_ENV = "container";

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth API',
        status: "up",
        httpStatus: 200
    })
})

app.use(express.json());

startApplicationData();

function startApplicationData() {
    if(env.NODE_ENV !== CONTAINER_ENV){
        createInitialData();
    }
}

app.post("/api/initial-data", (req, res) => {
    createInitialData();
    return res.json({ message: "Data created." });
});

app.use(tracing);
app.use(userRoute);

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
})