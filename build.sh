#!/bin/bash

cd /data/jar/
tar -zxvf package.tgz

sk="-javaagent:/data/agent/skywalking-agent.jar -Dskywalking.collector.backend_service=node3:11800"

echo '=============重启gateway服务================'
gatewayServiceName="libra-gateway"
gatewayJar="$gatewayServiceName-1.0.0-SNAPSHOT.jar"
mkdir /data/jar/libra-gateway
mv /data/jar/libra-gateway/target/$gatewayJar /data/jar/libra-gateway/$gatewayJar
cd /data/jar/libra-gateway
kill -9 `cat $gatewayServiceName-pid`
gatewaySk="$sk -Dskywalking.agent.service_name=$gatewayServiceName"
nohup java -jar -Xms256m -Xmx512m $gatewaySk  $gatewayJar --server.port=9999 --spring.profiles.active=prod --spring.cloud.nacos.server-addr=http://node1:8848  2>&1 &
echo "$!" > $gatewayServiceName-pid
echo '==============启动gateway完成================='


echo '=============base-service================'
baseServiceName="libra-base-service"
baseJar="$baseServiceName-1.0.0.jar"
mkdir /data/jar/libra-base
mv /data/jar/libra-service/libra-base/libra-base-service/target/$baseJar /data/jar/libra-base/$baseJar
cd /data/jar/libra-base
kill -9 `cat $baseServiceName-pid`
baseSk="$sk -Dskywalking.agent.service_name=$baseServiceName"
nohup java -jar -Xms256m -Xmx512m $baseSk  $baseJar --server.port=8810 --spring.profiles.active=prod --spring.cloud.nacos.server-addr=http://node1:8848  2>&1 &
echo "$!" > $baseServiceName-pid
echo '==============base-service================='


echo '=============重启ucpm-service服务================'
ucpmServiceName="libra-ucpm-service"
ucpmJar="$ucpmServiceName-1.0.0.jar"
mkdir /data/jar/libra-ucpm
mv /data/jar/libra-service/libra-ucpm/libra-ucpm-service/target/$ucpmJar /data/jar/libra-ucpm/$ucpmJar
cd /data/jar/libra-ucpm
kill -9 `cat $ucpmServiceName-pid`
ucpmSk="$sk -Dskywalking.agent.service_name=$ucpmServiceName"
nohup java -jar -Xms256m -Xmx512m $ucpmSk  $ucpmJar --server.port=8811 --spring.profiles.active=prod --spring.cloud.nacos.server-addr=http://node1:8848  2>&1 &
echo "$!" > $ucpmServiceName-pid
echo '==============启动ucpm-service完成================='


echo '=============tenant-service服务================'
tenantServiceName="libra-tenant-service"
tenantJar="$tenantServiceName-1.0.0.jar"
mkdir /data/jar/libra-tenant
mv /data/jar/libra-service/libra-tenant/libra-tenant-service/target/$tenantJar /data/jar/libra-tenant/$tenantJar
cd /data/jar/libra-tenant
kill -9 `cat $tenantServiceName-pid`
tenantSk="$sk -Dskywalking.agent.service_name=$tenantServiceName"
nohup java -jar -Xms256m -Xmx512m $tenantSk  $tenantJar --server.port=8812 --spring.profiles.active=prod --spring.cloud.nacos.server-addr=http://node1:8848  2>&1 &
echo "$!" > $tenantServiceName-pid
echo '==============启动tenant-service完成================='


echo '=============重启libra-auth服务================'
authServiceName="libra-auth"
authJar="$authServiceName-1.0.0.jar"
mkdir /data/jar/libra-auth
mv /data/jar/libra-auth/target/$authJar /data/jar/libra-auth/$authJar
cd /data/jar/libra-auth
kill -9 `cat $authServiceName-pid`
authSk="$sk -Dskywalking.agent.service_name=$authServiceName"
nohup java -jar -Xms256m -Xmx512m $authSk  $authJar  --server.port=8813 --spring.profiles.active=prod --spring.cloud.nacos.server-addr=http://node1:8848 2>&1 &
echo "$!" > $authServiceName-pid
echo '==============启动libra-auth完成================='


echo '=============重启libra-gen服务================'
genServiceName="libra-gen"
genJar="$genServiceName-1.0.0.jar"
mkdir /data/jar/libra-gen
mv /data/jar/libra-service/libra-gen/target/$genJar /data/jar/libra-gen/$genJar
cd /data/jar/libra-gen
kill -9 `cat $genServiceName-pid`
genSk="$sk -Dskywalking.agent.service_name=$genServiceName"
nohup java -jar -Xms256m -Xmx512m $genSk  $genJar  --server.port=8814 --spring.profiles.active=prod --spring.cloud.nacos.server-addr=http://node1:8848  2>&1 &
echo "$!" > $genServiceName-pid
echo '==============启动libra-gen完成================='
