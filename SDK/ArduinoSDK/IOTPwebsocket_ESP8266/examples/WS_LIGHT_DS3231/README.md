# 使用继电器控制四个灯
## 此Demo的api_key = "eslabtest" device_id = "6".
## 接口格式：
```
接口的形式是4位的字符串（只包含0/1，eg：0000）：
    0表示此位灯灭，1表示此位灯亮。
    此四位的0/1可以自由组合。
    第一位是最右边的白灯，第二位，第三位，第四位依次递推。

    eg：
        0001 表示 最左边的黑灯亮。
        1000 表示 最右边的白灯亮。
        0000 表示 全灭。
        1111 表示 全亮。
```
示例python脚本：（流水灯）
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
        'device_id': '6',
        'data': '1000'}).encode('utf-8')
    pp = urllib.parse.urlencode({
        'api_key': 'eslabtest',
        'device_id': '6',
        'data': '0100'}).encode('utf-8')
    cc = urllib.parse.urlencode({
        'api_key': 'eslabtest',
        'device_id': '6',
        'data': '0010'}).encode('utf-8')
    ff = urllib.parse.urlencode({
        'api_key': 'eslabtest',
        'device_id': '6',
        'data': '0001'}).encode('utf-8')
    while(True):
        postt(posturl, dd)
        time.sleep(1)
        postt(posturl, pp)
        time.sleep(1)
        postt(posturl, cc)
        time.sleep(1)
        postt(posturl, ff)
        time.sleep(1)

```