FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/polling-app.jar /opt/sharenow-coding-challenge/polling-app.jar
EXPOSE 16080
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.redis.host=${CACHE_SERVICE_HOST}", "-Dspring.redis.port=${CACHE_SERVICE_PORT}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVICE_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVICE_URL}/eureka", "-Dcar2go.base.url=${CAR2GO_SERVICE_URL}", "/opt/sharenow-coding-challenge/polling-app.jar"]