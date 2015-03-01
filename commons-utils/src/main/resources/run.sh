#!/bin/bash

jarName=JS_MONITOR_Scheduler.jar

#进入脚本所在路径
cd `dirname $0`

while getopts "cskdr" arg
do
	case $arg in
	c)
		rm -rf logs
		;;
	s)
		mkdir -p logs
		nohup java -jar $jarName 2>&1 >logs/nohup.log  &
		echo `ps -ef | grep java | grep $jarName | grep -v grep|awk '{print $2}'`
		;;
	k)
		kill -9 `ps -ef | grep java | grep $jarName | grep -v grep|awk '{print $2}'`
		;;
	d)
		mkdir -p logs
		nohup java -Xdebug -server -Xrunjdwp:transport=dt_socket,server=y,address=8080 -jar $jarName  2>&1 > logs/nohup &
		echo `ps -ef | grep java | grep $jarName | grep -v grep|awk '{print $2}'`
		;;
	r)
		kill -9 `ps -ef | grep java | grep $jarName | grep -v grep|awk '{print $2}'`
		rm -rf logs
		mkdir -p logs
		nohup java -jar Statistics.jar 2>&1 >logs/nohup.log  &
		echo `ps -ef | grep java | grep $jarName | grep -v grep|awk '{print $2}'`
		;;
	?)
		echo unknow argument
		;;
	esac
done


