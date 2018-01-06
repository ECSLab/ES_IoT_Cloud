# 使用红外模块控制格力空调
## 此Demo的api_key = "eslabtest" device_id = "9".
## 接口格式：
```
接口的形式：
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
示例python脚本：
```
import urllib.parse
import urllib.request
import time

def postt(posturl, data):
    req = urllib.request.Request(posturl, data)
    return urllib.request.urlopen(req)

if __name__ == '__main__':
    posturl = 'http://47.92.48.100:9000/upload'
    dd =  urllib.parse.urlencode({
        'api_key': 'eslabtest',
        'device_id': '9',
        'data': '{"mode":1,"switch":1,"wind_speed":1,"UD_scavenging":1,"LR_scavenging":1,"temp":19}'}).encode('utf-8')
    while(True):
        postt(posturl, dd)
        time.sleep(3)
```