FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/ex6-0.0.1-SNAPSHOT.jar dufourj.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar dufourj.jar
ENV DATABASE_URL=jdbc:mysql://host.docker.internal:3306/DH_Destinations_Duf
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar dufourj.jar
