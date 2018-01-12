package com.example.dell.iotplatformdemo.Base.Permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class PermissonActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    //权限请求码
    private static final int PERMISSION_REQUEST_CODE=0;

    /**
     * 判断是否需要检测
     */
    private boolean isNeedCheck=true;
    protected void checkPermission(){
        if (isNeedCheck)
        {
            List<String> needRequestPermissionList=findDeniedPermissions(getNeedPermissions());
            if (needRequestPermissionList!=null&&needRequestPermissionList.size()>0){
                ActivityCompat.requestPermissions(this,needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]),PERMISSION_REQUEST_CODE);
            }
        }
    }
    protected boolean idNeedPermissions(){
        return findDeniedPermissions(getNeedPermissions()).size()>0;
    }

    /**
     * 获得需要申请权限的列表
     * @param permissions
     * @return
     */
    private List<String> findDeniedPermissions(String[] permissions){
        List<String> needRequestPermissons=new ArrayList<String>();
        for (String permission:permissions){
            if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED||ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                needRequestPermissons.add(permission);
            }
        }
        return needRequestPermissons;
    }

    /**
     * 检测是否所有的权限都已授权
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults){
        for (int result:grantResults){
            if (result!=PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * 申请权限
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_REQUEST_CODE){
            if (!verifyPermissions(grantResults)){
                showMissingPermissionDialog();
                isNeedCheck=false;
            }
        }
    }
    private void showMissingPermissionDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\\\"设置\\\"-\\\"权限\\\"-打开所需权限。");
        //拒绝时退出
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //启动应用设置
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    private void startAppSettings(){
        Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //获取需要的权限
    protected abstract String[] getNeedPermissions();
}
