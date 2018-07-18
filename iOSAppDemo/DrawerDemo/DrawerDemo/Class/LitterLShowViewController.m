

#import "LitterLShowViewController.h"
#import "MMDrawerBarButtonItem.h"
#import "UIViewController+MMDrawerController.h"
#import "AFNetworking.h"
#import "iotPost.h"
#import "iotOperation.h"
@interface LitterLShowViewController ()
@property(nonatomic,strong)iotPost *post;
@property(nonatomic,strong)iotOperation *op;
@property(nonatomic,strong)NSString *str;
@end

@implementation LitterLShowViewController
NSUInteger defaultTemp=18;
NSUInteger mode=0,Switch=0,wind_speed=1,UD_scavenging=1,LR_scavenging=1;
- (void)viewDidLoad {
    [super viewDidLoad];
    _post=[[iotPost alloc]init];
    _op=[[iotOperation alloc]init];
    [_post setApiKey:@"eslabtest"];
    self.view.backgroundColor = [UIColor whiteColor];
}
-(void)judgeImage:(UIImageView *)iview{
    if([self.title isEqualToString:@"灯组"]){
        if([_str isEqualToString:@"0000"])
            iview.image=[UIImage imageNamed:@"lightbulb_off.9"];
        else
            iview.image=[UIImage imageNamed:@"lightbulb_on.9"];
    }
    else if([self.title isEqualToString:@"风扇"]){
        [UIView animateWithDuration:0.1 animations:^{
            // 设置旋转角度
            iview.transform = CGAffineTransformRotate(iview.transform, M_PI_2);
            
        } completion:^(BOOL finished) {
            
            // 旋转结束时调用旋转方法
            [self rorateAnimatetion:iview];
        }];
    }
    else if([self.title isEqualToString:@"实验室空调"]){
        UIImageView *modeImg=[[UIImageView alloc]initWithFrame:CGRectMake(iview.bounds.size.width-100, iview.bounds.size.height-100 , 50, 50)];
        if(mode==1){
            modeImg.image=[UIImage imageNamed:@"hot"];
        }else{
            modeImg.image=[UIImage imageNamed:@"cold"];
        }
        [iview addSubview:modeImg];
        UIImageView *windImg=[[UIImageView alloc]initWithFrame:CGRectMake(30, iview.bounds.size.height-40, 40, 40)];
        if(wind_speed==0){
            windImg.image=[UIImage imageNamed:@"auto_selected"];
        }else if(wind_speed==1){
            windImg.image=[UIImage imageNamed:@"air_wind1"];
        }else if(wind_speed==2){
            windImg.image=[UIImage imageNamed:@"air_wind3"];
        }else if(wind_speed==3){
            windImg.image=[UIImage imageNamed:@"air_wind5"];
        }
        [iview addSubview:windImg];
        UIImageView *LRImg=[[UIImageView alloc]initWithFrame:CGRectMake(80, iview.bounds.size.height-60, 80, 80)];
        if(LR_scavenging==1){
            LRImg.image=[UIImage imageNamed:@"wind_horizontal_pressed"];
            [iview addSubview:LRImg];
        }
        UIImageView *UDImg=[[UIImageView alloc]initWithFrame:CGRectMake(140, iview.bounds.size.height-60, 80, 80)];
        if(UD_scavenging==1){
            UDImg.image=[UIImage imageNamed:@"wind_vertical_pressed"];
            [iview addSubview:UDImg];
        }
    }
}
- (void)rorateAnimatetion:(UIImageView *)iview
{
    if([_str isEqualToString:@"ON"]){
        [UIView animateWithDuration:0.1 animations:^{
            
            // 设置旋转角度
            iview.transform = CGAffineTransformRotate(iview.transform, M_PI_4);
            
        } completion:^(BOOL finished) {
            
            // 旋转结束时调用旋转方法
            [self rorateAnimatetion:iview];
        }];
    }
}
-(void)changeSta:(UIButton *)btn{
    NSLog(@"%@",self.title);
    btn.enabled=NO;
    [self performSelector:@selector(changeButtonStatus:) withObject:nil afterDelay:1.0f];//防止用户重复点击
    if([self.title isEqualToString:@"风扇"]){
        _post =[[iotPost alloc]init];
        _op=[[iotOperation alloc]init];
        [_post setApiKey:@"eslabtest"];
        [_post setDeviceId:@"8"];
        NSString *data;
        if([_str isEqualToString:@"ON"]){
            data=@"OFF";
        }
        else{
            data=@"ON";
        }
        NSLog(@"%@",data);
//        [_post setData:data];
//        NSString *result=[_op sendMessage:_post];
//        if([result isEqualToString:@"DEV_NOT_ONLINE"]){
//            UIAlertController *alert=[[UIAlertController alloc]init];
//            [alert setMessage:@"该设备暂不在线"];
//            [alert actions];
//        }else if([result isEqualToString:@"PUBLISH_SUCCESS"]){
//            _str=data;
//
//        }
        [self afterAppear];
    }else if([self.title isEqualToString:@"实验室空调"]){
        [_post setApiKey:@"eslabtest"];
        [_post setDeviceId:@"9"];
        if(btn.tag==1){
            if(Switch==1){
                Switch=0;
            }else{
                Switch=1;
            }
        }else if(btn.tag==2){
            if(mode==1)
                mode=0;
            else
                mode=1;
        }else if(btn.tag==3){
            if(wind_speed<3){
                wind_speed++;
            }else
                wind_speed=0;
        }else if (btn.tag==4){
            if(LR_scavenging==0){
                LR_scavenging=1;
            }else
                LR_scavenging=0;
        }else if (btn.tag==5){
            if(UD_scavenging==0)
                UD_scavenging=1;
            else
                UD_scavenging=0;
        }else if (btn.tag==6){
            if(defaultTemp>16){
                defaultTemp--;
            }
        }else if(btn.tag==7){
            if(defaultTemp<30){
                defaultTemp++;
            }
        }
        NSDictionary *dic=[[NSDictionary alloc]init];
        dic=@{
              @"mode":[NSString stringWithFormat:@"%lu",mode],
              @"switch":[NSString stringWithFormat:@"%lu",Switch],
              @"wind_speed":[NSString stringWithFormat:@"%lu",wind_speed],
              @"LR_scavenging":[NSString stringWithFormat:@"%lu",LR_scavenging],
              @"UD_scavenging":[NSString stringWithFormat:@"%lu",UD_scavenging],
              @"temp":[NSString stringWithFormat:@"%lu",defaultTemp]
              };
        NSString *data=[self dictionaryToJson:dic];
        //[_post setData:data];
        //[self afterAppear];
        //        NSString *result=[_op sendMessage:_post];
        //        if([result isEqualToString:@"DEV_NOT_ONLINE"]){
        //            UIAlertController *alert=[[UIAlertController alloc]init];
        //            [alert setMessage:@"该设备暂不在线"];
        //            [alert actions];
        //        }else if([result isEqualToString:@"PUBLISH_SUCCESS"]){
        //            _str=data;
        //            [self afterAppear];
        //        }
    }
    
}
- (NSString*)dictionaryToJson:(NSDictionary *)dic
{
    NSError *parseError = nil;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dic options:NSJSONWritingPrettyPrinted error:&parseError];
    return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}
