//
//  iotPost.h
//  iotSDK
//
//  Created by John Zheng on 2017/12/13.
//  Copyright © 2017年 John Zheng. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface iotPost : NSObject{
@private
    NSString *apiKey;
@private
    NSDate *startTime;
@private
    NSDate *endTime;
@private
    NSString *orderBy;
@private
    NSString *deviceId;
@private
    NSString *data;
}
-(NSString *)data;
-(void)setData:(NSString *)_data;
-(NSDictionary *)Dic;
-(NSString *)apiKey;
-(void)setApiKey:(NSString *)_apiKey;
-(NSDate *)startTime;
-(void)setStartTime:(NSDate *)_startTime;
-(NSDate *)endTime;
-(void)setEndTime:(NSDate *)_endTime;
-(NSString *)orderBy;
-(void)setOrderBy:(NSString *)_orderBy;
-(NSString *)deviceId;
-(void)setDeviceId:(NSString *)_deviceId;
@end
