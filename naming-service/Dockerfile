FROM adoptopenjdk:11-jre-hotspot-focal
MAINTAINER Anirban Das <anirbandas18@live.com>
RUN mkdir ~p /opt/sharenow-coding-challenge
COPY target/naming-app.jar /opt/sharenow-coding-challenge/naming-app.jar
EXPOSE 8761
CMD ["java", "-jar", "/opt/sharenow-coding-challenge/naming-app.jar"]