FROM java:8
WORKDIR /
ADD build/libs/rest-aggregator.jar rest-aggregator.jar
EXPOSE 8080
CMD java -jar rest-aggregator.jar