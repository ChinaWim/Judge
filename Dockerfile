FROM hub.c.163.com/library/java:8-alpine

MAINTAINER m969130721@163.com

ADD target/*.jar judge.jar

EXPOSE 9081

ENTRYPOINT ["java", "-jar", "/judge.jar"]