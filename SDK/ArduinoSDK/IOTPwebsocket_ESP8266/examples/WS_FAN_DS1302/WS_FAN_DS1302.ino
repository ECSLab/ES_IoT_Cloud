 /*
 * ArduinoDemo for test,using lib ESP8266WiFi and DS1302;
 * 
 * A quick demo of how to use my IOTPwebsocket-library to auth dev and send 
 * messages.
 * 
 * I assume you know how to connect the ds1302 and SDS011.
 *  * CONNECTIONS:
 *     DS1302:
 *        DS1302 RST --> GPIO0
 *        DS1302 DAT --> GPIO2
 *        DS1302 CLK --> GPIO15
 *        DS1302 VCC --> 3.3v
 *        DS1302 GND --> GND
 *     RELAY --> GPIO13
 * 
 * Created on :2017.12.24
 */

#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <DS1302.h>
#include <Esp.h>
#include <IOTPWebSocketsClient.h>
#include <string.h>
#include <stdlib.h>

#define SSID    "ESLAB_508"
#define PWD     "weigechaoshuai"
#define HEART_BEAT "HEART_BEAT"

#define RST         0
#define DAT         2
#define CLK         15
#define RELAY_PIN   13


DS1302 rtc(RST,DAT,CLK);
IOTPWebSocketsClient webSocket("eslabtest",8);

double pm25 = 0;
double pm10 = 0;
char state[] = "OFF";

char * getTimeInfo()
{
    Time t;
    t=rtc.getTime();
    int yr=t.year;
    char * output = "2017-11-11 11:11:11";
    output[0]=char((yr / 1000)+48);
    output[1]=char(((yr % 1000) / 100)+48);
    output[2]=char(((yr % 100) / 10)+48);
    output[3]=char((yr % 10)+48);
    output[4]='-';
    if(t.mon<10)
        output[5] = 48;
    else
        output[5] = char((t.mon / 10) + 48);
    output[6]=char((t.mon % 10)+48);
    output[7]='-';
    if (t.date<10)
        output[8]=48;
    else
        output[8]=char((t.date / 10)+48);
    output[9]=char((t.date % 10)+48);
    output[10]=0;
    strcat(output," ");
    strcat(output,rtc.getTimeStr());
    return output;
}

void webSocketEvent(WStype_t type,uint8_t * payload,size_t length)
{
  switch(type)
  {
    case WStype_DISCONNECTED:
        {
            Serial.printf("[FAIL]%s: Disconnected to server!\n", getTimeInfo());
            if(WiFi.status() != WL_CONNECTED)
            {
                Serial.printf("[failed]%s:Disconnect to WiFi\n",getTimeInfo());
            }
            Serial.printf("Reconnecting");
            while(WiFi.status() != WL_CONNECTED)
            {
                delay(500);
                Serial.print(".");
            }
        }
        break;
    case WStype_CONNECTED: 
    {
        Serial.printf("[OK]%s: Connected to url: %s\n", getTimeInfo(), payload);
        
        // send message to server when Connected
        webSocket.sendTXT("IOTPWebSocketClient test!", getTimeInfo());
    }
        break;
    case WStype_TEXT:
    {
        if(strcmp((char *)payload,HEART_BEAT) == 0)
        {
            webSocket.sendTXT(state, getTimeInfo());
        }
        else
        {
            Serial.printf("[Message]%s: get message: %s\n", getTimeInfo(), payload);
            if(strcmp((char *)payload, "ON") == 0)
            {
                pinMode(RELAY_PIN, OUTPUT);
                digitalWrite(RELAY_PIN, HIGH);
                strcpy(state, "ON");
            }
            else if(strcmp((char *)payload, "OFF") == 0)
            {
                pinMode(RELAY_PIN, INPUT);
                strcpy(state, "OFF");
            }
        }
    }
    break;
  default:
    {
        Serial.printf("Default!\n");
    }
    break;
  }

}

void setup()
{
//    webSocket.setDebugMode(9600);
    Serial.begin(9600);

    pinMode(RELAY_PIN, INPUT);

    rtc.halt(false);
    rtc.writeProtect(false);

    Serial.println(getTimeInfo());

    Serial.print("Attempting to connect to ");
    Serial.println(SSID);
    WiFi.begin(SSID, PWD);
    Serial.print("Connecting");
    while(WiFi.status() != WL_CONNECTED)
    {
        delay(500);
        Serial.print(".");
    }
    Serial.println("Connected to wifi");

    webSocket.begin();

    webSocket.onEvent(webSocketEvent);

    webSocket.setReconnectInterval(2);
}

void loop()
{
    webSocket.loop();
}
