FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/position-app.jar /opt/sharenow-coding-challenge/position-app.jar
EXPOSE 19080
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Dposs.polygon-service.url=${POLYGON_SERVICE_URL}", "-Dposs.car-service.url=${CAR_SERVICE_URL}", "-Dspring.cloud.config.uri=${CONFIGURATION_SERVER_URL}", "-Deureka.client.service-url.defaultZone=${NAMING_SERVER_URL}/eureka", "-Dspring.zipkin.baseUrl==${TRACING_SERVER_URL}", "/opt/sharenow-coding-challenge/position-app.jar"]