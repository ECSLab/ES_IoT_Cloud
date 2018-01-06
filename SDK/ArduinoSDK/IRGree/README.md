# 基于 __ESP8266__ 的 __Arduino IRGree-library__ .
### 使用 ___IRGree___ 库实现对格力空调的红外控制。
## 限制
- 暂时只支持制冷制热模式，其他的模式会不断完善。
- 由于没有示波器，无法确定定时时间是否准确所以没有有关定时功能。
## 支持的硬件
- ESP8266 Arduino for ESP8266
## 客户端 __API__
- `setInfo` : 根据json格式将要发送的IR指令设置好。
```
bool setInfo(char * json);
```
- `sendIR()` ： 发送已经编码的IR指令。
```
void sendIR();
```
- `setInfo`的参数 _json_ （使用时要注意转义）。
```
{
    "mode" : int类型（0表示制冷，1表示制热）,
    "switch" : int类型(0表示关闭空调，1表示打开空调),
    "wind_speed" : int类型（0表示自动，1表示一级风速，2表示二级风速，3表示三级风速）,
    "UD_scavenging" : int类型（0表示关闭上下扫风，1表示开启上下扫风）,
    "LR_scavenging" : int类型（0表示关闭左右扫风，1表示开启左右扫风）,	
    "temp" : int类型（温度的取值，取值范围为16~30）,
}

示例：
    {
        "mode" : 0,
        "switch" : 1,
        "wind_speed" : 0,
        "UD_scavenging" : 1,
        "LR_scavenging" : 1,	
        "temp" : 16,
    }
```