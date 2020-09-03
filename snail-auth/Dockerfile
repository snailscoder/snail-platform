FROM anapsix/alpine-java:8_server-jre_unlimited
RUN rm -f /etc/localtime && ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8010
ARG JAR_FILE
ADD target/${JAR_FILE} ./app.jar
ENTRYPOINT ["sh","-c","java -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
CMD ["--spring.profiles.active=test"]
