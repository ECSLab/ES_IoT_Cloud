# 物联网云平台WebSocket代理系统
## 文件介绍

### src：代码文件夹

### bin：服务器可执行文件文件夹

### testtools：测试工具文件夹





## 需求

物联网云平台代理系统是EMLAB物联网服务云平台的一部分，旨在对传感器进行数据的交互（双工通讯）。为开发SDK的开发人员提供开发接口。

### 功能性要求

设备能够通过代理服务器上传数据到数据库

能够通过代理服务器推送数据到传感器设备（WebSocket）

具有API认证功能，可以拒绝非法设备的接入

### 非功能性要求

系统稳定性好

系统可扩展性好，能够后期添加各类协议

系统安全性好，能够对设备合理地进行识别和授权，能够处理用户非法数据和非法操作





## API设计

### 设备认证API

| 方法 | WebSocket --- send |
| --- | --- |
| URL | ws://127.0.0.1:9000/ |
| 内容 | json格式：设备的各项数据，例如GPS数据{&quot;api-key&quot;:xxxx-ffff-zzzzz,&quot;device\_id&quot;:xxxxxx,&quot;data&quot;:&quot;AUTH&quot;} |
| 举例：|{&quot;api\_key&quot;: &quot;12345&quot;, &quot;device\_id&quot;: &quot;1&quot;, &quot;data&quot;: &quot;AUTH&quot;}  |
| 错误回执 | SERVER\_AUTH\_ERROR：认证错误
||SERVER\_AUTH\_SUCESS：认证成功 |

### 设备数据上传API

| 方法 | WebSocket --- send |
| --- | --- |
| URL | ws://127.0.0.1:9000/ |
| 内容 | json格式：设备的各项数据，例如GPS数据{&quot;api-key&quot;:&quot;STRING&lt;项目的APIKEY&gt;&quot;,&quot;device\_id&quot;:&quot;STRING&lt;设备在项目中的id&gt;&quot;,&quot;data&quot;:&quot;STRING&lt;用户自定义数据&gt;&quot;，&quot;time&quot;:&quot;STRING&lt;时间&gt;&quot;} |
| 举例|_{&quot;api\_key&quot;: &quot;sdakasyri&quot;, &quot;device\_id&quot;: &quot;1&quot;, &quot;data&quot;: &quot;_你好传感器_&quot;, &quot;time&quot;:&quot;2017-10-27 20:23:31&quot;}_  |
| 错误回执 | SERVER\_SOLVE\_JSON\_ERROR：json解析错误 
||SERVER\_CHECK\_DEV\_ERROR：设备信息检错误
||API\_DEVID\_NOT\_PAIRED：API和DEVID不匹配
||SERVER\_ADD\_DATA\_ERROR：数据添加错误
||SERVER\_ADD\_DATA\_SUCCESS：数据发布成功   |




### 向设备推送消息API

| 方法 | HTTP --- POST |
| --- | --- |
| URL | http://127.0.0.1:9000/upload |
| HTTP内容 | HTTP表单：设备的各项数据&quot;api-key&quot;:&quot;xxxxxx&quot;,&quot;device\_id&quot;:&quot;xxxxxx&quot;,&quot;data&quot;:&quot;STRING&lt;用户自定义数据&gt;&quot; |
| 例子（以Python为例）：|data = { **&#39;api\_key&#39;** : **&#39;12345&#39;** , **&#39;device\_id&#39;** : **&#39;1&#39;** , **&#39;data&#39;** : **&#39;**  你好传感器 **&#39;** }  |
|  错误回执 | AUTH\_ERROR：认证错误
||AUTH\_FAIL：认证失败
||DEV\_NOT\_ONLINE：设备不在线
||PUBLISH\_SUCCESS：发布成功  |

## 更新历史

- 0.1.1
  * 修改了数据库连接数不足的BUG
  * 加入了json配置文件机制， 可以自定义数据库配置（用户名、密码、IP地址、端口、数据库名称 、数据库编码方式），**修改可执行文件同文件夹下的dbsetting.json文件即可**
  * 简化了服务器log显示方式
  * 更改了README.md文件中的格式错误

- 0.1.2
  * 加入了log文件机制,会在可执行文件夹下生成`websocketserver.log`文件
   
    有四种log类型：
    
    `INFO`：正常消息

    `ERRO`：运行时错误

    `WARN`：运行时警告

    `PANI`：系统奔溃

    示例：
    ```log
    [INFO]2017/11/27 23:58:47 Start listening port 9000
    [INFO]2017/11/27 23:58:59 Get Data from http://localhost:63342
    [INFO]2017/11/27 23:58:59 Estanblish :http://localhost:63342
    [INFO]2017/11/27 23:58:59 Now dev num :  1
    ```

    ```log
    [PANI]2017/11/28 00:17:44 ListenAndServe: listen tcp :9000: bind: address already in use
    ```

  * 继续精简了log显示方式，不再不间断打印当前设备数

- 0.1.3
  * 修复了工作目录错误的BUG

- 0.1.4
  *  修改了数据库信息插入json信息失败的bug
  *  添加了功能：log和回执中能查看错误源