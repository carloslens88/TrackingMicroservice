FROM openjdk:11

ARG JAVA_OPTS

ENV JAVA_OPTS=${JAVA_OPTS}

COPY /target/tracking-microservice-0.0.1-SNAPSHOT.jar /app/tms-api.jar

CMD ["sh", "-c", "java ${JAVA_OPTS} -jar /app/tms-api.jar"]