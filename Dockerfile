FROM openjdk:17-alpine
COPY target/parking-management-system-0.0.1-SNAPSHOT.jar parking-management-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","parking-management-system-0.0.1-SNAPSHOT.jar"]