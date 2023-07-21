import axios from "axios";

import {PRODUCT_API_URL} from "../../../config/constants/secrets.js"

class ProductClient{
    async checkProductStock(productsData, token) {
        try{
            const headers = {
                Authorization: token,
            }
            console.info(`Sending request to Product API with data: ${JSON.stringify(productsData)}`);
            await axios.post(`${PRODUCT_API_URL/check-stock}`,{products: productsData} ,{headers})
                .then(res => {
                    return true;
                })
                .catch(err => {
                    console.error(err.response.message);
                    return false;
                })
        }catch (err) {
            return false;
        }
    }
}

export default new ProductClient();