import Order from "../../modules/sales/models/Order.js";

export async function createInitialData(){
    await Order.collection.drop();
    await Order.create({
        products: [
            {
                productId: 3,
                quantity: 2
            },
            {
                productId: 4,
                quantity: 1
            },
            {
                productId: 5,
                quantity: 1
            }
        ],
        user: {
            id: "sample",
            name: "User Test",
            email: "usertest@gmail.com",
        },
        status: "APPROVED",
        createdAt: new Date(),
        updatedAt: new Date()
    });
    await Order.create({
        products: [
            {
                productId: 1,
                quantity: 2
            },
            {
                productId: 3,
                quantity: 1
            }
        ],
        user: {
            id: "sample2",
            name: "User Test 2",
            email: "usertest2@gmail.com",
        },
        status: "REJECTED",
        createdAt: new Date(),
        updatedAt: new Date()
    });
}