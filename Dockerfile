FROM openjdk:11.0.3-jre

RUN mkdir /opt/moviechallenge

WORKDIR /opt/moviechallenge

COPY ./target/moviechallenge*.jar moviechallenge.jar

SHELL ["/bin/bash","-c"]

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT java ${ADDITIONAL_OPTS} -jar moviechallenge.jar
