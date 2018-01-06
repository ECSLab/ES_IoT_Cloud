# 物联网云平台HTTP代理系统

## 文件介绍
### src: 代码文件夹
### bin: 服务器可执行文件文件夹

## 需求
物联网云平台代理系统是EMLAB物联网服务云平台的一部分，旨在对传感器进行数据的交互（双工通讯）。为开发SDK的开发人员提供开发接口。
### 功能性要求
具有API认证功能，可以拒绝非法设备的接入

设备能够通过代理服务器上传数据到数据库
### 非功能性要求
系统稳定性好

系统可扩展性好，能够后期添加各类协议

系统安全性好，能够对设备合理地进行识别和授权，能够处理用户非法数据和非法操作

## API设计
### 设备认证API
| 方法 | HTTP --- POST |
| --- | --- |
| URL | http://127.0.0.1:9006/ |
| 内容 | 通过http表单向服务器提交API_key，Dev_number以及设备的各种数据，例如GPS数据{"Api_key":"STRING<项目的APIKEY>","Device_id":"STRING<设备在项目中的id>","Data":"STRING<用户自定义数据>","Dev_num":"INT<添加的设备号>", "Create_time": "DATETIME<添加时间>"}|
| 举例 | {"Api_key": "sdakasyri", "Device_id": "1", "Data": "你好传感器", "Dev_num": 1, "Create_time": "2017-11-24 00:00:00"} |
| 错误回执 | API_DEVID_NOT_PAIRED: API与DEVID不匹配 |
|         | PAIRED: 匹配成功                      |
|         | GET_DATA_ERROR: 获取数据错误           |
|         | SERVER_ADD_DATA_ERROR: 数据添加错误    |
|         |  PUBLISH_SUCCESS: 数据发布成功         |


## 配置文件
默认路径:httpbroker/config/conf.toml
### 配置内容
| 配置信息  | 含义   |
| :-----:  | :---: |
| Hostsip  | 数据库ip与端口 |
| Username | 数据库用户名 |
| Password | 数据库密码 |
| DBname   | 数据库名 |


## 更新历史
- 0.01
  - 更新README中描述不符的内容
  - 在README中添加配置文件项
  - 修改之前的访问数据库的方式，避免多次连接数据库
  - create_time现在是可自行添加的字段