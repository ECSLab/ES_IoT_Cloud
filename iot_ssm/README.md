# 物联网云平台系统Web后台  

## 适用对象
Android、iOS和Web前端  

## 后台json返回值说明
```text
//成功情况
{
  "code" : 0,
  "message" : "成功",
  "data" : {
    //  内容
  }
}

//错误情况
{
  "code" : 大于0的数字,
  "message" : "错误提示信息",
  "data" : null
}

//后台服务器异常情况(这种情况请联系我)
{
  "code" : -1,
  "message" : "系统抛出的异常",
  "data" : null
}
```

## 前端接口测试
http://47.92.48.100:8099/iot/swagger-ui.html

## 移动端SDK接口

一、获取设备数据  

| 名称 | 获取设备数据 |
| --- | --- |
| URL | http://47.92.48.100:8099/iot/sdk/device/data|
| 方法 | post |
  
上传参数：    
apiKey : 项目的唯一秘钥  
deviceId : 设备号  
order : "asc"或"desc"(可选,不填时默认为asc)  
pageNumber : 默认为"1",表示获取第n页数据  

成功返回：  
```text
{
    "code": 0,
    "message": "成功",
    "data": {
        "currentPageNumber": 1,             //表示此数据是第1页的
        "pageSize": 50,                     //表示每页数据量为50条，最后一页可能不满
        "totalPageNumber": 21333,           //总页数（注意，为了提升效率，当且仅当pageNumber="1"时才可得到该数据，其他情况均为0）
        "currentPageDataCount": 50,         //表示当前页中有50条数据
        "list": [
            {
                "id": 3383630,
                "deviceId": 7,
                "dataValue": "{\"pm2.5\": 45.20,\"pm10\": 90.00}",
                "createTime": "2017-12-26 21:03:51"
            },
            {
                "id": 3383634,
                "deviceId": 7,
                "dataValue": "{\"pm2.5\": 45.10,\"pm10\": 91.20}",
                "createTime": "2017-12-26 21:03:54"
            }
        ]
    }
}
```

失败返回:
```json
{
    "code": 207,  
    "message": "未找到相应的项目,请确认APIKEY和deviceId",  
    "data": null
}
```
二、获取设备最近一条数据  

| 名称 | 获取设备最近一条数据 |
| --- | --- |
| URL | http://47.92.48.100:8099/iot/sdk/device/data/latest|
| 方法 | post |

上传参数：    
apiKey : 项目的唯一秘钥  
deviceId : 设备号  

成功返回：  
```json
{
    "code": 0,
    "message": "成功",
    "data": {
        "id": 3,
        "deviceId": 1,
        "dataValue": "250",
        "createTime": "2017-10-27 20:24:01"
    }
}
```

失败返回:
```json
{
    "code": 207,  
    "message": "未找到相应的项目,请确认APIKEY和deviceId",  
    "data": null
}
```


三、获取某个时间段设备数据信息

| 名称 | 获取某个时间段设备数据信息 |
| --- | --- |
| URL | http://47.92.48.100:8099/iot/sdk/device/data/time|
| 方法 | post |

上传参数：    
apiKey : 项目的唯一秘钥    
deviceId : 设备号  
startTime : 开始时间(yyyy-MM-dd HH:mm:ss)  
endTime : 结束时间（yyyy-MM-dd HH:mm:ss）  
order : "asc"或"desc"(可选,不填时默认为asc)  
pageNumber : 默认为"1"，表示获取第n页数据  

成功返回：  
```text
{
    "code": 0,
    "message": "成功",
    "data": {
        "currentPageNumber": 1,             //表示此数据是第1页的
        "pageSize": 50,                     //表示每页数据量为50条，最后一页可能不满
        "totalPageNumber": 16974,           //总页数（注意，为了提升效率，当且仅当pageNumber="1"时才可得到该数据，其他情况均为0）
        "currentPageDataCount": 50,         //表示当前页中有50条数据
        "list": [
            {
                "id": 3771807,
                "deviceId": 7,
                "dataValue": "{\"pm2.5\": 74.60,\"pm10\": 130.30}",
                "createTime": "2017-12-31 00:00:02"
            },
            {
                "id": 3771810,
                "deviceId": 7,
                "dataValue": "{\"pm2.5\": 75.70,\"pm10\": 135.00}",
                "createTime": "2017-12-31 00:00:05"
            }
        ]
    }
}
```

失败返回:
```json
{
    "code": 207,  
    "message": "未找到相应的项目,请确认APIKEY和deviceId",  
    "data": null
}
```

## 更新日志
2018-2-12  
1. 优化数据库结构  
2. 修改移动端SDK，修改返回类型，将1与3接口修改为分页式获取数据  

2018-2-7  
1. 优化SQL，建立索引  

2018-2-2  
1. 修改部分API，针对涉及删除操作的API，加apiKey参数  

2018-1-13  
1. 部署开发者中心页面
2. 采用Thymeleaf引擎代替jsp

2018-1-5  
1. 部署登录页面，实现Https

2017-12-19  
1. 新增两个供硬件设备查询接口，供测试时使用

2017-12-17  
1. 修复日期显示格式
2. 新增用户操作记录日志

2017-12-9  
1. 新增“获取项目概况”和“获取项目详细信息”接口  
2. 优化接口展示内容，减少前端请求次数  

2017-12-3  
1. 新增SDK两个查询接口  

2017-12-2  
1. 修复创建设备时,时间为空的BUG  
2. 新增SDK查询设备数据接口  

2017-11-27  
1. 新增手机号登录功能
