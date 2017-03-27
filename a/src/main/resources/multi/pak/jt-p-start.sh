#!/bin/bash
BASE_HOME=/opt/projects/jt-p
LIBS="$BASE_HOME/lib"
CLASSES="$BASE_HOME"
CLASS_PATH="$CLASSPATH":"$LIBS/*":"$CLASSES"
echo "BASE_HOME : $BASE_HOME"
echo "CLASS_PATH: $CLASS_PATH"

java -server -Xms64m -Xmx256m -classpath "$CLASS_PATH" com.hitler.server.TowerProvider &

echo $! > /var/run/jt-p.pid