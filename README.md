# **Shopping System**
This is a comprehensive shopping system built using Java and JavaScript. The system utilizes Spring Boot as the framework, MongoDB and PostgreSQL as the databases, AWS as the cloud platform, and RabbitMQ and Docker to implement microservices architecture.

## **Features**
- User Authentication: Users can register, login, and manage their accounts.
- Product Catalog: Users can browse through a wide range of products available in the catalog.
- Shopping Cart: Users can add products to their cart and proceed to checkout.
- Order Management: Users can view and manage their orders.
- Payment Integration: Integration with a payment gateway for secure and convenient payments.
- Wishlist: Users can create and manage their wishlist.
- Reviews and Ratings: Users can read and write product reviews and ratings.
- Search Functionality: Users can search for products based on keywords or categories.
- Inventory Management: Inventory management for product stock and availability.
- Admin Panel: An administration panel for managing products, categories, and user accounts.

## **Technologies Used**
- Java
- JavaScript
- Spring Boot
- MongoDB
- PostgreSQL
- AWS (Amazon Web Services)
- RabbitMQ
- Docker

## **Getting Started**

### **Prerequisites**
Make sure you have the following software installed on your system:

- Java Development Kit (JDK)
- Node.js and NPM (Node Package Manager)
- Docker

### **Installation**
1. Clone the repository:   
    - git clone https://github.com/joaoalcimar/shopping-system.git

  
2. Backend Setup:
    - Open the backend directory.
    - Configure the database connection settings in the application.properties file.
    - Build and run the Spring Boot application.


3. Frontend Setup:
    - Open the frontend directory.
    - Install the required dependencies by running the following command:
    - Configure the backend API endpoint in the .env file.
    - Start the frontend application:
    - npm start

  
4. Microservices Setup:
    - Open the microservices directory.
    - Each microservice is contained in a separate directory. Navigate to each microservice directory and follow the respective instructions in their README files.


5. Deployment:
    - Deploy the application to AWS using the provided deployment scripts or by following the AWS documentation.
  
### **Local Running**

1. Go through shopping-system directory on CMD
2. Run docker-compose up --build
   - notes:
       - Containers **must** be linux
       - Ports 5433, 5434, 5672, 8080, 8081, 8082, 15672, 25676, 27017, 28017 must be free

## **Contributing**
Contributions are welcome! If you have any ideas or improvements for the project, please submit a pull request. Make sure to follow the existing code style and provide detailed information about the changes you made.

## **Contact**
For any questions or inquiries, please contact joao.alcimar.junior@gmail.com.
