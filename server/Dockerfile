FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon -x test
CMD ["java", "-jar", "build/libs/server-0.0.1-SNAPSHOT.jar"]
