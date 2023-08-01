import axios from "axios";

import {PRODUCT_API_URL} from "../../../config/constants/secrets.js"

class ProductClient{
    async checkProductStock(productsData, token, transactionid) {
        let response = false;
        try{

            const headers = {
                Authorization: token,
                transactionid
            }

            console.info(`Sending request to Product API with data: ${JSON.stringify(productsData)}
            and transactionId: ${trasactionid})`);

            await axios.post(`${PRODUCT_API_URL/check-stock}`,{products: productsData} ,{headers})
                .then((res) => {
                    console.info(`Success response from Product-API. TransactionId ${transactionid}`);
                    response = true;
                })
                .catch(err => {
                    console.error(`Error response from Product-API. TransactionId ${transactionid}`);
                    response = false;
                })
        }catch (err) {
            console.error(`Error response from Product-API. TransactionId ${transactionid}`);
            return response;
        }
    }
}

export default new ProductClient();