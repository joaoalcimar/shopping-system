FROM maven:3.8.4-openjdk-11
EXPOSE 8081
ARG JAR_FILE=target/product-api.jar
ADD ${JAR_FILE} product-api.jar
ENTRYPOINT ["java","-jar","/product-api.jar"]