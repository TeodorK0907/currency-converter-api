FROM gradle:8-jdk17
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

EXPOSE 8080

CMD ["java", "-jar", "/home/gradle/src/build/libs/currency-converter-api-0.0.1-SNAPSHOT.jar"]