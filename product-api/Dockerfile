FROM maven:3.8.4-jdk-11
WORKDIR .
COPY ./ ./
RUN mvn clean install -DskipTests
EXPOSE 8081
CMD ["mvn", "spring-boot:run"]