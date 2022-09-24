FROM openjdk:18

ENV APP_ROOT /application

RUN mkdir ${APP_ROOT}

WORKDIR ${APP_ROOT}

COPY target/*.jar ${APP_ROOT}/run.jar
COPY config ${APP_ROOT}/config/

ENTRYPOINT ["java", "-jar", "run.jar"]