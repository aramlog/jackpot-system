FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy JAR
COPY jackpot-app/target/jackpot-app.jar app.jar

# Match exposed container port to Spring Boot port
EXPOSE 8080

# Java options for memory control
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

