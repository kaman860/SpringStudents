FROM openjdk:8-alpine
VOLUME /tmp
COPY students.jar students.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/students.jar"]
