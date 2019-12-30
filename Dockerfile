FROM maven:3.6.3-jdk-11 AS build  
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java:11
#Should make jar name smaller...  
COPY --from=build /usr/src/app/target/twentyone-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/twentyone-1.0-SNAPSHOT-jar-with-dependencies.jar  
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/twentyone-1.0-SNAPSHOT-jar-with-dependencies.jar"]  

