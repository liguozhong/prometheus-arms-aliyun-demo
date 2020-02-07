FROM java:8
ADD ./target/prometheus_demo-0.0.1-SNAPSHOT.jar /root
EXPOSE 8081
EXPOSE 8080
WORKDIR /root
CMD ["java","-jar","prometheus_demo-0.0.1-SNAPSHOT.jar"]
