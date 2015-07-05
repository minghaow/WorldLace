#!/bin/sh
#
# Linux startup script for Resin

export CPS_FRONT_HOME=`pwd`

## Change this if necessary
if [[ $# -ge 2 ]]; then
  export RESIN_HOME=$2
fi

if [[ "$RESIN_HOME" == "" ]]; then
  echo "RESIN_HOME is not set in resin.sh, please set {resin.home.dir} in build.properties"
  exit 1
fi

PID=$RESIN_HOME/resin.pid

#
# If you want to start the entire Resin process as a different user,
# set this to the user name.  If you need to bind to a protected port,
# e.g. port 80, you can't use USER, but will need to use bin/resin.
#
USER=
#
# You can change this to $RESIN_HOME/bin/resin if you need Resin to
# bind to port 80, but run as a different user.
#
EXE=$RESIN_HOME/bin/httpd.sh
#
# Sets the commandline arguments.
#
#ARGS="-java_home $JAVA_HOME -resin_home $RESIN_HOME -Dodis.home=$CPS_FRONT_HOME -Dnative.home=$CPS_FRONT_HOME -XX:MaxPermSize=2048M -J-Xincgc -J-verbose:gc  -J-XX:NewSize=2048M -J-XX:MaxNewSize=2048M -J-Xmx8000M -J-Xms3000M -J-XX:+PrintGCDetails -J-XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -Djava.awt.headless=true"
ARGS="-java_home $JAVA_HOME -resin_home $RESIN_HOME -Dodis.home=$CPS_FRONT_HOME -Dnative.home=$CPS_FRONT_HOME -XX:MaxPermSize=256m -J-Xincgc -J-verbose:gc  -J-XX:NewSize=256M -J-XX:MaxNewSize=256M -J-Xmx800M -J-Xms600M -J-XX:+PrintGCDetails -J-XX:+PrintGCTimeStamps"
echo $ARGS
case "$1" in
  start)
    if [ -a $PID ]; then
      ps $(cat $PID) > /dev/null 2>&1
      ret=$?
      if [ $ret -eq 0 ]; then
        echo Web running as process `cat $PID`.  Stop it first.
        exit 89
      fi
    fi
    echo -n "Starting resin: "
    if test -n "$USER"; then
        su $USER -c "$EXE -pid $PID start $ARGS"
    else
      	$EXE -pid $PID start $ARGS
    fi
    echo
	;;
  stop)
	echo -n "Shutting down resin: "
	$EXE -pid $PID stop
	echo
	rm -f $PID
	;;
  restart)
	$0 stop $2
	$0 start $2
	;;
  status)
    if [ -a $PID ]; then
        ps $(cat $PID) > /dev/null
        let ret=$?
        if [ $ret -eq 0 ]; then
            echo running
            exit 80
        fi
        if [ $ret -eq 1 ]; then
            echo crushed
            exit 82
        fi
        echo Unkown status, ps returns $ret
        exit 83
    else
        echo stopped
        exit 81
    fi
    ;;
  stopforce)
    if [ -a $PID ]; then
        pid=$(cat $PID)
        kill -9 $pid
        rm -f $PID
    fi
    ;;
  *)
	echo "Usage: $0 {start|stop|restart}"
	exit 84
esac

exit 0
