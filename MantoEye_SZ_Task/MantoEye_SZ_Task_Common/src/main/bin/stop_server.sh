#!/bin/bash
#####################################################################
#        This is stop MantoEye_SZ_Task Server shell
#####################################################################

source /etc/profile
source ~/.bash_profile
export LANG="zh_CN.UTF-8"

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`

gbpdpproc=`ps -ef|grep -v grep|grep com.etone.mantoeye.analyse.local.action.*DataAction|awk 'BEGIN \
{ 
	while(getline){
 	    print($2);
		print(" ");
    }
}'`

echo "Stop Task Server --> "`date`>> $BASEDIR/logs/stop_server.log

kill -9 $gbpdpproc

#gbpdpproc=`ps -ef|grep -v grep|grep com.etone.mantoeye.analyse.socket|awk 'BEGIN \
#{ 
#	while(getline){
#	    print($2);
#		print(" ");
#    }
#}'`

#echo "Stop Task Server --> "`date`>> $BASEDIR/logs/stop_server.log

#kill -9 $gbpdpproc

#remove task exec permission
date=`date +%H`
echo "Stop Hour --> ${date}">> $BASEDIR/logs/stop_server.log
if [ ! 23 = ${date} ] ; then 
	#chmod -x start_server.sh
	#chmod -x *.sh
	echo "Task Server is stoped , after modify ,please chmod +x start_server.sh and exec ./start_server.sh"
fi

exit 0 
