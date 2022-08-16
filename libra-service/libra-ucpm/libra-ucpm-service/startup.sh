#!/bin/bash
export SERVER="app.jar"
export ENV=$ENV
export JVM_XMS=$JVM_XMS
export JVM_XMX=$JVM_XMX
export JVM_XMN=$JVM_XMN
export JVM_MS=$JVM_MS
export JVM_MMS=$JVM_MMS
export NACOS_ADDR=$NACOS_ADDR
export NACOS_USERNAME=$NACOS_USERNAME
export NACOS_PASSWORD=$NACOS_PASSWORD
export JAVA_OPT="java -jar"
# jvm 参数比例 JVM_XMS 2g  JVM_XMX 2g JVM_XMN 1g JVM_MS 128m JVM_MMS 320m
if [[ "${ENV}" == "" ]]; then
    ENV="prod"
fi
if [[ "${JVM_XMS}" == "" ]]; then
    JVM_XMS="1g"
fi
if [[ "${JVM_XMX}" == "" ]]; then
    JVM_XMX="1g"
fi
if [[ "${JVM_XMN}" == "" ]]; then
    JVM_XMN="512m"
fi
if [[ "${JVM_MS}" == "" ]]; then
    JVM_MS="128m"
fi
if [[ "${JVM_MMS}" == "" ]]; then
    JVM_MMS="320m"
fi
echo "ENV:" ${ENV} " JVM_XMS:" ${JVM_XMS} " JVM_XMX:" ${JVM_XMX} " JVM_XMN:" ${JVM_XMN} " JVM_MS:" ${JVM_MS} " JVM_MMS:" ${JVM_MMS}
JAVA_OPT="${JAVA_OPT} -Xms${JVM_XMS} -Xmx${JVM_XMX} -Xmn${JVM_XMN} -XX:MetaspaceSize=${JVM_MS} -XX:MaxMetaspaceSize=${JVM_MMS}"
# nacos config
if [[ "${NACOS_ADDR}" != "" ]]; then
    JAVA_OPT="${JAVA_OPT} -Dspring.cloud.nacos.server-addr=${NACOS_ADDR}"
fi
if [[ "${NACOS_USERNAME}" != "" ]]; then
    JAVA_OPT="${JAVA_OPT} -Dspring.cloud.nacos.username=${NACOS_USERNAME}"
fi
if [[ "${NACOS_PASSWORD}" != "" ]]; then
    JAVA_OPT="${JAVA_OPT} -Dspring.cloud.nacos.password=${NACOS_PASSWORD}"
fi
JAVA_OPT="${JAVA_OPT} -Dspring.profiles.active=${ENV}"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/logs/java_heapdump.hprof"
JAVA_OPT="${JAVA_OPT} ${SERVER}"

echo "Start opt : ${JAVA_OPT}"

${JAVA_OPT}

