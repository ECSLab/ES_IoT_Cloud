/*
 * ArduinoDemo for test,using lib ESP8266WiFi and DS3231;
 * 
 * A quick demo of how to use my IOTPwebsocket-library to auth dev and send 
 * messages.
 * 
 * I assume you know how to connect the ds3231 and relays.
 *
 * CONNECTIONS:
 *        DS3231 SDA --> SDA
 *        DS3231 SCL --> SCL
 *        DS3231 VCC --> 3.3v or 5v
 *        DS3231 GND --> GND
 *        
 *        LIGHT1_RELAY_PIN --> GPIO0
 *        LIGHT2_RELAY_PIN --> GPIO13
 *        LIGHT3_RELAY_PIN --> GPIO12
 *        LIGHT4_RELAY_PIN --> GPIO14
 *        
 * 
 * Created on :2017.12.23
 */

#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <IOTPWebSocketsClient.h>
#include <string.h>
#include <Wire.h>
#include <RtcDS3231.h>


#define SSID                  "ESLAB_508"
#define PWD                   "weigechaoshuai"
#define HEART_BEAT            "HEART_BEAT"
#define countof(a) (sizeof(a) / sizeof(a[0]))

#define LIGHT1_RELAY_PIN  0
#define LIGHT2_RELAY_PIN  13
#define LIGHT3_RELAY_PIN  12
#define LIGHT4_RELAY_PIN  14
 

RtcDS3231<TwoWire> Rtc(Wire);
char state[5] = "0000";
IOTPWebSocketsClient webSocket("eslabtest",6);
char timeinfo[20];


char * getTimeInfo()
{   
    if (!Rtc.IsDateTimeValid()) 
    {
        Serial.println("RTC lost confidence in the DateTime!");
    }
    RtcDateTime now = Rtc.GetDateTime();

    snprintf_P(timeinfo, 
            countof(timeinfo),
            PSTR("%04u-%02u-%02u %02u:%02u:%02u"),
            now.Year(),
            now.Month(),
            now.Day(),
            now.Hour(),
            now.Minute(),
            now.Second());
    
    return timeinfo;
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
        webSocket.sendTXT("IOTPWebSocketClient test for lights and fan!", getTimeInfo());
    }
        break;
    case WStype_TEXT:
    {
        if(strcmp((char *)payload,HEART_BEAT) == 0)
        {
            Serial.printf("[OK]%s: Receive heart beat！\n", getTimeInfo());
            // send message to server
            webSocket.sendTXT(state, getTimeInfo());
        }
        else
        {
            Serial.printf("[Message]%s: get message: %s\n", getTimeInfo(), payload);
            //light1 state;
            if(strlen((char *)payload) != 4)
                break;
            if(payload[0] == '1')
            {
                state[0] = '1';
                digitalWrite(LIGHT1_RELAY_PIN, HIGH);
            }else if(payload[0] == '0')
            {
                state[0] = '0';
                digitalWrite(LIGHT1_RELAY_PIN, LOW);
            }

            //light2 state;
            if(payload[1] == '1')
            {
                state[1] = '1';
                digitalWrite(LIGHT2_RELAY_PIN, HIGH);
            }else if(payload[1] == '0')
            {
                state[1] = '0';
                digitalWrite(LIGHT2_RELAY_PIN, LOW);
            }

            //light3 state;
            if(payload[2] == '1')
            {
                state[2] = '1';
                digitalWrite(LIGHT3_RELAY_PIN, HIGH);
            }else if(payload[2] == '0')
            {
                state[2] = '0';
                digitalWrite(LIGHT3_RELAY_PIN, LOW);
            }

            //light4 state;
            if(payload[3] == '1')
            {
                state[3] = '1';
                digitalWrite(LIGHT4_RELAY_PIN, HIGH);
            }else if(payload[3] == '0')
            {
                state[3] = '0';
                digitalWrite(LIGHT4_RELAY_PIN, LOW);
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
    Serial.begin()

    Rtc.Begin();
    Serial.print(getTimeInfo());

    pinMode(LIGHT1_RELAY_PIN, OUTPUT);
    pinMode(LIGHT2_RELAY_PIN, OUTPUT);
    pinMode(LIGHT3_RELAY_PIN, OUTPUT);
    pinMode(LIGHT4_RELAY_PIN, OUTPUT);;

    digitalWrite(LIGHT1_RELAY_PIN, LOW);
    digitalWrite(LIGHT2_RELAY_PIN, LOW);
    digitalWrite(LIGHT3_RELAY_PIN, LOW);
    digitalWrite(LIGHT4_RELAY_PIN, LOW);

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
}

void loop()
{
    webSocket.loop();
}
