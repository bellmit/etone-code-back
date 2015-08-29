#!/bin/bash
echo "######################################################################"
echo "#              This is install MantoEye_SZ_Task shell                #"
echo "######################################################################"

#debug
#set -x

source /etc/profile
source ~/.bash_profile
export LANG="zh_CN.UTF-8"

echo "MantoEye SZ Task will be start install ..."

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
echo -n "Checking base path "
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`
echo "${BASEDIR} ...OK"

# check dir logs is exists
if [ ! -d $PRGDIR/logs ]; then
	echo -n "Checking logs path "
	mkdir -p $PRGDIR/logs
	echo "${PRGDIR}/logs ...OK"
fi

#check crontab task list
echo -n "Checking current user crontab config "
crontab -l >> $PRGDIR/logs/crontab.bak
echo "${PRGDIR}/logs/crontab.bak ...OK"

#delete old task list
echo -n "Backup current user crontab config "
sed -e "/${APP_NAME}/d"  ${PRGDIR}/logs/crontab.bak > ${PRGDIR}/logs/crontab2.bak
echo "${PRGDIR}/logs/crontab2.bak ...OK"

#add new task list
echo -n "Make current user new crontab config ..."
echo "        " >> ${PRGDIR}/logs/crontab2.bak
echo "# ${APP_NAME}[`date`]" >> ${PRGDIR}/logs/crontab2.bak
echo "# Check ${APP_NAME} shell every 2 min" >> ${PRGDIR}/logs/crontab2.bak
echo "*/2 * * * *  ${BASEDIR}/${APP_NAME}/bin/start_server.sh" >>  ${PRGDIR}/logs/crontab2.bak
echo "# Kill ${APP_NAME} shell every day 23:59 pm" >>  ${PRGDIR}/logs/crontab2.bak
echo "30 01-08/3,12,15 * * *  ${BASEDIR}/${APP_NAME}/bin/stop_server.sh" >>  ${PRGDIR}/logs/crontab2.bak
echo "        " >> ${PRGDIR}/logs/crontab2.bak
echo "OK"

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
echo -n "Chmod exec shell permission "
chmod +x  $PRGDIR/bin/*.sh
echo "${PRGDIR}/bin/*.sh ...OK"

echo "Install MantoEye SZ Task ... OK"
echo "Please into ${PRGDIR}/etc edit your config."
echo "Or use default configs and exec ${PRGDIR}/bin/start_server.sh"

exit 0
