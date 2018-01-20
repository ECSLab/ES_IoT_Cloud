## ES物联云
![ES物联云logo](https://github.com/ECSLab/ES_IoT_Cloud/blob/master/LOGO.jpg?raw=true)

<a href="https://github.com/ECSLab/ES_IoT_Cloud">官网主页<a/> | <a href="https://github.com/ECSLab/ES_IoT_Cloud">GitHub主页</a> | <a href="https://github.com/ECSLab/ES_IoT_Cloud">体验Demo</a>

点击查看相关项更多详情：

- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/AndroidSDK">Android</a>
- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/iOSSDK">iOS<a/>
- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/ArduinoSDK/IOTPwebsocket_ESP8266">Arduino</a> 
- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/PythonSDK">Python</a>
- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/iot_ssm">Web服务器后台</a>
- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/wsbroker">WebSocket 代理系统</a>
- <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/httpbroker">HTTP 代理系统</a>

## 简介
本系统是一个通用的、开源的、可扩展的物联网服务系统，后台主要采用Go语言和JAVA语言开发。旨在做成一个扩展传感设备简易，可以在多平台上查看、管理、获取信息简单快捷的物联网服务系统。

## 系统架构

## 功能列表 
1. <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/AndroidSDK">Android SDk</a>
	-  通过api_key进行项目认证，即初始化SDK
	-  向设备发送信息（实时）
	-  获取设备的历史数据（获取全部、最近一条、时间段）


2. <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/iOSSDK">iOS SDK</a>
	-  方便开发者快速开发iot平台iOS端，提供以下接口：
	-  向设备发送信息（实时）
	-  获取设备的历史数据（获取全部、最近一条、时间段）

3. <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/ArduinoSDK/IOTPwebsocket_ESP8266">Arduino SDK</a>
	- 基于 ESP8266 的 Arduino WebSocketsClient-library ：</br>
	 使用 IOTPWebSocketsClient 库实现对IOT平台的远程长连接</br>
	 支持的 RCF6455 文本框架、连接与关闭、长连接等功能

	- 基于 ESP8266 的 Arduino IRGree-library ： 	</br>
	 使用 IRGree 库实现对格力空调的红外控制

4. <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/SDK/PythonSDK">Python SDK（树莓派）</a>
	-  长连接 Websocket
	-  单词发送 HTTP POST
	-  硬件开发 WiringPi for Python

5. <a href="https://github.com/ECSLab/ES_IoT_Cloud/tree/master/wsbroker">websocket 代理系统</a>
	-  设备能够通过代理服务器上传数据到数据库
	-  能够通过代理服务器推送数据到传感器设备
	-  具有API认证功能，可以拒绝非法设备的接入
	-  系统稳定性好
	-  系统可扩展性好，能够后期添加各类协议
	-  系统安全性好，能够对设备合理地进行识别和授权，能够处理用户非法数据和非法操作

## 项目部署


## Change log
### V1.0
-  部署登录页面，实现Https
-  新增两个供硬件设备查询接口，供测试时使用
-  修复日期显示格式
-  新增用户操作记录日志
-  新增“获取项目概况”和“获取项目详细信息”接口
-  优化接口展示内容，减少前端请求次数
-  新增SDK两个查询接口
-  修复创建设备时,时间为空的BUG
-  新增SDK查询设备数据接口
-  新增手机号登录功能
### V2.0
-  提交了IOTPWebSocketsClient
-  提交了由继电器控制的四个饰灯的示例程序
-  提交了由继电器控制的排风扇的示例程序和上传当前pm2.5以及pm10信息的示例程序
-  引入debug模式，在此模式下，会将程序运行的详细信息输出
-  提交了红外模块控制格力空调的示例程序，并且提交了红外控制格力空调的SDK
### V3.0
-  修改了数据库连接数不足的BUG
-  加入了json配置文件机制， 可以自定义数据库配置（用户名、密码、IP地址、端口、数据库名称 、数据库编码方式），修改可执行文件同文件夹下的dbsetting.json文件即可
-  简化了服务器log显示方式
-  加入了log文件机制,会在可执行文件夹下生成websocketserver.log文件，有四种log类型：
		 	INFO：正常消息
		 	ERRO：运行时错误
		 	WARN：运行时警告
			PANI：系统奔溃
-  继续精简了log显示方式，不再不间断打印当前设备数
-  修复了工作目录错误的BUG
-  修改了数据库信息插入json信息失败的bug
-  添加了功能：log和回执中能查看错误源

## To do list
	
- 	实现实时控制反馈的接口
- 	红外控制空调的SDK在关闭空调时存在缺陷，无法按愿望正常关闭空调。
- 	改写SDK结构，让用户更加易用
