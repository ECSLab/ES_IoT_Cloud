/*
 * @file IRGree.cpp
 * @date 1.1.2018
 * @author MyLord
 * 
 */

#include "IRGree.h"

static IRsend irsend(PIN);

IRGree::IRGree()
{
    irsend.begin();
    init();
}


void IRGree::init()
{
    message[0] = 0x00;
    message[1] = 0x00;
    message[2] = 0x06;
    message[3] = 0x0A;
    message[4] = 0x00;
    message[5] = 0x04;
    message[6] = 0x00;
    message[7] = 0x00;
}


void IRGree::sendStartCode()
{
    irsend.mark(9000);
    irsend.space(4500);
}


void IRGree::sendConnectCode()
{
    irsend.mark(560);
    irsend.space(10000);
    irsend.space(10000);
}


void IRGree::sendBit0()
{
    irsend.mark(560);
    irsend.space(565);
}


void IRGree::sendBit1()
{
    irsend.mark(560);
    irsend.space(1690);
}


void IRGree::sendGree(uint8_t ircode, uint8_t len)
{
    uint8_t mask = 0x80;
    for(int i = 0; i < len; i++)
    {
        if(ircode & mask)
        {
            sendBit1();
            // Serial.print("1");
        }
        else
        {
            sendBit0();
            // Serial.print("0");
        }
    mask >>= 1;
  }
}


void IRGree::setCheckCode()
{
    uint8_t tem = mode * 3 + temp - 10 + LR_scavenging - on_off; 
    uint8_t num = 0x00;
    for(int i = 0; i < 4; i++)
    {
        num <<= 1;
        num |= tem & 0x01;
        tem >>= 1;
    }
    message[7] |= num;
}


bool IRGree::setInfo(char * json)
{
    init();

    StaticJsonBuffer<255> jsonBuffer;
    JsonObject & js =jsonBuffer.parseObject(json);
    if(!js.success())
        return false;
    mode = js["mode"];
    on_off = js["switch"];
    uint8_t wind_speed = js["wind_speed"];
    uint8_t UD_scavenging = js["UD_scavenging"];
    LR_scavenging = js["LR_scavenging"];
    temp = js["temp"];

    if(mode == 0)
        message[0] |= 0x80;     //制冷
    else if(mode == 1)
        message[0] |= 0x20;     //制热
    else
        return false;
    
    if(on_off == 1)
        message[0] |= 0x10;     //开机
    else if(on_off == 0)
        message[0] &= 0xEF;     //关机
    else
        return false;

    switch(wind_speed)
    {
        case 0 : message[0] &= 0xF3; break;                     //自动
        case 1 : message[0] |= 0x08; message[0] &= 0xFB; break; //一级
        case 2 : message[0] &= 0xF7; message[0] |= 0x04; break; //二级
        case 3 : message[0] |= 0x0C; break;                     //三级
        default: return false;
    }

    if(UD_scavenging == 1 && LR_scavenging == 1)
    {
        //上下扫风，左右扫风都开启
        message[0] |= 0x02;
        message[4] = 0x88;
    }
    else if(UD_scavenging == 0 && LR_scavenging == 1)
    {
        //关闭上下扫风，开启左右扫风
        message[0] |= 0x02;
        message[4] = 0x08;
    }
    else if(UD_scavenging == 1 && LR_scavenging == 0)
    {
        //开启上下扫风，关闭左右扫风
        message[0] |= 0x02;
        message[4] = 0x80;
    }
    else if(UD_scavenging == 0 && LR_scavenging == 0)
        message[0] &= 0xFD;     //上下扫风，左右扫风都关闭
    else
        return false;

    uint8_t num = 0, tem = 0;
    tem = temp - 16;
    for(int i = 0; i < 8; i++)
    {
        num <<= 1;
        num |= tem & 0x01;
        tem >>= 1;
    }
    message[1] = num;
    
    setCheckCode();

    return true;

}

void IRGree::sendIR()
{
    int i = 0;
    irsend.enableIROut(38);
    sendStartCode();
    for(i = 0; i < 4; i++)
        sendGree(message[i], 8);
    sendGree(0x40, 3);
    sendConnectCode();
    for(i = 4; i < 8; i++)
        sendGree(message[i], 8);
    irsend.mark(560);
    irsend.space(0);
}

