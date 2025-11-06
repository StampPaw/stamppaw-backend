FROM eclipse-temurin:17-jdk

# ✅ 작업 디렉토리 생성
WORKDIR /app

# ✅ JAR 파일을 /app으로 복사
COPY build/libs/*-SNAPSHOT.jar /app/stamppaw_backend.jar

EXPOSE 8080

# ✅ /app 안의 jar 실행
ENTRYPOINT ["java", "-jar", "/app/stamppaw_backend.jar"]