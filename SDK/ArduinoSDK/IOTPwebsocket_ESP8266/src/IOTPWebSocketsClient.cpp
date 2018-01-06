/**
 * @file IOTPWebSocketsClient.h
 * @date 23.11.2017
 * @author MyLord
 * 
 */

#include "WebSockets.h"
#include "WebSocketsClient.h"
#include "IOTPWebSocketsClient.h"

IOTPWebSocketsClient::IOTPWebSocketsClient()
    :WebSocketsClient()
{
    this->api_key = "";
    this->device_id = 0;
    this->debugMode = false;
}

IOTPWebSocketsClient::IOTPWebSocketsClient(String api_key,long unsigned int device_id)
    :WebSocketsClient()
{
    this->api_key = api_key;
    this->device_id = device_id;
    this->debugMode = false;
}

IOTPWebSocketsClient::~IOTPWebSocketsClient()
{
    disconnect();
}

void IOTPWebSocketsClient::setDebugMode(int baud_rate)
{
    this->debugMode = true;
    Serial.begin(baud_rate);
}

void IOTPWebSocketsClient::debugInfo(String info)
{
    if(debugMode == true)
    {
        Serial.println(info);
    }
}

bool IOTPWebSocketsClient::setKeyId(String api_key,long unsigned int device_id)
{
    if(api_key == "" || device_id == 0)
    {
        debugInfo("[fault]:api_key or device_id is not legitimate!");
        return false;
    }
    this->api_key = api_key;
    this->device_id = device_id;
    return true;
}

void IOTPWebSocketsClient::begin()
{
    WebSocketsClient::begin(SERVER_IP,SERVER_PORT);
}

bool IOTPWebSocketsClient::sendTXT(String data,String time)
{
    String message = "{\"api_key\": \"";
    message += this->api_key;
    message += "\", \"device_id\": \"";
    message += String(device_id);
    message += "\", \"data\": \"";
    message += data;
    message += "\", \"time\":\"";
    message += time;
    message += "\"}";

    if(WebSocketsClient::sendTXT(message) == false)
    {
        debugInfo("[fault]:send message fault!");
        return false;
    }
    debugInfo(message);
    return true;
}