#!/bin/bash
#如果新增了模块，需在此处添加，多个模块用,隔开
usage() {
    echo "Usage: sh 执行脚本.sh <profile>"
    echo "eg: sh package-application.sh test"
    exit 1
}

if [[ -n "$1" ]];then
  mvnw clean package -P"$1" -f pom.xml -pl application,framework
else
  usage
fi
