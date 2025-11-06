# jdk17 Image Start
FROM eclipse-temurin:17-jdk

# build/libs 폴더 안의 "전체 의존성 포함 JAR"만 복사
COPY ./build/libs/*-SNAPSHOT.jar stamppaw_backend.jar

ENTRYPOINT ["java", "-jar", "/stamppaw_backend.jar"]