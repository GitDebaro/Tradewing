FROM eclipse-temurin:23-jdk AS builder

# Set the Current Working Directory inside the container
WORKDIR /app

COPY . .

# clean up the file
RUN sed -i 's/\r$//' mvnw
# run with the SH path
RUN /bin/sh mvnw dependency:resolve

FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
# This container exposes port 8080 to the outside world
EXPOSE 8080

# Run the binary program produced by `go build`
ENTRYPOINT ["java","-jar","app.jar"]
