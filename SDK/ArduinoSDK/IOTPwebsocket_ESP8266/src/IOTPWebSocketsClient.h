/**
 * @file IOTPWebSocketsClient.h
 * @date 23.11.2017
 * @author MyLord
 * 
 */

#ifndef IOTPWEBSOCKETCLIENT_H_
#define IOTPWEBSOCKETCLIENT_H_

#include "WebSocketsClient.h"
#include <string>
#include <sstream>

using namespace std;

#define DEFAULT_TIME "2017-11-11 11:11:11"
#define SERVER_IP    "192.168.43.7"
#define SERVER_PORT  9000

class IOTPWebSocketsClient: public WebSocketsClient {
    private:

        String api_key;
        long unsigned int device_id;    
        bool debugMode; 

    public:

        IOTPWebSocketsClient();
        IOTPWebSocketsClient(String api_key,long unsigned int device_id);
        
        ~IOTPWebSocketsClient();

        /**
         * Turn on debug mode.This kind of behavior will print debug info
         * on serial.
         * @parameter:baud rate
         */
        void setDebugMode(int baud_rate);

        /**
         * Print debug info on serial.
         * @parameter:infomation
         */
        void debugInfo(String info);

        /**
         * Set your device id and api key.
         * @parameter:device api key;
         * @parameter:device id;
         * @return:success:true;
         *         fail:false.
         */
        bool setKeyId(String api_key,long unsigned int device_id);

        /**
         * Build connection to server.Call the begin() function of the
         * superclass. server ip and port are fixed with ServerIP and
         * ServerPort.
         */
        void begin();

        /**
         * Send message to server.
         * @parameter:Message you want to send;
         * @parameter:Time to send message,if you can't get the real time
         *            you will send the default time.
         * @return:success：ture；
         *         fail    :false.
         */
        bool sendTXT(String data,String time = DEFAULT_TIME); 
        
};


#endif

