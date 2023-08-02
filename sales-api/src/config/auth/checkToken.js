import { UNAUTHORIZED, INTERNAL_SERVER_ERROR} from "../constants/httpStatus.js";
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
                UNAUTHORIZED,
                "Access token was not informed.");
        }

        let accessToken = authorization;

        // bearer token pattern
        if(accessToken.includes(empty)){
            accessToken = authorization.split(empty)[1];
        }

        const decoded = await promisify(jwt.verify)(
            accessToken,
            secrets.API_SECRET
        );

        req.authUser = decoded.authUser;
        return next();
    }catch (err){
        const status = err.status ? err.status : INTERNAL_SERVER_ERROR;
        return res.status(status).json({
            status,
            message : err.message
        });
    }
}