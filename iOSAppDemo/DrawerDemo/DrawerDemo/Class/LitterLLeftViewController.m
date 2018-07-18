

#import "LitterLLeftViewController.h"
#import "LitterLShowViewController.h"
#import "UIViewController+MMDrawerController.h"
@interface LitterLLeftViewController ()
@property(nonatomic,strong)NSDictionary *dic;
@end

@implementation LitterLLeftViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    NSArray *array1=[[NSArray alloc]initWithObjects:@"machine_ir_switch_1",@"machine_fan_tag_1",@"machine_air_tag_1",@"machine_air_evolution1",@"machine_air_evolution1", nil];
    NSArray *array2=[[NSArray alloc]initWithObjects:@"灯组",@"风扇",@"实验室空调",@"温湿度",@"pm指数", nil];
    NSArray *controllerArray=[NSArray array];
    _dic=[[NSDictionary alloc]init];
    _dic=@{
           @"image":array1,
           @"name":array2,
           @"controller":controllerArray
           };
    self.title = @"设备列表";
    self.tableView.tableFooterView=[[UIView alloc]init];//关键语句
}


#pragma mark - UITableViewDataSource


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 5;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
        static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    cell.imageView.image=[UIImage imageNamed:[_dic valueForKey:@"image"][indexPath.row]];
    //调整按钮中图片大小
    CGSize itemSize = CGSizeMake(50, 50);
    UIGraphicsBeginImageContextWithOptions(itemSize, NO, UIScreen.mainScreen.scale);
    CGRect imageRect = CGRectMake(0.0, 0.0, itemSize.width, itemSize.height);
    [cell.imageView.image drawInRect:imageRect];
    cell.imageView.image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    cell.textLabel.textColor=[UIColor blueColor];
    cell.textLabel.text =[_dic valueForKey:@"name"][indexPath.row];
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return self.view.bounds.size.height/8.0;
}

#pragma mark - UITableViewDelegate
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    LitterLShowViewController *showVC = [[LitterLShowViewController alloc]init];
    showVC.title = [_dic valueForKey:@"name"][indexPath.row];
    //拿到我们的LitterLCenterViewController，让它去push
    UITabBarController* nav = (UITabBarController*)self.mm_drawerController.centerViewController;
    NSArray *arr = nav.viewControllers;
    [arr[0] pushViewController:showVC animated:NO];
    //当我们push成功之后，关闭我们的抽屉
    [self.mm_drawerController closeDrawerAnimated:YES completion:^(BOOL finished) {
        //设置打开抽屉模式为MMOpenDrawerGestureModeNone，也就是没有任何效果。
        [self.mm_drawerController setOpenDrawerGestureModeMask:MMOpenDrawerGestureModeCustom];
    }];
}

@end
