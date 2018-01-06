# 基于 __ESP8266__ 的 __Arduino WebSocketsClient-library__ .
### 使用 ___IOTPWebSocketsClient___ 库实现对IOT平台的远程长连接。
## 支持的 __RCF6455__ 功能
- 文本框架
- 连接与关闭
- 长连接
## 限制
- 服务器的IP在 ___./src/IOTPWebSocketsClient.h___ 的 `SERVER_IP` 中被限制
- 服务器的端口在 ___./src/IOTPWebSocketsClient.h___ 的 `SERVER_PORT` 中被限制
- 发送数据的默认时间在 ___./src/IOTPWebSocketsClient.h___ 的 `DEFAULT_TIME` 中被限制为 ___2017-11-11 11:11:11___
## 支持的硬件
- ESP8266 Arduino for ESP8266
## 客户端 __API__
- `IOTPWebSocketsClient` : 建立IOTPWebSocketsClient对象。
```
IOTPWebSocketsClient();
IOTPWebSocketsClient(String api_key,long unsigned int device_id);
```
- `~IOTPWebSocketsClient` ： 在析构函数中会断开连接。
```
~IOTPWebSocketsClient();
```
- `setDebugMode` ： 将程序设置为debug模式，传入参数是设置的串口通信 __波特率__。
```
void setDebugMode(int baud_rate);
```
- `setKeyId` ： 设置项目的 __api_key__ 和设备的 __device_id__ 。
```
bool setKeyId(String api_key,long unsigned int device_id)；
```
- `begin` ： 建立客户端和服务器的连接，其中 __IP__ 和 __PORT__ 被限制在 ___IOTPWebSocketsClient.h___ 中.
```
void begin();
```
- `sendTXT` ： 从客户端向服务器发送文本数据，其中 __DEFAULT_TIME__ 被限制在 ___IOTPWebSocketsClient.h___ 中。
```
bool sendTXT(String data,String time = DEFAULT_TIME);
```
- `onEvent` : 递归处理websocket事件。
```
void onEvent(WebSocketClientEvent cbEvent);
```
- `WebSocketClientEvent` : 处理websocket事件。
```
void (*WebSocketClientEvent)(WStype_t type, uint8_t * payload, size_t length)
```
其中 ___WStype_t type___ 被定义为：
```
typedef enum{
    WStype_ERROR,
    WStype_CONNECTED,
    WStype_TEXT,
    WStype_BIN,
        WStype_FRAGMENT_TEXT_START,
        WStype_FRAGMENT_BIN_START,
        WStype_FRAGMENT,
        WStype_FRAGMENT_FIN,
} WStype_t;
```