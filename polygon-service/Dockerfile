FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/polygon-app.jar /opt/sharenow-coding-challenge/polygon-app.jar
EXPOSE 18080
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dspring.data.mongodb.host=${DOCUMENTDB_SERVER_HOST}", "-Dspring.data.mongodb.port=${DOCUMENTDB_SERVER_PORT}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVER_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVER_URL}/eureka", "-Dspring.zipkin.baseUrl==${TRACING_SERVER_URL}", "/opt/sharenow-coding-challenge/polygon-app.jar"]