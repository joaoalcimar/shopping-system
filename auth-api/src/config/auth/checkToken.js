import * as httpStatus from "../constants/httpStatus.js"
import AuthException from "./AuthException.js";
import * as secrets from "../constants/secrets.js";
import jwt from "jsonwebtoken";
import {promisify} from "util";

const empty = " ";

export default async (req, res, next) => {
    try{
        let {authorization} = req.headers;

        if(!authorization){
            throw new AuthException(
                httpStatus.UNAUTHORIZED,
                "Access token was not informed.");
        }

        let accessToken = authorization;

        // bearer token pattern
        if(accessToken.includes(empty)){
            accessToken = authorization.split(empty)[1];
        }

        const decoded = await promisify(jwt.verify)(
            accessToken,
            secrets.apiSecret
        );

        req.authUser = decoded.authUser;
        return next();
    }catch (err){
        const status = err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR;
        return res.status(status).json({
            status,
            message : err.message
        });
    }
}