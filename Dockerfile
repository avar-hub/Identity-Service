FROM openjdk:17
EXPOSE 6969
ADD ./build/libs/Identity-Service-0.0.1-SNAPSHOT.jar Identity-Service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Identity-Service-0.0.1-SNAPSHOT.jar"]