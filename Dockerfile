FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/vocabulary-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.profiles.active=docker"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
