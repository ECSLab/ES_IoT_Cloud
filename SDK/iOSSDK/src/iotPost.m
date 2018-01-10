//
//  iotPost.m
//  iotSDK
//
//  Created by John Zheng on 2017/12/13.
//  Copyright © 2017年 John Zheng. All rights reserved.
//

#import "iotPost.h"

@implementation iotPost
-(NSDictionary *)Dic{  //格式NSDictionary转换为json
    NSString *apiKey=[self apiKey];
    NSString *deviceId=[self deviceId];
    NSDate *startTime=[self startTime];
    NSDate *endTime=[self endTime];
    NSString *order=[self orderBy];
    NSDictionary *dic=[[NSDictionary alloc]initWithObjects:@[apiKey!=nil?apiKey:@"",deviceId!=nil?deviceId:@"",startTime!=nil?startTime:@"",endTime!=nil?startTime:@"",order!=nil?startTime:@""] forKeys:@[@"apiKey",@"deviceId",@"startTime",@"endTime",@"order"]];
    NSLog(@"%@",dic);
    return dic;
}
-(NSString *)data{
    return data;
}
-(void)setData:(NSString *)_data{
    data=_data;
}
-(NSString *)apiKey{
    return apiKey;
}
-(void)setApiKey:(NSString *)_apiKey{
    apiKey=_apiKey;
}
-(NSString *)orderBy{
    return orderBy;
}
-(void)setOrderBy:(NSString *)_orderBy{
    orderBy=_orderBy;
}
-(NSDate *)startTime{
    return startTime;
}
-(void)setStartTime:(NSDate *)_startTime{
    startTime=_startTime;
}
-(NSDate *)endTime{
    return endTime;
}
-(void)setEndTime:(NSDate *)_endTime{
    endTime=_endTime;
}
-(NSString *)deviceId{
    return deviceId;
}
-(void)setDeviceId:(NSString *)_deviceId{
    deviceId=_deviceId;
}

@end
