# Stage 1: Tạo JDK tối ưu với Jlink
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /opt

# Định nghĩa rõ ràng $JAVA_HOME
ENV JAVA_HOME=/opt/java/openjdk

# Bổ sung đầy đủ các module cần thiết cho Spring và AOP
# Thay java.transaction bằng java.transaction.xa
RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.logging,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,\
java.xml,jdk.unsupported,java.compiler,java.transaction.xa,java.rmi,java.scripting,\
java.net.http,jdk.crypto.ec,jdk.jfr \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /opt/jdk

# Stage 2: Chạy ứng dụng với JDK tối ưu
FROM alpine:latest

# Cài đặt một số tiện ích cơ bản
RUN apk add --no-cache bash tzdata curl

# Sao chép JDK tối ưu từ stage trước
COPY --from=build /opt/jdk /opt/jdk

ENV JAVA_HOME=/opt/jdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Tạo thư mục cho ứng dụng
WORKDIR /app

# Sao chép file JAR của
COPY target/mymatchbackend-0.0.1-SNAPSHOT.jar app.jar

# Khai báo cổng
EXPOSE 8080

# Thiết lập entrypoint với tối ưu JVM và các cấu hình Spring AOP
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-XX:+UseSerialGC", "-Dspring.aop.proxy-target-class=true", "-Dspring.aop.auto=true", "-jar", "/app/app.jar"]
