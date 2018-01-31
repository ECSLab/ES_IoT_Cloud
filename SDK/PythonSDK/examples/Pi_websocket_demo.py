import websocket
import threading
import time
import json
import wiringpi

broke_data = 0xffff

def DHT11():
    owpin = 7

    def getval(owpin):
        tl = []
        tb = []
        wiringpi.wiringPiSetup()
        wiringpi.pinMode(owpin, 1)
        wiringpi.digitalWrite(owpin, 1)
        wiringpi.delay(1)
        wiringpi.digitalWrite(owpin, 0)
        wiringpi.delay(25)
        wiringpi.digitalWrite(owpin, 1)
        wiringpi.delayMicroseconds(20)
        wiringpi.pinMode(owpin, 0)
        while (wiringpi.digitalRead(owpin) == 1): pass

        for i in range(45):
            tc = wiringpi.micros()

            while (wiringpi.digitalRead(owpin) == 0): pass
            while (wiringpi.digitalRead(owpin) == 1):
                if wiringpi.micros() - tc > 500:
                    break
            if wiringpi.micros() - tc > 500:
                break
            tl.append(wiringpi.micros() - tc)

        tl = tl[1:]
        for i in tl:
            if i > 100:
                tb.append(1)
            else:
                tb.append(0)

        return tb

    def GetResult(owpin):
        for i in range(10):
            SH = 0;
            SL = 0;
            TH = 0;
            TL = 0;
            C = 0
            result = getval(owpin)
            if len(result) == 40:
                for i in range(8):
                    SH *= 2;
                    SH += result[i]
                    SL *= 2;
                    SL += result[i + 8]
                    TH *= 2;
                    TH += result[i + 16]
                    TL *= 2;
                    TL += result[i + 24]
                    C *= 2;
                    C += result[i + 32]
                if ((SH + SL + TH + TL) % 256) == C and C != 0:
                    break
                else:
                    return broke_data, broke_data 
            else:
                return broke_data, broke_data
            wiringpi.delay(200)
        return SH, TH

    SH, TH = GetResult(owpin)
    return SH, TH

def GetCurrentTime():
    TimeStruct = time.localtime(time.time())
    LocalTime = time.strftime("%Y-%m-%d %H:%M:%S",TimeStruct)
    return LocalTime

def Connect_to_server(ws):
    ws.connect("ws://47.92.48.100:9000/")
    Auth = {"api_key": "eslabtest", "device_id": "3000", "data": "Hi I'm DHT11", "time": GetCurrentTime()}
    ws.send(json.dumps(Auth))

def SendMessage(ws):
    while(True):
        hum,temp = DHT11()
        if hum == broke_data:
            time.sleep(2)
            continue
        data = {"humidity": hum, "temperature": temp}
        data = json.dumps(data)
        Auth = {"api_key": "eslabtest", "device_id": "3000", "data": data, "time": GetCurrentTime()}
        Json_Send = json.dumps(Auth)
        ws.send(Json_Send)
        time.sleep(3)

def RecvFromServer(ws):
    while(True):
        jsondata = ws.recv()
        if jsondata == 'HEART_BEAT':
            time = GetCurrentTime()
            print('Heart Is Beating %s' % (time))
        else:
            print("Receive From Server: %s" % jsondata)


if __name__=='__main__':
    wiringpi.wiringPiSetup()
    ws = websocket.WebSocket()
    while(True):
        try:
            Connect_to_server(ws)
            send_thread = threading.Thread(target = SendMessage, args=(ws,))
            recv_thread = threading.Thread(target = RecvFromServer, args=(ws,))
        except:
            print("Restart connecting...")
            time.sleep(3)
            continue
        send_thread.start()
        recv_thread.start()
        send_thread.join()
        recv_thread.join()
