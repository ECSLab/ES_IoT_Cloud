#!/usr/bin/env python3
#-*- coding: utf-8 -*-

'ESLAB_IOT Module For RaspberryPi'

__author__ = 'Kid Wu'

import time
import threading
import wiringpi
import websocket
import json
import urllib.parse
import urllib.request

class DHT11(object):
    def __init__(self,owpin):
        """
        Test DEMO
        :param owpin: get data from this GPIO pin
        """
        self.owpin = owpin

    def getval(self):
        tl = []
        tb = []
        wiringpi.wiringPiSetup()
        wiringpi.pinMode(self.owpin, 1)
        wiringpi.digitalWrite(self.owpin, 1)
        wiringpi.delay(1)
        wiringpi.digitalWrite(self.owpin, 0)
        wiringpi.delay(25)
        wiringpi.digitalWrite(self.owpin, 1)
        wiringpi.delayMicroseconds(20)
        wiringpi.pinMode(self.owpin, 0)
        while (wiringpi.digitalRead(self.owpin) == 1): pass

        for i in range(45):
            tc = wiringpi.micros()

            while (wiringpi.digitalRead(self.owpin) == 0): pass
            while (wiringpi.digitalRead(self.owpin) == 1):
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

    def checksum(self):
        for i in range(10):
            SH = 0
            SL = 0
            TH = 0
            TL = 0
            C = 0
            result = self.getval()
            if len(result) == 40:
                for i in range(8):
                    SH *= 2
                    SH += result[i]
                    SL *= 2
                    SL += result[i + 8]
                    TH *= 2
                    TH += result[i + 16]
                    TL *= 2
                    TL += result[i + 24]
                    C *= 2
                    C += result[i + 32]
                if ((SH + SL + TH + TL) % 256) == C and C != 0:
                    break
                else:
                    print("Read Sucess,But checksum error! retrying")
            else:
                print("Read failer! Retrying")
            wiringpi.delay(200)
        return SH, SL, TH, TL

    def getresult(self):
        sh,sl,th,tl = self.checksum()
        return "humidity : %d.%d, temperature : %d.%d" %(sh, sl, th, tl)


class Humidity_detect(object):
    def __init__(self, hdpin):
        """
            there is no AD transfer module
            so it can only print wet or dry
            :param owgin: get data from this GPIO pin
        """
        self.hdpin = hdpin

    def wet_or_dry(self):
        wiringpi.wiringPiSetup()
        wiringpi.digitalRead(self.hdpin)
        if self.hdpin:
            status = "dry"
        else:
            status = "wet"
        return status

class LocalTime(object):

    #This class doesn't to be initialized

    def get_current_time(self):
        TimeStruct = time.localtime(time.time())
        LocalTime = time.strftime("%Y-%m-%d %H:%M:%S", TimeStruct)
        return LocalTime

class HTTPPost(object):
    def __init__(self, ip_address = "127.0.0.1", port = 9006,
                 api_key = None, device_id = None,
                 user_data = "Hi I'm Python",
                 dev_num = None,sleep_time = 3):
        """
        ip_address and port has default value
        :param ip_address: default_value "127.0.0.1"
        :param port: default_value "9006"
        """
        print('The Default ip_address is %s\nThe Default port is %d'
              % (ip_address,port))
        self.ip_address = ip_address
        self.port = port
        self.user_data = user_data
        self.api_key = api_key
        self.Device_id = device_id
        self.user_data = user_data
        self.Dev_num = dev_num
        self.sleep_time = sleep_time

    def messages_send_to_server(self):
        local_time = LocalTime().get_current_time()
        values = {"Api_key": self.api_key,
                  "Device_id": self.Device_id,
                  "Data": self.user_data,
                  "Dev_num": self.Dev_num,
                  "Create_time": local_time}
        return values

    def post_forever(self):
        url = 'http://' + self.ip_address + ':' + str(self.port)
        values = self.messages_send_to_server()
        values = urllib.parse.urlencode(values).encode('utf-8')
        while (True):
            try:
                req = urllib.request.Request(url, values)
                response = urllib.request.urlopen(req)
                return response
            except:
                print("Post Error.\nRePost")
            finally:
                values = self.messages_send_to_server()
                values = urllib.parse.urlencode(values).encode('utf-8')
                time.sleep(self.sleep_time)

class Websocket(object):
    def __init__(self, ip_address = "127.0.0.1",
                 port = 9000, api_key = None, dev_id = None,
                 data = 'Hi I,m Python',sleep_time = 3):
        """
        :param ws: websocket object
        :param ip_address: the default value is "127.0.0.1"
        :param port: the default value is 9000
        """
        self.ws = websocket.WebSocket()
        self.ip_address = ip_address
        self.port = port
        self.api_key = api_key
        self.dev_id = dev_id
        self.data = data
        self.sleep_time = sleep_time


    def connect_to_server(self):
        current_time = LocalTime().get_current_time()
        self.ws.connect("ws://" + self.ip_address + ':'+ str(self.port) + '/' )
        Auth = {"api_key": self.api_key,
                "device_id": self.dev_id,
                "data": "Hi I'm Python",
                "time": current_time}
        self.ws.send(json.dumps(Auth))

    def recv_from_server(self):
        while (True):
            jsondata = self.ws.recv()
            if jsondata == 'HEART_BEAT':
                status = 'Heart Is Beating'
            else:
                status = ("Receive From Server:" + jsondata)
            return status

    def send_message(self, data):
        while (True):
            current_time = LocalTime().get_current_time()
            time.sleep(self.sleep_time)
            Auth = {"api_key": self.api_key,
                    "device_id": self.dev_id,
                    "data": data,
                    "time": current_time}
            json_send = json.dumps(Auth)
            self.ws.send(json_send)

    def run_forever(self, data):
        while (True):
            try:
                self.connect_to_server()
            except:
                print("Restart connecting...")
                time.sleep(3)
                continue
            send_thread = threading.Thread(target=self.send_message, args=(data,))
            recv_thread = threading.Thread(target=self.recv_from_server, args=())
            send_thread.start()
            recv_thread.start()
            send_thread.join()
            recv_thread.join()