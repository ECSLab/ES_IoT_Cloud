//
//  iotOperation.h
//  iotSDK
//
//  Created by John Zheng on 2017/12/12.
//  Copyright © 2017年 John Zheng. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "iotPost.h"
#import "AFNetworking/AFNetworking.h"
@interface iotOperation : NSObject
@property(nonatomic,strong)NSDictionary* dictionary;
-(NSDictionary *)getDataList:(iotPost *)iotPost;  //获取所有设备数据
-(NSDictionary *)getDataLatest:(iotPost *)iotPost; //获取最近一条设备数据
-(NSDictionary *)getDataListByTime:(iotPost *)iotPost; //获取一段时间内的设备数据
-(NSString *)sendMessage:(iotPost *)iotPost;//向设备发送数据
@end
