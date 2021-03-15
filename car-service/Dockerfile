FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdip ~p /opt/sharenow-coding-challenge
COPY target/vehicle-car.jar /opt/sharenow-coding-challenge/car-app.jar
CMD ["java","-jar","/opt/sharenow-coding-challenge/car-app.jar"]