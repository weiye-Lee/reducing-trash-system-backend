FROM hub.c.163.com/library/java:11
VOLUME /tmp
ADD target/recycle-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]