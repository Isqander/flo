FROM gradle:7.6.1-jdk17-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

RUN ./gradlew dependencies

COPY . .

RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/your-project-all.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]