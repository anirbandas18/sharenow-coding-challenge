FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/polling-app.jar /opt/sharenow-coding-challenge/polling-app.jar
EXPOSE 16080
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.redis.host=${CACHE_SERVER_HOST}", "-Dspring.redis.port=${CACHE_SERVER_PORT}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVER_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVER_URL}/eureka", "-Dcar2go.vehicles.by.location.uri=${CAR2GO_SERVER_URL}", "/opt/sharenow-coding-challenge/polling-app.jar"]