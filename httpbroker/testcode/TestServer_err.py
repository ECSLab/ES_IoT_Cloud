import requests

postdata = {'Api_key':'sdakasyri', 'Device_id':'2fse',
                           'Data':'Hello', 'Dev_num':1, 'Create_time':'2017-11-24'}

r = requests.post("http://localhost:9006", data = postdata)
print(r.text)
