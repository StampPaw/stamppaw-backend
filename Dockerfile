# jdk17 Image Start
FROM eclipse-temurin:17-jdk

# build/libs 폴더 안의 빌드 결과 jar 복사
COPY build/libs/*-SNAPSHOT.jar /stamppaw_backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/stamppaw_backend.jar"]