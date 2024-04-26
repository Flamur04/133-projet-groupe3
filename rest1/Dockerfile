FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENV DATABASE_URL=jdbc:mysql://mysql:3306/133ex6
COPY target/rest1-0.0.1-SNAPSHOT.jar 133projetgroupe3.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar 133projetgroupe3.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar 133projetgroupe3.jar
