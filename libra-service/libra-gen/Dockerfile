FROM registry.cn-shenzhen.aliyuncs.com/p_jack_hfbin/openjdk:8-jdk-alpine
LABEL maintainer=libra-gen
ADD startup.sh startup.sh
ADD target/*.jar /app.jar
RUN chmod +x startup.sh
ENV LANG=zh_CN.UTF-8
ENV TZ=Asia/Shanghai

ENTRYPOINT ["sh", "./startup.sh"]