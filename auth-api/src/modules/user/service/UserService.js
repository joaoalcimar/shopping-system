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

            this.validateAuthenticatedUser(user, req.authUser);

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
                "User email was not informed."
            );
        }
    }

    validateUserNotFound(user) {
        //TODO change architecture to user being incapable of discover registered emails
        if(!user){
            throw new UserException(
                httpStatus.NOT_FOUND,
                "Invalid user email."
            );
        }
    }

    validateAuthenticatedUser(user, authUser){
        if(!authUser || user.id !== authUser.id){
            throw new UserException(
                httpStatus.FORBIDDEN,
                "You are not authorized to see this data."
            );
        }
    }

    async getAccessToken(req) {
        try{

            const {transactionid, serviceid} = req.headers;
            console.info(
                `Request to POST Login with data ${JSON.stringify(req.body)} 
                | [transactionalId: ${transactionid} 
                | serviceId: ${serviceid}]`
            );

            const {email, password} = req.body;
            this.validateAccessTokenData(email,password);

            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);

            await this.validatePassword(password, user.password);
            let authUser = {id: user.id, name: user.name, email: user.email}
            const accessToken = jwt.sign({authUser}, secrets.API_SECRET, {expiresIn: '1d'});

            let response = {
                status: httpStatus.SUCCESS,
                accessToken
            }

            console.info(
                `Request to POST Login with data ${JSON.stringify(response)} 
                | transactionalId: ${transactionid} 
                | serviceId: ${serviceid}`
            );
            return response;
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