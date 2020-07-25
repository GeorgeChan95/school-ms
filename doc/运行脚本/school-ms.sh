#!/bin/bash
#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=school-ms.jar

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case $2 in
   "start")
     start
     ;;
   "stop")
     stop
     ;;
   "status")
     status
     ;;
   "restart")	
     restart
     ;;
   *)
     usage
     ;;
esac

#使用说明，用来提示输入参数
# 使用示例：
# 启动 sh school-ms.sh prod start
# 状态 sh school-ms.sh prod status
# 停止 sh school-ms.sh prod stop
# 重启 sh school-ms.sh prod restart
usage() {
    echo "Usage: sh school-ms.sh [dev|prod] [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist() { 
    pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}' `
    #如果不存在返回1，存在返回0
    if [ -z "${pid}" ]; then
      return 1
    else
      return 0
    fi
}

# 检查环境参数是否正确
is_active() {
	# 判断用户输入的环境参数是否正确，正确返回 1， 错误返回 0
	if [ $1 -eq "dev" ]
	then
			return 1
	elif [ $1 -eq "prod" ]
	then
			return 1
	else 
			echo "环境参数输入异常"
			usage
			return 0
	fi
}

#启动方法
start() {
   is_exist
   if [ $? -eq 0 ]; then
     echo "${APP_NAME} is already running. pid=${pid} ."
   else
	 is_active
	 if [ $? -eq 1 ]
	 then
			nohup java -jar $APP_NAME > appstart.log 2>&1 &
   fi
}

# 停止方法
stop(){
   is_exist
   if [ $? -eq 0 ]
   then
		is_active
		if [ $? -eq 1 ]
		then
				kill -9 $pid
   else
     echo "${APP_NAME} is not running"
   fi
}

#输出运行状态
status() {
   is_exist
   if [ $? -eq 0 ]
   then
		is_active
		if [ $? -eq 1 ]
		then
			echo "${APP_NAME} is running. Pid is ${pid}"
   else
     echo "${APP_NAME} is not running."
   fi
}

#重启
restart() {
	is_active
	if [ $? -eq 1 ]
	then
			stop
			start
}