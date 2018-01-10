//
//  iotOperation.m
//  iotSDK
//
//  Created by John Zheng on 2017/12/12.
//  Copyright © 2017年 John Zheng. All rights reserved.
//

#import "iotOperation.h"
#import "AFNetworking.h"

@implementation iotOperation
static int flag;
//宏定义成功block 回调成功后得到的信息
typedef void (^HttpSuccess)(id data);
//宏定义失败block 回调失败信息
typedef void (^HttpFailure)(NSError *error);
-(NSDictionary *)getDataList:(iotPost *)iotPost{
    flag=1;
    dispatch_group_t group = dispatch_group_create();
    NSString *URL=@"http://47.92.48.100:8099/iot/sdk/device/data";
    static NSDictionary *data;
    AFHTTPSessionManager *manager=[AFHTTPSessionManager   manager];
    manager = [[AFHTTPSessionManager alloc] initWithBaseURL:[NSURL URLWithString:URL]];
    manager.requestSerializer.timeoutInterval=120;
    [manager.requestSerializer setStringEncoding:NSUTF8StringEncoding];
    [manager.requestSerializer setValue:@"application/x-www-form-urlencoded; charset=utf-8" forHTTPHeaderField:@"Content-Type"];
    NSDictionary * dic=@{
                         @"apiKey" : [iotPost apiKey],
                         @"deviceId":[iotPost deviceId],
                         @"order"   :   [iotPost orderBy]!=nil?[iotPost orderBy]:@"asc"
                         };
    dispatch_group_enter(group);
    [manager POST:URL parameters:dic  progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        _dictionary=[NSDictionary dictionaryWithDictionary:responseObject];
        dispatch_group_leave(group);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        dispatch_group_leave(group);
        NSLog(@"%@",error);
    }];
    dispatch_group_notify(group, dispatch_get_main_queue(), ^(){
        flag=0;
    });
    while (flag == 1) {
        [[NSRunLoop currentRunLoop] runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];
    }
    return data;
}
-(NSDictionary *)getDataLatest:(iotPost *)iotPost{
    flag=1;
    dispatch_group_t group = dispatch_group_create();
    NSString *URL=@"http://47.92.48.100:8099/iot/sdk/device/data/latest";
    AFHTTPSessionManager *manager=[AFHTTPSessionManager   manager];
    manager = [[AFHTTPSessionManager alloc] initWithBaseURL:[NSURL URLWithString:URL]];
    manager.requestSerializer.timeoutInterval=120;
    [manager.requestSerializer setStringEncoding:NSUTF8StringEncoding];
    [manager.requestSerializer setValue:@"application/x-www-form-urlencoded; charset=utf-8" forHTTPHeaderField:@"Content-Type"];
    NSDictionary * dic=@{
                         @"apiKey" : [iotPost apiKey],
                         @"deviceId":[iotPost deviceId],
                         };
    dispatch_group_enter(group);
    [manager POST:URL parameters:dic  progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        _dictionary=[NSDictionary dictionaryWithDictionary:responseObject];
        dispatch_group_leave(group);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"%@",error);
        dispatch_group_leave(group);
    }];
    dispatch_group_notify(group, dispatch_get_main_queue(), ^(){
        flag=0;
    });
    while (flag == 1) {
        [[NSRunLoop currentRunLoop] runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];
    }
    return _dictionary;
}
-(NSDictionary *)getDataListByTime:(iotPost *)iotPost{
    flag=1;
     dispatch_group_t group = dispatch_group_create();
    NSString *URL=@"http://47.92.48.100:8099/iot/sdk/device/data/time";
    NSDateFormatter *formatter =[[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd hh:mm:ss"];
    NSString *apiKey=[iotPost apiKey];
    NSString *deviceId=[iotPost deviceId];
    NSString *order=[iotPost orderBy];
    if(order==nil)
        order=@"asc";
    NSString *startTime=[formatter stringFromDate:[iotPost startTime]];
    NSString *endTime=[formatter stringFromDate:[iotPost endTime]];
    NSDictionary * dic=@{
                         @"apiKey":apiKey,
                         @"deviceId":deviceId,
                         @"order":order,
                         @"startTime":startTime!=nil?startTime:@"",
                         @"endTime":endTime!=nil?endTime:@""
                            };
    dispatch_group_enter(group);
    [self postWithUrlString:URL parameters:dic success:^(id data){
        _dictionary=[NSDictionary dictionaryWithDictionary:data];
        dispatch_group_leave(group);
    } failure:^(NSError *error){
        NSLog(@"%@",error);
        dispatch_group_leave(group);
    }];
    dispatch_group_notify(group, dispatch_get_main_queue(), ^(){
        flag=0;
    });
    while (flag == 1) {
        [[NSRunLoop currentRunLoop] runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];
    }
    return _dictionary;
}
-(NSString *)sendMessage:(iotPost *)iotPost{
    flag=1;
    dispatch_group_t group = dispatch_group_create();
    NSString *URL=@"http://47.92.48.100:9000/upload";
    NSDictionary * dic=@{
                        @"api_key" : [iotPost apiKey]        ,
                         @"device_id":[iotPost deviceId],
                         @"data" : [iotPost data]
                         };
    static NSString *string ;
    AFHTTPSessionManager *manager=[AFHTTPSessionManager   manager];
    manager = [[AFHTTPSessionManager alloc] initWithBaseURL:[NSURL URLWithString:URL]];
    manager.requestSerializer.timeoutInterval=120;
    manager.responseSerializer=[AFHTTPResponseSerializer serializer];
    [manager.requestSerializer setValue:@"application/x-www-form-urlencoded; charset=utf-8" forHTTPHeaderField:@"Content-Type"];
    NSDateFormatter *formatter =[[NSDateFormatter alloc]init];
    [formatter setDateFormat:@"yyyy-MM-dd hh:mm:ss"];
    NSString *order=[iotPost orderBy];
    if(order==nil)
        order=@"asc";
    dispatch_group_enter(group);
    [manager POST:URL parameters:dic  progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        
        dispatch_group_leave(group);
        string = [[NSString alloc] initWithData:responseObject encoding:NSUTF8StringEncoding];
        //dataArray=[NSArray arrayWithArray:[data valueForKey:@"data"]];
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"%@",error);
        dispatch_group_leave(group);
    }];
    dispatch_group_notify(group, dispatch_get_main_queue(), ^(){
        flag=0;
    });
    while (flag == 1) {
        [[NSRunLoop currentRunLoop] runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];
    }
    NSLog(@"%@",string);
    return string;
}
-(NSString *)convertToJsonData:(NSDictionary *)dict

