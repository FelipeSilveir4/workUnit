#!/bin/sh

echo "Host file"
cat /etc/hosts

echo "Stating Work Unit"
cd /workUnit
java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -jar work-unit-0.3.jar
