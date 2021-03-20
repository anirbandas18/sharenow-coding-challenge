FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/polygon-app.jar /opt/sharenow-coding-challenge/polygon-app.jar
EXPOSE 18080
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.data.mongodb.host=${DOCUMENTDB_SERVICE_HOST}", "-Dspring.data.mongodb.port=${DOCUMENTDB_SERVICE_PORT}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVICE_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVICE_URL}/eureka", "-Dspring.zipkin.baseUrl=${TRACING_SERVICE_URL}", "/opt/sharenow-coding-challenge/polygon-app.jar"]