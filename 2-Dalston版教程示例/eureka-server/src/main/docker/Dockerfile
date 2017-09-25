FROM dockerfile/java:oracle-java8

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/eureka-server.jar"]

ADD target/lib /usr/share/myservice/lib

ADD target/myservice.jar /usr/share/myservice/eureka-server.jar
