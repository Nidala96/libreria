# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-jammy

RUN apt-get update
RUN apt-get -y install git
RUN git clone https://github.com/Nidala96/libreria.git
WORKDIR /libreria
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

CMD ["./mvnw", "spring-boot:run"]
