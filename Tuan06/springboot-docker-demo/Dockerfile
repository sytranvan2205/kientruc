# define base docker image
FROM openjdk:11
LABEL maintainer = "javaguides.net"
ADD target/springboot-docker-demo-0.0.1-SNAPSHOT.jar springboot-docker-demo.jar
ENTRYPOINT ["java","-jar","springboot-docker-demo.jar"]