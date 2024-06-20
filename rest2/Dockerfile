FROM openjdk:17-jdk

VOLUME /tmp 
ARG JAVA_OPTS 
ENV JAVA_OPTS=$JAVA_OPTS 

# Copy the jar file to the working directory
COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]