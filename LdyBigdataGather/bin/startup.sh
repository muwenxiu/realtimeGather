#!/bin/bash

CURRENT_HOME=$(cd `dirname ${0}` ; pwd)
app_name=LdyBigdataGatherApplications
cd ${CURRENT_HOME}/..

pid=`jps |grep ${app_name} | awk -F '' '{print $1}'`
if [ ${pid} ] ; then
  kill -9 ${pid}
fi
nohup java -Xms1024m -Xmx1024m -Djava.awt.headless=true -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -Dfile.encoding=UTF-8 -Djava.io.tmpdir=${workdir} -cp ldybigdatagather-1.0.0.jar ldy.bigdata.gather.LdyBigdataGatherApplication > /dev/null  2>&1 &                                                                                                                                                                                                                                                                       -cp ldybigdatagather-1.0.0.jar ldy.bigdata.gather.LdyBigdataGatherApplication
