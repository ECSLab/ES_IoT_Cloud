[项目主页](https://github.com/biloba123/EslabIot_Android_SDK)
## 主要功能
方便开发者快速开发iot平台iOS端，提供以下接口：
1. 向设备发送信息（实时）
2. 获取设备的历史数据（获取全部、最近一条、时间段）
## 使用方法
**1. 添加iOSSDK文件夹中的SDK文件夹到项目**

**2. 在您的.m文件中import "iotPost.h"及"iotOperation.h"**

**3. iotPost 参数说明**

1. setApiKey:设置api_key
2. setDeviceId:设置设备号
3. setOrderyBy:设置排序方式（获取设备数据时用）asc为时间顺序，desc为时间倒序
4. setStartTime:设置查询起始时间（获取设备数据时用）
5. setEndTime:设置查询结束时间（获取设备数据时用）
6. setData:设置传输数据（向服务器发送数据时用）

**4. 向设备发送控制信息**

发送采用AFNetWorking方式
```
iotPost *poststr=[[iotPost alloc]init];
[poststr setApiKey:[_dic valueForKey:@"api_key"]];
[poststr setDeviceId:[_dic valueForKey:@"device_id"]];
[poststr setData:data];
iotOperation *op=[[iotOperation alloc]init];
NSString *str=[op sendMessage:poststr];//返回值为NSString类型
```
参数说明：
poststr: 参数汇总
data: 发送信息，由开发者自定义
api_key: 项目的api_key
device_id：设备号

示例：
```
//发送"OFF"控制信息给api_key为eslabIot，device_id为2的设备
iotPost *poststr=[[iotPost alloc]init];
[poststr setApiKey:@“eslabIot”];
[poststr setDeviceId:@“2”];
[poststr setData:@“OFF”];
iotOperation *op=[[iotOperation alloc]init];
NSString *str=[op sendMessage:poststr];
```
常见出错情况

error|对应情况
---------------------|------------
AUTH_ERROR|认证错误
AUTH_FAIL|认证失败
DEV_NOT_ONLINE|设备不在线

**5. 获取设备信息**
```
iotOperation *op=[[iotOperation alloc]init];
//获取全部数据，返回值为NSDictionary类型
[op getDataList:(iotPost *)postData];

//获取最新一条数据，返回值为NSDictionary类型

[op getDataLatest:(iotPost *)postData];

//获取指定时间段数据(默认为升序)，返回值为NSDictionary类型

[op getListByTime:(iotPost *)postData];

```
参数说明：
postData：参数汇总

示例：
```
iotPost *poststr=[[iotPost alloc]init];
[poststr setApiKey:@“eslabIot”];
[poststr setDeviceId:@“2”];
[poststr setOrderBy:@"asc"];
iotOperation *op=[[iotOperation alloc]init];
NSDictionary *dic=[op getDataList:poststr];
```


