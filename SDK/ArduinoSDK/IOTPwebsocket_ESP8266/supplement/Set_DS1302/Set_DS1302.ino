/*
 * set ds 1302 time in this .ino
 * 
 * This two lines: 
 *    rtc.setTime(12, 0, 0);
 *    rtc.setDate(6, 8, 2017); 
 * 
 */

#include <DS1302.h>

#define RST         0
#define DAT         2
#define CLK         15

// Init the DS1302
DS1302 rtc(RST,DAT,CLK);

void setup()
{
  // Set the clock to run-mode, and disable the write protection
  rtc.halt(false);
  rtc.writeProtect(false);
  
  // Setup Serial connection
  Serial.begin(115200);

  // The following lines can be commented out to use the values already stored in the DS1302
 // rtc.setDOW(FRIDAY);        // Set Day-of-Week to FRIDAY
  rtc.setTime(12, 0, 0);     // Set the time to 12:00:00 (24hr format)
  rtc.setDate(6, 8, 2017);   // Set the date to August 6th, 2017
}

char * get()
{
  Time t;
  t=rtc.getTime();
  int yr=t.year;
  char * output = "xxxxxxxxxx";
  output[0]=char((yr / 1000)+48);
  output[1]=char(((yr % 1000) / 100)+48);
  output[2]=char(((yr % 100) / 10)+48);
  output[3]=char((yr % 10)+48);
  output[4]='-';
  if(t.mon<10)
    output[5] = 48;
  else
    output[5] = char((t.mon % 10) + 48);
  output[6]=char((t.mon % 10)+48);
  output[7]='-';
  if (t.date<10)
    output[8]=48;
  else
    output[8]=char((t.date / 10)+48);
    output[9]=char((t.date % 10)+48);
    output[10]=0;
  return output;
}

void loop()
{
  // Send Day-of-Week
  Serial.print(rtc.getDOWStr());
  Serial.print(" ");
  
  // Send date
  Serial.print(get());
  Serial.print(" -- ");

  // Send time
  Serial.println(rtc.getTimeStr());
  
  // Wait one second before repeating :)
  delay (1000);
}
