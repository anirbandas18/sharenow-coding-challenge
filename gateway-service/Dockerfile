FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/gateway-app.jar /opt/sharenow-coding-challenge/gateway-app.jar
EXPOSE 8081
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVER_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVER_URL}/eureka", "/opt/sharenow-coding-challenge/gateway-app.jar"]