-(void)openOrBlock:(UIButton *)btn{
    btn.enabled = NO;
    [self performSelector:@selector(changeButtonStatus:) withObject:nil afterDelay:1.0f];//防止用户重复点击
    _post=[[iotPost alloc]init];
    _op=[[iotOperation alloc]init];
    [_post setApiKey:@"eslabtest"];
    [_post setDeviceId:@"6"];
    NSString *data;
    if(btn.tag==1){
        if([_str isEqualToString:@"0000"]){
            data=@"0001";
        }
        else{
            data=@"0000";
        }
    }else if(btn.tag==2){
        data=@"1000";
    }
    else
        data=@"1111";
 //   [_post setData:data];
//   NSString *result=[_op sendMessage:_post];
//    if([result isEqualToString:@"DEV_NOT_ONLINE"]){
//        UIAlertController *alert=[[UIAlertController alloc]init];
//        [alert setMessage:@"该设备暂不在线"];
//        [alert actions];
//    }else if([result isEqualToString:@"PUBLISH_SUCCESS"]){
//        _str=data;
//        [self afterAppear];
//    }
}
-(void)changeButtonStatus:(UIButton *)btn{
    btn.enabled = YES;
}
-(void)afterAppear{
    if([self.title isEqualToString:@"灯组"]){
        UIImageView *iview=[[UIImageView alloc]initWithFrame:CGRectMake(0, 20, self.view.bounds.size.width, self.view.bounds.size.height/1.75)];
        UIButton *on=[UIButton buttonWithType:UIButtonTypeCustom];
        on.frame=CGRectMake((self.view.bounds.size.width/2)-20, (self.view.bounds.size.height*2/3)-10, 50, 50);
        [on setImage:[UIImage imageNamed:@"ic_off_on"] forState:UIControlStateNormal];
        on.tag=1;
        UIButton *type1=[UIButton buttonWithType:UIButtonTypeCustom];
        type1.tag=2;
        [type1 setImage:[UIImage imageNamed:@"s_1"] forState:UIControlStateNormal];
    type1.frame=CGRectMake(self.view.bounds.size.width/2-60, self.view.bounds.size.height*2/3+30, 50, 50);
        [type1 addTarget:self action:@selector(openOrBlock:) forControlEvents:UIControlEventTouchUpInside];
        UIButton *type2=[UIButton buttonWithType:UIButtonTypeCustom];
        type2.tag=3;
        [type2 setImage:[UIImage imageNamed:@"s_2"] forState:UIControlStateNormal];
    type2.frame=CGRectMake(self.view.bounds.size.width/2+20, self.view.bounds.size.height*2/3+30, 50, 50);
        [type2 addTarget:self action:@selector(openOrBlock:) forControlEvents:UIControlEventTouchUpInside];
        [on addTarget:self action:@selector(openOrBlock:) forControlEvents:UIControlEventTouchUpInside];
        on.backgroundColor=[UIColor clearColor];
        [self.view addSubview:type1];
        [self.view addSubview:type2];
        [self.view addSubview:on];
        [self judgeImage:iview];
        [self.view addSubview:iview];
    }
    else if([self.title isEqualToString:@"风扇"]){
        UIImageView *iview=[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width, self.view.bounds.size.width)];
        UIImage *img=[UIImage imageNamed:@"fan_fore"];
        iview.image=img;
        UIImageView *fanview=[[UIImageView alloc]initWithFrame:CGRectMake(0, 75, self.view.bounds.size.width, self.view.bounds.size.width)];
        fanview.image=[UIImage imageNamed:@"fan_leaf"];
        [self judgeImage:fanview];
        UIGraphicsEndImageContext();
        UIButton *on=[UIButton buttonWithType:UIButtonTypeCustom];              on.frame=CGRectMake((self.view.bounds.size.width/2)-20, (self.view.bounds.size.height/1.75)+80, 50, 50);
        [on setImage:[UIImage imageNamed:@"ic_off_on"] forState:UIControlStateNormal];
        on.tag=1;
        [on addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        [fanview addSubview:iview];
        [self.view addSubview:fanview];
        [self.view addSubview:on];
    }else if([self.title isEqualToString:@"实验室空调"]){
        
        UIImageView *view=[[UIImageView alloc ]initWithFrame:CGRectMake(0, 60, self.view.bounds.size.width, self.view.bounds.size.height*0.4)];
        view.backgroundColor=[UIColor colorWithRed:235/255.0 green:235/255.0 blue:235/255.0 alpha:1.0];
        view.layer.cornerRadius=25;
        NSUInteger a=defaultTemp/10;
        NSUInteger b=defaultTemp%10;
        UIImageView *tempV=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@%lu",@"ac_",a]]];
        tempV.frame=CGRectMake(30, 30, 100, 150);
        UIImageView *tempVB=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"%@%lu",@"ac_",b]]];
        tempVB.frame=CGRectMake(85, 0, 100, 150);
        UIImageView *cent=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ac_du"]];
        cent.frame=CGRectMake(90,0,45,45);
        [tempVB addSubview:cent];
        [self judgeImage:view];
        [tempV addSubview:tempVB];
        [view addSubview:tempV];
        UIView *btnView=[[UIView alloc]initWithFrame:CGRectMake(0, self.view.bounds.size.height*0.4, self.view.bounds.size.width, self.view.bounds.size.height*0.6)];
        UIView *rowView1=[[UIView alloc]initWithFrame:CGRectMake(0, 30, self.view.bounds.size.width, self.view.bounds.size.height*0.6*0.33)];
        UIButton *btn1=[UIButton buttonWithType:UIButtonTypeCustom];
        btn1.tag=1;
        [btn1 setTitle:@"开/关" forState:UIControlStateNormal];
        [btn1 setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
        btn1.frame=CGRectMake(0, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        [btn1 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        UIButton *btn2=[UIButton buttonWithType:UIButtonTypeSystem];
        [btn2 setTitle:@"模式" forState:UIControlStateNormal];
        btn2.tag=2;
        btn2.frame=CGRectMake(self.view.bounds.size.width/3*2, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        [btn2 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        [rowView1 addSubview:btn1];
        [rowView1 addSubview:btn2];
        [btnView addSubview:rowView1];
        UIView *rowView2=[[UIView alloc]initWithFrame:CGRectMake(0, self.view.bounds.size.height*0.6*0.33+30, self.view.bounds.size.width, self.view.bounds.size.height*0.6*0.33)];
        UIView *rowView3=[[UIView alloc]initWithFrame:CGRectMake(0, (self.view.bounds.size.height*0.6*0.33*2)+30, self.view.bounds.size.width, self.view.bounds.size.height*0.6*0.33)];
        UIButton *btn3=[UIButton buttonWithType:UIButtonTypeSystem];
        [btn3 setTitle:@"风速" forState:UIControlStateNormal];
        btn3.frame=CGRectMake(0, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        btn3.tag=3;
        [btn3 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        UIButton *btn4=[UIButton buttonWithType:UIButtonTypeSystem];
        [btn4 setTitle:@"上下扫风" forState:UIControlStateNormal];
        btn4.frame=CGRectMake(self.view.bounds.size.width/3, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        btn4.tag=4;
        [btn4 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        UIButton *btn5=[UIButton buttonWithType:UIButtonTypeSystem];
        [btn5 setTitle:@"左右扫风" forState:UIControlStateNormal];
        btn5.frame=CGRectMake((self.view.bounds.size.width/3)*2, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        btn5.tag=5;
        [btn5 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        [rowView3 addSubview:btn3];
        [rowView3 addSubview:btn4];
        [rowView3 addSubview:btn5];
        [btnView addSubview:rowView3];
        UIButton *btn6=[UIButton buttonWithType:UIButtonTypeSystem];
        [btn6 setTitle:@"-" forState:UIControlStateNormal];
        btn6.tag=6;
        btn6.titleLabel.font = [UIFont systemFontOfSize: 30.0];
        btn6.frame=CGRectMake(0, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        [btn6 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        UILabel *lbl=[[UILabel alloc]initWithFrame:CGRectMake(self.view.bounds.size.width/3, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33)];
        lbl.text=@"温度";
        lbl.textAlignment=NSTextAlignmentCenter;
        lbl.textColor=[UIColor lightGrayColor];
        [rowView2 addSubview:lbl];
        UIButton *btn7=[UIButton buttonWithType:UIButtonTypeSystem];
        [btn7 setTitle:@"+" forState:UIControlStateNormal];
        btn7.tag=7;
        btn7.titleLabel.font = [UIFont systemFontOfSize: 30.0];
        btn7.frame=CGRectMake(self.view.bounds.size.width/3*2, 0, self.view.bounds.size.width/3, self.view.bounds.size.height*0.6*0.33);
        [btn7 addTarget:self action:@selector(changeSta:) forControlEvents:UIControlEventTouchUpInside];
        [rowView2 addSubview:btn6];
        [rowView2 addSubview:btn7];
        [btnView addSubview:rowView2];
        [self.view addSubview:btnView];
        [self.view addSubview:view];
    }
    else if([self.title isEqualToString:@"温湿度"]){
        UIImageView *backG=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"show"]];
        backG.frame=CGRectMake(0, 50, self.view.bounds.size.width, self.view.bounds.size.height);
        UIImageView *view=[[UIImageView alloc]init];
        view.backgroundColor=[UIColor whiteColor];
        view.frame=CGRectMake(30, 20, self.view.bounds.size.width-60, self.view.bounds.size.height*0.2);
        
        NSDictionary *dic=[self dictionaryWithJsonString:_str];
        NSString *temp1=[dic valueForKey:@"temperature"];
        int temper=[temp1 intValue];
        NSString *temp2=[dic valueForKey:@"humidity"];
        int humid=[temp2 intValue];
        if(temper>10){
            UIImageView *aView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"ac_%d",temper/10]]];
            aView.frame=CGRectMake(30, 15, 70, 70);
            UIImageView *bView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"ac_%d",temper%10]]];
            bView.frame=CGRectMake(60, 0, 70, 70);
            UIImageView *duView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ac_du"]];
            duView.frame=CGRectMake(60, 0, 40, 40);
            [bView addSubview:duView];
            [aView addSubview:bView];
            [view addSubview:aView];
        }else if(temper<10&&temper>=0){
            UIImageView *aView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"ac_%d",temper/10]]];
            aView.frame=CGRectMake(30, 15, 70, 70);
            UIImageView *duView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ac_du"]];
            duView.frame=CGRectMake(60, 0, 40, 40);
            [aView addSubview:duView];
            [view addSubview:aView];
        }else if(temper>-10){
            UIImageView *fuView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ic_temp_dece"]];
            fuView.frame=CGRectMake(30, 15, 40, 40);
            UIImageView *aView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"ac_%d",temper/10]]];
            aView.frame=CGRectMake(60, 0, 70, 70);
            UIImageView *duView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ac_du"]];
            duView.frame=CGRectMake(60, 0, 40, 40);
            [aView addSubview:duView];
            [fuView addSubview:aView];
            [view addSubview:fuView];
        }else{
            UIImageView *fuView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ic_temp_dece"]];
            fuView.frame=CGRectMake(30, 15, 40, 40);
            UIImageView *aView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"ac_%d",temper/10]]];
            aView.frame=CGRectMake(60, 0, 70, 70);
            UIImageView *bView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:[NSString stringWithFormat:@"ac_%d",temper%10]]];
            bView.frame=CGRectMake(60, 0, 70, 70);
            UIImageView *duView=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"ac_du"]];
            duView.frame=CGRectMake(60, 0, 40, 40);
            [bView addSubview:duView];
            [aView addSubview:bView];
            [fuView addSubview:aView];
            [view addSubview:fuView];
        }
        UILabel *lbl=[[UILabel alloc]initWithFrame:CGRectMake(30, view.bounds.size.height-40, 200, 40)];
        lbl.textAlignment=NSTextAlignmentLeft;
        lbl.textColor = [UIColor lightGrayColor];
        lbl.text=[NSString stringWithFormat:@"湿度：%d",humid];
        view.layer.cornerRadius=25;
        [view addSubview:lbl];
        [backG addSubview:view];
        [self.view addSubview:backG];
    }else if ([self.title isEqualToString:@"pm指数"]){
        UIImageView *backG=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"show"]];
        backG.frame=CGRectMake(0, 50, self.view.bounds.size.width, self.view.bounds.size.height);
        UIImageView *view=[[UIImageView alloc]init];
        view.backgroundColor=[UIColor whiteColor];
        view.frame=CGRectMake(30, 20, self.view.bounds.size.width-60, self.view.bounds.size.height*0.2);
        NSDictionary *dic=[self dictionaryWithJsonString:_str];
        NSString *temp1=[dic valueForKey:@"pm2.5"];
        NSString *temp2=[dic valueForKey:@"pm10"];
        UILabel *lbl1=[[UILabel alloc]initWithFrame:CGRectMake(20, 20, 200, 30)];
        lbl1.textAlignment=NSTextAlignmentLeft;
        lbl1.textColor = [UIColor lightGrayColor];
        lbl1.text=[NSString stringWithFormat:@"pm2.5：%@",temp1];
        UILabel *lbl2=[[UILabel alloc]initWithFrame:CGRectMake(20, 60, 200, 30)];
        lbl2.textAlignment=NSTextAlignmentLeft;
        lbl2.textColor = [UIColor lightGrayColor];
        lbl2.text=[NSString stringWithFormat:@"pm10：%@",temp2];
        [view addSubview:lbl1];
        [view addSubview:lbl2];
        view.layer.cornerRadius=25;
        [backG addSubview:view];
        [self.view addSubview:backG];
    }
}
-(void)viewWillAppear:(BOOL)animated{
    if([self.title isEqualToString:@"灯组"]){
        [_post setDeviceId:@"6"];
        NSDictionary *data=[_op getDataLatest:_post];
        NSString *dataValue=[[data valueForKey:@"data"] valueForKey:@"dataValue"];
        _str=dataValue;
        NSLog(@"%@",dataValue);
        [self afterAppear];
    }else if([self.title isEqualToString:@"风扇"]){
        [_post setDeviceId:@"8"];
        NSDictionary *data=[_op getDataLatest:_post];
        NSString *dataValue=[[data valueForKey:@"data"]valueForKey:@"dataValue"];
        _str=dataValue;
        NSLog(@"%@",dataValue);
        [self afterAppear];
    }else if([self.title isEqualToString:@"实验室空调"]){
        [self afterAppear];
    }else if([self.title isEqualToString:@"温湿度"]){
        [_post setDeviceId:@"3000"];
        NSDictionary *data=[_op getDataLatest:_post];
        NSString *dataValue=[[data valueForKey:@"data"]valueForKey:@"dataValue"];
        _str=dataValue;
        [self afterAppear];
    }else if([self.title isEqualToString:@"pm指数"]){
        [_post setDeviceId:@"7"];
        NSDictionary *data=[_op getDataLatest:_post];
        NSString *dataValue=[[data valueForKey:@"data"]valueForKey:@"dataValue"];
        _str=dataValue;
        [self afterAppear];
    }
    [super viewWillAppear:animated];
    //关闭抽屉模式
    self.hidesBottomBarWhenPushed=YES;
    [self.mm_drawerController setOpenDrawerGestureModeMask:MMOpenDrawerGestureModeNone];
}
-(NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString {//json转字典
    if (jsonString == nil) {
        return nil;
    }
    NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *err;
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&err];
    if(err) {
        NSLog(@"json解析失败：%@",err);
        return nil;
    }
    return dic;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}





@end

