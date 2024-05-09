FROM openjdk:17-jdk-alpine
RUN addgroup -S paymentchain && adduser -S admin -G paymentchain
USER admin:paymentchain
VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENV JAVA_OPTS=""
EXPOSE 8080:8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]