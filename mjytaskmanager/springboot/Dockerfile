# 使用官方 OpenJDK 基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将本地的 jar 包复制到容器中
COPY target/springboot-0.0.1-SNAPSHOT.jar /app/springboot-0.0.1-SNAPSHOT.jar

# 暴露应用程序运行的端口（例如 Spring Boot 默认端口为8080）
EXPOSE 8080

# 启动应用程序
ENTRYPOINT ["java", "-jar", "/app/springboot-0.0.1-SNAPSHOT.jar"]