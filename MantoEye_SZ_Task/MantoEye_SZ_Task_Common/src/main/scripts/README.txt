包結構說明：
1、install.sh項目安裝腳本
2、uninstall.sh項目卸載腳本
3、bin/下Task運行腳本
4、lib/下工程jar包及所有第三方支持jar包
5、etc/下工程所需配置文件
6、logs/下所有日誌文件
7、README.txt 說明文件

Task安裝及運行說明：
1、$chmod +x install.sh 回車
3、運行安裝Task腳本 ,配置相關環境變量
   $./install.sh
4、運行Task執行腳本,開啟Task任務
   $cd bin/ 回車 
   $./start_server.sh
5、$ps aux | grep MantoEye_SZ_Task 可查看Task是否運行成功

Task卸載說明：
1、$chmod +x uninstall.sh 回車
3、運行sh腳本 
   $./uninstall.sh
4、$ps aux | grep MantoEye_SZ_Task 可查看Task進程是否存在
5、刪除Task
   $cd .. && rm -rf ${project.build.finalName}*

配置修改說明：
1、etc下修改配置文件
