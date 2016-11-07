FROM openjdk:8

COPY target/isheep-0.0.1-SNAPSHOT.jar /usr/src/isheep/isheep.jar

CMD ["java", "-jar" "/usr/src/isheep/isheep.jar"]