package com.example.dell.iotplatformdemo.Base.Permission;

/**
 * Created by DELL on 2017/11/19.
 */

public interface PermissionListener {
    /**
     * 授权调用
     */
    public void onGranted();
    /**
     * 禁止权限调用
     */
    public void onDenied();

    /**
     * 是否显示阐述性说明
     * @param permissions 返回需要显示说明的权限数组
     */
    public void onShowRationale(String[] permissions);
}
