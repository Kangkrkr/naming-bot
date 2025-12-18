# 1. 빌드 환경
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .

# [추가된 부분] gradlew 파일에 실행 권한 부여
RUN chmod +x ./gradlew

# 2. Gradle로 소스코드 빌드
RUN ./gradlew bootJar --no-daemon

# 3. 실행 환경
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# 4. 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]