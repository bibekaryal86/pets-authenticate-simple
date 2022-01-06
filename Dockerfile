FROM openjdk:11-jre-slim-bullseye
RUN adduser --system --group springdocker
USER springdocker:springdocker
ARG JAR_FILE=app/build/libs/pets-authenticate-simple.jar
COPY ${JAR_FILE} pets-authenticate.jar
ENTRYPOINT ["java","-jar", \
#"-DSPRING_PROFILES_ACTIVE=docker", \
#"-DTZ=America/Denver", \
#"-DBASIC_AUTH_USR_PETSSERVICE=some_username", \
#"-DBASIC_AUTH_PWD_PETSSERVICE=some_password", \
#"-DSECRET_KEY=some_secret_key", \
"/pets-authenticate.jar"]
# Environment variables to be prdvided in docker-compose
