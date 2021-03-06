FROM adoptopenjdk/openjdk11:ubi
WORKDIR /opt/app
COPY . .
RUN ./mvnw package
EXPOSE 8080
CMD ["java", "-jar", "target/saveup-backend-1.0.0-SNAPSHOT.jar"]