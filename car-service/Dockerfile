FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/car-app.jar /opt/sharenow-coding-challenge/car-app.jar
EXPOSE 17080
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.redis.host=${CACHE_SERVICE_HOST}", "-Dspring.redis.port=${CACHE_SERVICE_PORT}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVICE_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVICE_URL}/eureka", "-Dspring.zipkin.baseUrl=${TRACING_SERVICE_URL}", "/opt/sharenow-coding-challenge/car-app.jar"]