{
    
    NSError *error;
    
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dict options:NSJSONWritingPrettyPrinted error:&error];
    
    NSString *jsonString;
    
    if (!jsonData) {
        
        NSLog(@"%@",error);
        
    }else{
        
        jsonString = [[NSString alloc]initWithData:jsonData encoding:NSUTF8StringEncoding];
        
    }
    
    NSMutableString *mutStr = [NSMutableString stringWithString:jsonString];
    
    NSRange range = {0,jsonString.length};
    
    [mutStr replaceOccurrencesOfString:@" " withString:@"" options:NSLiteralSearch range:range];
    
    NSRange range2 = {0,mutStr.length};
    
    [mutStr replaceOccurrencesOfString:@"\n" withString:@"" options:NSLiteralSearch range:range2];
    
    return mutStr;
    
}
-(void)postWithUrlString:(NSString *)urlString parameters:(NSDictionary *)parameters success:(HttpSuccess)success failure:(HttpFailure)failure{
    //创建请求管理者
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    //
    manager = [[AFHTTPSessionManager alloc] initWithBaseURL:[NSURL URLWithString:urlString]];
    manager.requestSerializer.timeoutInterval=120;
    [manager.requestSerializer setStringEncoding:NSUTF8StringEncoding];
    [manager.requestSerializer setValue:@"application/x-www-form-urlencoded; charset=utf-8" forHTTPHeaderField:@"Content-Type"];
    //post请求
    [manager POST:urlString parameters:parameters progress:^(NSProgress * _Nonnull uploadProgress) {
        //数据请求的进度
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        success(responseObject);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        failure(error);
    }];
}
@end
