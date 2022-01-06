FROM openjdk:11-jre-slim-bullseye
RUN adduser --system --group springdocker
USER springdocker:springdocker
ARG JAR_FILE=app/build/libs/pets-authenticate-simple.jar
COPY ${JAR_FILE} pets-authenticate.jar
ENTRYPOINT ["java","-jar", \
"/pets-authenticate.jar"]
# Environment variables to be prdvided in docker-compose
