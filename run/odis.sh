#!/bin/sh
# 
#  Outfox Command Script
#

# resolve links - $0 may be a softlink
THIS="$0"
while [ -h "$THIS" ]; do
  ls=`ls -ld "$THIS"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    THIS="$link"
  else
    THIS=`dirname "$THIS"`/"$link"
  fi
done

THIS_DIR=`dirname "$THIS"`
ODIS_HOME=`cd "$THIS_DIR/.." ; pwd`
export ODIS_HOME

# Limit stack space because we need a lot of threads
ulimit -s 512

# JAVA parameters

if [ "$JAVA_HOME" = "" ]; then
  JAVA="java -server"
else
  JAVA="$JAVA_HOME/bin/java -server"
fi;

JAVA_HEAP_MAX=-Xmx2000m 
JAVA_ASSERT="-ea"

# CLASSPATH initially contains $ODIS_HOMEi/lib
CLASSPATH=${CLASSPATH}:$ODIS_HOME/lib
CLASSPATH=${CLASSPATH}:$ODIS_HOME/conf

# Add eclipse class path
if [ -d "$ODIS_HOME/classes" ]; then
  CLASSPATH=${CLASSPATH}:$ODIS_HOME/classes
fi
# Add build/test classes to CLASSPATH
if [ -d "$ODIS_HOME/build/classes" ]; then
  CLASSPATH=${CLASSPATH}:$ODIS_HOME/build/classes
fi
if [ -d "$ODIS_HOME/build/test/classes" ]; then
  CLASSPATH=${CLASSPATH}:$ODIS_HOME/build/test/classes
fi
if [ -d "$ODIS_HOME/build/tool/classes" ]; then
  CLASSPATH=${CLASSPATH}:$ODIS_HOME/build/tool/classes
fi
# so that filenames w/ spaces are handled correctly in loops below
IFS=
# add libs to CLASSPATH
for f in $ODIS_HOME/lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done
# restore ordinary behaviour
unset IFS

while true; do

# assertion 
if [ "$1" = "-noassert" ] ; then
  JAVA_ASSERT="-da"
  shift;
# remote debugging
elif [ "$1" = "-jdwp" ] ; then
  JAVA="$JAVA -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=0.0.0.0:8001"
  shift;
# CPU profiling
elif [ "$1" = "-cpu" ] ; then
  JAVA="$JAVA -agentlib:hprof=cpu=samples,depth=20"
  shift;
# Heap size
elif [ "$1" = "-heap" ] ; then
  shift;   
  JAVA_HEAP_MAX="-Xmx$1"
  shift;
# Do lock contention profiling
elif [ "$1" = "-lock" ] ; then
  JAVA="$JAVA -agentlib:hprof=monitor=y"
  shift;
# other options
elif [ "$1" = "-J" ] ; then
  shift;
  JAVA="$JAVA $1"
  shift;
elif [ ! `expr "$1" : "-D.*"` = "0" ] ; then
  JAVA="$JAVA $1"
  shift;
elif [ ! `expr "$1" : "-X.*"` = "0" ] ; then
  JAVA="$JAVA $1"
  shift;
else
  break;
fi

done #while

# figure out which class to run
CLASS=outfox.buyers.tool.ToolMain

if [ "$1" = "run" ] ; then
  shift;
  CLASS=$1
  shift
fi

# run it
EXEC_CMD="$JAVA $JAVA_HEAP_MAX $JAVA_ASSERT -Dodis.home=$ODIS_HOME -Dpid=$$ -Dfile.encoding=UTF-8 -classpath $CLASSPATH $CLASS $@"
exec $EXEC_CMD
