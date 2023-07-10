import * as httpStatus from "../../../config/constants/httpStatus.js";
import * as secrets from "../../../config/constants/secrets.js";
import UserRepository from "../repository/UserRepository.js";
import UserException from "../exception/UserException.js";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

class UserService {

    async findByEmail(req) {
        try{
            const {email} = req.params;
            this.validateRequestData(email);

            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);

            return {
                status: httpStatus.SUCCESS,
                user : {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                }
            }

        }catch (err){
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    validateRequestData(email){
        if(!email) {
            throw new UserException(
                httpStatus.BAD_REQUEST,
                "User email was no t informed."
            );
        }
    }

    validateUserNotFound(user) {
        if(!user){
            throw new UserException(
                httpStatus.NOT_FOUND,
                "User email was no t informed."
            );
        }
    }

    async getAccessToken(req) {
        try{
            const {email, password} = req.body;
            this.validateAccessTokenData(email,password);

            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);

            await this.validatePassword(password, user.password);
            let authUser = {id: user.id, name: user.name, email: user.email}
            const accessToken = jwt.sign({authUser}, secrets.apiSecret, {expiresIn: '1d'});

            return {
                status: httpStatus.SUCCESS,
                accessToken
            }
        }catch (err){
            return{
                status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: err.message
            };
        }

    }

    validateAccessTokenData(email, password) {
        if(!email || !password){
            throw new UserException(
                httpStatus.BAD_REQUEST,
                "Email and password must be informed.");
        }
    }

    async validatePassword(password, hashPassword) {
        if (!await bcrypt.compare(password, hashPassword)) {
            throw new UserException(
                httpStatus.UNAUTHORIZED,
                "Password does not match.");
        }

    }
}

export default new UserService();