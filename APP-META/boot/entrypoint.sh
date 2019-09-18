#!/usr/bin/env bash

if [ -z "$JAVA_OPT" ]; then
  JAVA_OPT="-Xms2G -Xmx2G"
fi

if [ -f "$TLOG_HOME/bin/tlog-env.sh" ]; then
  . "$TLOG_HOME/bin/tlog-env.sh"
fi

if [ -f "/home/admin/conf/tlog-cloud.properties" ]; then
   TLOG_JAVA_OPT=`cat /home/admin/conf/tlog-cloud.properties|grep "config.tlog.jstorm.worker.opt"|awk -F 'config.tlog.jstorm.worker.opt=' '{print $2}'`
fi

CLASSPATH="$TLOG_HOME/tlog-akka-2.0.0-SNAPSHOT.jar"

for f in $TLOG_HOME/lib/*.jar; do
    CLASSPATH=${CLASSPATH}:$f;
done

java -XX:OnOutOfMemoryError="kill -9 %p" $JAVA_OPT $TLOG_JAVA_OPT -cp "$CLASSPATH" com.taobao.tlog.biz.TLogCluster $TLOG_OPT "$@"