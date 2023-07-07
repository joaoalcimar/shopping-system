import User from "../../modules/user/model/User.js";
import {where} from "sequelize";

class UserRepository {

    async findById(id) {
        try {
            return await User.findOne({
                where: {id}
            })
        } catch (err) {
            console.error(err.message);
            return null;
        }
    }

    async findByEmail(email) {
        try {
            return await User.findOne({
                where: {email}
            })
        } catch (err) {
            //TODO improve error handling
            console.error(err.message);
            return null;
        }
    }
}

export default new UserRepository();