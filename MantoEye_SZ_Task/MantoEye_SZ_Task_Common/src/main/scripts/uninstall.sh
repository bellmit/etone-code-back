#!/bin/bash
echo "######################################################################"
echo "#             This is uninstall MantoEye_SZ_Task shell               #"
echo "######################################################################"

#debug
#set -x

source /etc/profile
source ~/.bash_profile
export LANG="zh_CN.UTF-8"


echo "MantoEye SZ Task will be uninstall ..."

# resolve links - $0 may be a softlink
PRG="$0"
APP_NAME="${project.build.finalName}"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

echo -n "Checking current path "
PRGDIR=`dirname "$PRG"`
echo "${PRGDIR} ...OK"

#check crontab task list
echo -n "Checking current user crontab config "
crontab -l >> $PRGDIR/logs/crontab.bak
echo "${PRGDIR}/logs/crontab.bak ...OK"

#delete old task list
echo -n "Backup current user crontab config "
sed -e "/${APP_NAME}/d"  $PRGDIR/logs/crontab.bak > $PRGDIR/logs/crontab2.bak
echo "${PRGDIR}/logs/crontab2.bak ...OK"

#active new task list
echo -n "Active current user new crontab config "
crontab -i $PRGDIR/logs/crontab2.bak
echo "${PRGDIR}/logs/crontab2.bak ...OK"

#delete bak file
echo -n "Delete current user bak crontab config "
rm -rf $PRGDIR/logs/crontab.bak
rm -rf $PRGDIR/logs/crontab2.bak
echo "${PRGDIR}/logs/crontab2.bak ...OK"

#modify shell limits
echo -n "Remove exec shell permission "
chmod -x  $PRGDIR/bin/*.sh
chmod -x  $PRGDIR/*.sh
echo "${PRGDIR}/bin/*.sh ${PRGDIR}/*.sh ...OK"

#run kill all task shell
echo -n "Check MantoEye SZ Task process "
gbpdpproc=`ps -ef|grep -v grep|grep ${APP_NAME}|awk 'BEGIN \
{ 
	while(getline){
 	        print($2);
			print(" ");
        }
}'`
kill -9 $gbpdpproc
echo "Kill MantoEye SZ Task process ...OK"

echo "Uninstall MantoEye SZ Task ... OK"
echo "Please exec commod : [\$cd .. && rm -rf ${project.build.finalName}*]"
exit 0