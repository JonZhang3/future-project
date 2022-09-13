#!/bin/bash
#初始化服务器脚本
# chrony 时间同步工具
# git 版本管理工具
sudo yum install chrony git -y
sudo yum install python3 python3-pip -y
#同步时区 
sudo systemctl enable chronyd
sudo systemctl start chronyd