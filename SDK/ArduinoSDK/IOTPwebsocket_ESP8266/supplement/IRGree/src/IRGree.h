/*
 * @file IRGree.h
 * @date 1.1.2018
 * @author MyLord
 * 
 */

#ifndef __IR_Gree__
#define __IR_Gree__

#ifndef UNIT_TEST
#include <Arduino.h>
#endif

#include <IRremoteESP8266.h>
#include <IRsend.h>
#include <ArduinoJson.h>

#define PIN 4   //An IR LED is controlled by GPIO pin 4(D2)

class IRGree{
private: 
    uint8_t message[8] = {0x00};//发送的信息
    uint8_t mode;               //模式
    uint8_t on_off;             //开关
    uint8_t LR_scavenging;      //左右扫风
    uint8_t temp;               //温度

    /*
     *  发送起始码S
     */
    void sendStartCode();

    /*
     * 发送连接码C
     */
    void sendConnectCode();

    /*
     * 发送0信号
     */
    void sendBit0();

    /*
     * 发送1信号
     */
    void sendBit1();

    /*
     * 发送指定长度的数据，高位先发送
     * @parameter：ircode：发送的数据；len：发送的长度
     */
    void sendGree(uint8_t ircode, uint8_t len);

    /*
     * 计算校验码
     */
    void setCheckCode();

public:
    IRGree();

    /*
     * 根据json格式的数据将IR指令设置好
     * @parameter：json数据
     */
    bool setInfo(char * json);

    /*
     * 发送IR指令
     */
    void sendIR();
};

#endif