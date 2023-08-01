# Endpoints Documentation

* The Auth-API application has only one endpoint, which is for authentication.
* The Product-API application has 3 modules with several endpoints for products, categories, and suppliers.
* The Sales-API application has only 4 endpoints.

**Obs.: All endpoints of the Product-API and Sales-API services require the authorization and transactionid headers.**

Required headers example:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoVXNlciI6eyJpZCI6MSwibmFtZSI6IlVzZXIgVGVzdCAxIiwiZW1haWwiOiJ0ZXN0ZXVzZXIxQGdtYWlsLmNvbSJ9LCJpYXQiOjE2MzM3OTk5MzUsImV4cCI6MTYzMzg4NjMzNX0.2AWPeoHSYUW_nGeLsx6rEOhm99ZfNZ8pQXPTJ0fwbDU
transactionid: 843e5420-e767-45f3-aee3-ca9a16233352
```

If the transactionid is not sent, the response will be:

```json
{
    "status": 400,
    "message": "The transactionid header is required."
}
```

# Auth-API

## Base URL: http://localhost:8080

### **POST** - **/api/user/auth** - Generates an access token

Headers:
```
Content-Type: application/json
```

Body:

```json
{
    "email": "testeuser1@gmail.com",
    "password": "123456"
}
```

Response:

```json
{
    "status": 200,
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoVXNlciI6eyJpZCI6MSwibmFtZSI6IlVzZXIgVGVzdCAxIiwiZW1haWwiOiJ0ZXN0ZXVzZXIxQGdtYWlsLmNvbSJ9LCJpYXQiOjE2MzM3OTk5MzUsImV4cCI6MTYzMzg4NjMzNX0.2AWPeoHSYUW_nGeLsx6rEOhm99ZfNZ8pQXPTJ0fwbDU"
}
```

If the token is not sent, the response will be:

```json
{
    "status": 401,
    "message": "The access token was not informed."
}
```

If an invalid token is not sent, the response will be:

```json
{
    "status": 401,
    "message": "Error while trying to proccess the Access Token."
}
```

# Product-API

## Base URL: http://localhost:8081

---

## Products Module

### **POST** - **/api/product** - Creates a new product

Body:

```json
{
    "name": "Scream",
    "categoryId": 1,
    "supplierId": 2,
    "availableQuantity": 2
}
```

Response:

```json
{
    "id": 1,
    "name": "Scream",
    "supplier": {
        "id": 2,
        "name": "Amazon"
    },
    "category": {
        "id": 1,
        "description": "Movie"
    },
    "quantity_available": 2,
    "created_at": "09/10/2021 16:38:43"
}
```

### **PUT** - **/api/product/{id}** - Update an product

Body:

```json
{
    "name": "Scream",
    "quantity_available": 2,
    "supplierId": 2,
    "categoryId": 1
}
```

Response (id parameter = 2):

```json
{
    "id": 2,
    "name": "Scream",
    "supplier": {
        "id": 2,
        "name": "Amazon"
    },
    "category": {
        "id": 1,
        "description": "Movie"
    },
    "quantity_available": 2,
    "created_at": null
}
```

### **DELETE** - **/api/product/{id}** - Delete an product

Response (id parameter = 2):

```json
{
    "status": 200,
    "message": "The product was deleted."
}
```

### **GET** - **/api/product** - Returns all products

Response:

```json
[
  {
    "id": 1,
    "name": "Scream",
    "supplier": {
      "id": 2,
      "name": "Amazon"
    },
    "category": {
      "id": 1,
      "description": "Movie"
    },
    "quantity_available": 2,
    "created_at": "09/10/2021 16:38:43"
  },
  {
    "id": 2,
    "name": "Barbie",
    "supplier": {
      "id": 1,
      "name": "Magalu"
    },
    "category": {
      "id": 1,
      "description": "Movie"
    },
    "quantity_available": 5,
    "created_at": "09/10/2021 17:38:43"
  }
]
```

### **GET** - **/api/product/{id}** - Search product by id

Response (id parameter = 1):

```json
{
  "id": 1,
  "name": "Scream",
  "supplier": {
    "id": 2,
    "name": "Amazon"
  },
  "category": {
    "id": 1,
    "description": "Movie"
  },
  "quantity_available": 2,
  "created_at": "09/10/2021 16:38:43"
}
```

### **GET** - **/api/product/name/{name}** - Search product by name(insensitive case/similar result)

Response (parameter name = bar):

```json
[
  {
    "id": 2,
    "name": "Barbie",
    "supplier": {
      "id": 1,
      "name": "Magalu"
    },
    "category": {
      "id": 1,
      "description": "Movie"
    },
    "quantity_available": 5,
    "created_at": "09/10/2021 17:38:43"
  }
]
```

### **GET** - **/api/product/category/{categoryId}** - Search product by category id

Response (id parameter = 1):

```json
[
  {
    "id": 1,
    "name": "Scream",
    "supplier": {
      "id": 2,
      "name": "Amazon"
    },
    "category": {
      "id": 1,
      "description": "Movie"
    },
    "quantity_available": 2,
    "created_at": "09/10/2021 16:38:43"
  }
]
```

### **GET** - **/api/product/supplier/{supplierId}** - Search product by supplier id

Response (parameter supplierId = 2):

```json
[
  {
    "id": 1,
    "name": "Scream",
    "supplier": {
      "id": 2,
      "name": "Amazon"
    },
    "category": {
      "id": 1,
      "description": "Movie"
    },
    "quantity_available": 2,
    "created_at": "09/10/2021 16:38:43"
  }
]
```


### **POST** - **/api/product/check-stock** - Check stock of an array of products

Body:

```json
{
    "products": [
        {
            "productId": 1,
            "quantity": 1
        },
        {
            "productId": 2,
            "quantity": 1
        },
        {
            "productId": 3,
            "quantity": 1
        }
    ]
}
```

Response:

```json
{
    "status": 200,
    "message": "The stock is ok!"
}
```

### **GET** - **/api/product/{productId}/sales** - Retrieve the product with all the IDs of the orders placed for it.

Response:

```json
{
    "id": 1001,
    "name": "Scream",
    "supplier": {
        "id": 2,
        "name": "Amazon"
    },
    "category": {
        "id": 1,
        "description": "Movie"
    },
    "sales": [
        "6161cd32560fbede60d48efc",
        "6161cd32560fbede60d48efe",
        "6161d007560fbede60d48f01"
    ],
    "quantity_available": 3,
    "created_at": "09/10/2021 14:11:15"
}
```

---

## Supplier Module

### **POST** - **/api/supplier** - Creates a new supplier

Body:

```json
{
    "name": "Amazon"
}
```

Response:

```json
{
    "id": 1,
    "name": "Amazon"
}
```

### **PUT** - **/api/supplier/{id}** - Updates a supplier

Body:

```json
{
    "name": "Amazon BR"
}
```

Response (id parameter = 1):

```json
{
    "id": 1,
    "name": "Amazon BR"
}
```

### **DELETE** - **/api/supplier/{id}** - Deletes a supplier

Response (id parameter = 1):

```json
{
    "status": 200,
    "message": "The supplier was deleted."
}
```

### **GET** - **/api/supplier** - Returns all suppliers

Response:

```json
[
    {
        "id": 1,
        "name": "Amazon"
    },
    {
        "id": 2,
        "name": "Magalu"
    }
]
```

### **GET** - **/api/supplier/{id}** - Returns a supplier by id

Response (id parameter = 2):

```json
{
    "id": 2,
    "name": "Magalu"
}
```

### **GET** - **/api/supplier/name/{name}** - Returns a supplier by name(insensitive case/similar result)

Response (name parameter = ama):

```json
[
    {
        "id": 1,
        "name": "Amazon"
    }
]
```

---

## Category Module

### **POST** - **/api/category** - Creates a new category

Body:

```json
{
    "description": "Comic Books"
}
```

Response:

```json
{
    "id": 3,
    "description": "Comic Books"
}
```

### **PUT** - **/api/category/{id}** - Updates a new category

Body:

```json
{
    "description": "Comics"
}
```

Response (id parameter = 3):

```json
{
    "id": 3,
    "name": "Comics"
}
```

### **DELETE** - **/api/category/{id}** - Deletes a category

Response (id parameter = 2):

```json
{
    "status": 200,
    "message": "The category was deleted."
}
```

### **GET** - **/api/category** - Retrieve all categories

Response:

```json
[
    {
        "id": 1,
        "description": "Movie"
    },
    {
        "id": 2,
        "description": "Comic Books"
    },
    {
        "id": 3,
        "description": "Books"
    }
]
```

### **GET** - **/api/category/{id}** - Returns a category by id

Response (id parameter = 1000):

```json
{
    "id": 2,
    "description": "Comic Books"
}
```

### **GET** - **/api/category/description/{description}** - Returns a category by description(insensitive case/similar result)

Response (description parameter = book):

```json
[
    {
        "id": 2,
        "description": "Comic Books"
    },
    {
        "id": 3,
        "description": "Books"
    }
]
```

# Sales-API

## Base URL: http://localhost:8082


### **POST** - **/api/order/create** - Creates an order

Body:
```json
{
  "products": [
    {
      "productId": 1,
      "quantity": 1
    },
    {
      "productId": 2,
      "quantity": 1
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

Response:

```json
{
    "status": 200,
    "createdOrder": {
        "products": [
            {
                "productId": 1,
                "quantity": 1
            },
            {
                "productId": 2,
                "quantity": 1
            },
            {
                "productId": 3,
                "quantity": 1
            }
        ],
        "user": {
            "id": 1,
            "name": "User Test 1",
            "email": "testeuser1@gmail.com"
        },
        "status": "PENDING",
        "createdAt": "2021-10-09T17:23:18.450Z",
        "updatedAt": "2021-10-09T17:23:18.450Z",
        "transactionid": "7eb42d2e-1a37-45e7-b950-a0a0a9b78fb7",
        "serviceid": "8cbf358c-19f0-4a37-ac6e-6c09ec9e8f13",
        "_id": "6161d007560fbede60d48f01",
        "__v": 0
    }
}
```

### **GET** - **/api/orders** - Retrieves all orders

Response:

```json
{
    "status": 200,
    "orders": [
        {
            "_id": "6161cd32560fbede60d48efc",
            "products": [
                {
                    "productId": 1,
                    "quantity": 2
                },
                {
                    "productId": 2,
                    "quantity": 1
                },
                {
                    "productId": 3,
                    "quantity": 1
                }
            ],
            "user": {
                "id": "a1sd1as5d165ads1s6",
                "name": "User Test",
                "email": "usertest@gmail.com"
            },
            "status": "APPROVED",
            "createdAt": "2021-10-09T17:11:14.453Z",
            "updatedAt": "2021-10-09T17:11:14.453Z",
            "transactionid": "7eb42d2e-1a37-45e7-b950-a0a0a9b78fb7",
            "serviceid": "8cbf358c-19f0-4a37-ac6e-6c09ec9e8f13",
            "__v": 0
        },
        {
            "_id": "6161cd32560fbede60d48efe",
            "products": [
                {
                    "productId": 1,
                    "quantity": 4
                },
                {
                    "productId": 2,
                    "quantity": 2
                }
            ],
            "user": {
                "id": "asd1as9d1asd1asd1as5d",
                "name": "User Test 2",
                "email": "usertest2@gmail.com"
            },
            "status": "REJECTED",
            "createdAt": "2021-10-09T17:11:14.489Z",
            "updatedAt": "2021-10-09T17:11:14.489Z",
            "transactionid": "7eb42d2e-1a37-45e7-b950-a0a0a9b78fb7",
            "serviceid": "8cbf358c-19f0-4a37-ac6e-6c09ec9e8f13",
            "__v": 0
        }
    ]
}
```

### **GET** - **/api/order/{orderId}** - Returns an order by id

Response (orderId parameter = 6161cd32560fbede60d48efc):

```json
{
    "status": 200,
    "existingOrder": {
        "_id": "6161cd32560fbede60d48efc",
        "products": [
            {
                "productId": 1,
                "quantity": 2
            },
            {
                "productId": 2,
                "quantity": 1
            },
            {
                "productId": 3,
                "quantity": 1
            }
        ],
        "user": {
            "id": "a1sd1as5d165ads1s6",
            "name": "User Test",
            "email": "usertest@gmail.com"
        },
        "status": "APPROVED",
        "createdAt": "2021-10-09T17:11:14.453Z",
        "updatedAt": "2021-10-09T17:11:14.453Z",
        "transactionid": "7eb42d2e-1a37-45e7-b950-a0a0a9b78fb7",
        "serviceid": "8cbf358c-19f0-4a37-ac6e-6c09ec9e8f13",
        "__v": 0
    }
}
```

### **GET** - **/api/orders/products/{productId}** - Retrieve all orders of a product

Response (productId parameter = 1):

```json
{
    "status": 200,
    "salesIds": [
        "6161cd32560fbede60d48efc",
        "6161cd32560fbede60d48efe",
        "6161d007560fbede60d48f01"
    ]
}
```