[项目主页](https://github.com/biloba123/EslabIot_Android_SDK)
## 主要功能
方便开发者快速开发iot平台Android端，提供以下接口：
1. 通过api_key进行项目认证，即初始化SDK
2. 向设备发送信息（实时）
3. 获取设备的历史数据（获取全部、最近一条、时间段）

![](http://upload-images.jianshu.io/upload_images/5734256-58cc8d66e0e236b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 使用方法
**1. 添加JitPack仓库到build file，并添加依赖**

1.1 如果使用gradle，在项目的build.gradle中添加
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在app或其他module的build.gradle中添加依赖
```
	dependencies {
	        compile 'com.github.biloba123:EslabIot_Android_SDK:1.0.1'
	}
```
1.2 如果使用maven
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
添加依赖
```
<dependency>
	    <groupId>com.github.biloba123</groupId>
	    <artifactId>EslabIot_Android_SDK</artifactId>
	    <version>1.0.1</version>
	</dependency>
```

**2. 用项目的api_key初始化SDK**
必须在调用之前进行初始化，建议在Application中初始化
```
EslabIot.init(String apiKey);
```
参数说明：
apiKey: 项目的api_key，用于验证合法性

**3. 向设备发送控制信息**
发送采用异步接口回调方式（内部用OkHttp进行请求，用RxJava进行异步操作）
```
EslabIot.postMessage(final int deviceId, final String data, final PostMsgListener listener);
```
参数说明：
deviceId: 想要控制的设备id
data: 发送信息，由开发者自定义
listener: 回调接口

示例：
```
//发送"OFF"控制信息给device_id为2的设备
EslabIot.postMessage(2, "OFF", new PostMsgListener() {
                            @Override
                            public void succ(String s) {
                                //发送成功时回调，返回"	PUBLISH_SUCCESS"
                                if (BuildConfig.DEBUG) Log.d(TAG, "succ: "+s);
                            }

                            @Override
                            public void error(Throwable e) {
                                //出错时回调
                                e.printStackTrace();
                            }
                        });
```
常见出错情况

e.getMessage()|对应情况
---------------------|------------
AUTH_ERROR|认证错误
AUTH_FAIL|认证失败
DEV_NOT_ONLINE|设备不在线

**4. 获取设备信息**
```
//获取全部数据
EslabIot.getHistoryData(final int deviceId, final String order, final GetDataListener listener);
//获取全部数据，按时间升序
EslabIot.getHistoryData(final int deviceId, final GetDataListener listener);

//获取最新一条数据
EslabIot.getLastData(final int deviceId, final GetDataListener listener);

//获取指定时间段数据
EslabIot.getDataWithTime(final int deviceId, Date startTime, Date endTime,
                                       String order, final GetDataListener listener);
//获取指定时间段数据，按时间升序
EslabIot.getDataWithTime(final int deviceId, Date startTime, Date endTime,
                                       String order, final GetDataListener listener);
```
参数说明：
order: 数据排序方式（取值EslabIot.ORDER_ASC:时间升序，EslabIot.ORDER_DESC:时间降序）
listener: 回调接口

示例：
```
EslabIot.getHistoryData(2,EslabIot.ORDER_ASC, new GetDataListener() {
            @Override
            public void succ(Reponse r) {
                if (r.getCode()==0) {//获取数据成功
                    String data=r.getData();
                    //将json格式数据进行装换
                    //...
                }else{//获取数据失败
                    if (BuildConfig.DEBUG) Log.e(TAG, "load data fail "+r.getCode()+" "+r.getMessage() );
                }
            }

            @Override
            public void error(Throwable e) {
                e.printStackTrace();
            }
        });
```
这里注意，succ(Reponse r)是在请求成功后回调的，但并不意味数据获取成功（例如该设备不存在）,只有r.getCode()为0才表示获取成功，r.getData()能得到相应json格式数据；如果code不为0，则表示获取失败，失败信息可通过r.getMessage()获取

**5. 高级用法**
向设备发送控制信息后，虽然发送成功但设备可能因为某些原因为做出相应状态改变，这种情况下设备会反馈信息给数据库。因此，如果要确定设备是否成功相应应在发送信息时记录时间，再在succ回调中适当时机获取最近一条记录，通过记录时间比较，得到设备的状态

