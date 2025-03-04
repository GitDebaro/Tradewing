FROM eclipse-temurin:23-jdk

# Set the Current Working Directory inside the container
WORKDIR /app

COPY . .

# Build the Go app
RUN ./mvnw clean package

FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
# This container exposes port 8080 to the outside world
EXPOSE 8080

# Run the binary program produced by `go build`
ENTRYPOINT ["java","-jar","app.jar"]
