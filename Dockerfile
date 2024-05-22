FROM azul/zulu-openjdk-alpine:17-latest
VOLUME /tmp
COPY target/hl7-echo-1.1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
VOLUME /tmp