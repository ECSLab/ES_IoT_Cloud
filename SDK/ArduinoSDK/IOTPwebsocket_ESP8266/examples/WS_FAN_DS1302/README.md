# 使用继电器控制风扇
## 此Demo的api_key = "eslabtest" device_id = "7".
## 接口格式：
```
接口的形式：
    "ON"表示此位灯灭，"OFF表示此位灯亮。
    
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
        'device_id': '7',
        'data': 'ON'}).encode('utf-8')
    pp = urllib.parse.urlencode({
        'api_key': 'eslabtest',
        'device_id': '7',
        'data': 'OFF'}).encode('utf-8')
    while(True):
        postt(posturl, dd)
        time.sleep(1)
        postt(posturl, pp)
        time.sleep(2)
```