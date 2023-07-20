import express from 'express';
import { connect } from './src/config/db/mongoDBConfig.js';
import {createInitialData} from "./src/config/db/initialData.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
createInitialData();

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth API',
        status: "up",
        httpStatus: 200
    })
})

app.listen(PORT, () => {
    console.info(`Server started succcessfuly at port ${PORT}`);
})


