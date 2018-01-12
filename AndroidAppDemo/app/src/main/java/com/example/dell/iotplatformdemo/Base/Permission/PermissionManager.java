package com.example.dell.iotplatformdemo.Base.Permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/11/19.
 */

public class PermissionManager {
    private Object mObject;
    private String [] mPermissions;
    private int mRequestCode;
    private PermissionListener mListener;
    //用户是否确认了解释框
    private boolean mIsPositive=false;
    public static PermissionManager with(Activity activity){
        return new PermissionManager(activity);
    }
    public static  PermissionManager with(Fragment fragment){
        return new PermissionManager(fragment);
    }
    public PermissionManager permissions(String... permissions){
        this.mPermissions=permissions;
        return this;
    }
    public PermissionManager addRequestCode(int requestCode){
        this.mRequestCode=requestCode;
        return this;
    }
    public PermissionManager setPermissionsListener(PermissionListener mListener){
        this.mListener=mListener;
        return this;
    }
    public PermissionManager(Object mObject){
        this.mObject=mObject;
    }

    /**
     * 请求权限
     * @return
     */
    public PermissionManager request(){
        request(mObject,mPermissions,mRequestCode);
        return this;
    }


   private void request(Object mObject, String[] mPermissions, int mRequestCode){
        //根据权限集合查找是否已经授权
       Map<String ,List<String>> map=findDeniedPermissions(getActivity(mObject),mPermissions);
       List<String> deniedPermissionList=map.get("denyList");
       List<String> rationalePermissionList=map.get("rationaleList");
       if (deniedPermissionList.size()>0){
           //第一次选择拒绝调用，mIsPositive是为了防止点确认解释框后request()递归调用onShowRationale();
           if (rationalePermissionList.size()>0&&mIsPositive==false){
               if (mListener!=null){
                   mListener.onShowRationale(rationalePermissionList.toArray(new String[rationalePermissionList.size()]));

               }
               return;
           }
           if (mObject instanceof Activity){
               ActivityCompat.requestPermissions((Activity)mObject,deniedPermissionList.toArray(new String[deniedPermissionList.size()]),mRequestCode);
           } else if (mObject instanceof  Fragment){
               ((Fragment) mObject).requestPermissions(deniedPermissionList.toArray(new String[deniedPermissionList.size()]),mRequestCode);
           } else {
               throw new IllegalArgumentException(mObject.getClass().getName()+"is not supported");
           }
       }else {
           if (mListener != null) {
               mListener.onGranted();
           }
       }
   }
   public void onPermissionResult(String[] permissions,int [] results){
       List<String> deniedPermissions=new ArrayList<String>();
       for (int i=0;i<results.length;i++){
           if (results[i]!=PackageManager.PERMISSION_GRANTED){
               deniedPermissions.add(permissions[i]);
           }
       }
       if (deniedPermissions.size()>0){
           if (mListener!=null)
               mListener.onDenied();
       }else {
           if (mListener!=null){
               mListener.onGranted();
           }
       }
   }
   private Map<String,List<String>> findDeniedPermissions(Activity activity,String...mPermissons){
      //权限集
       Map<String,List<String>> map=new HashMap<String, List<String>>();
      //未授权权限列表
      List<String> denyList=new ArrayList<String>();
      //需要阐述权限表
      List<String> rationaleList=new ArrayList<String>();
      for (String value:mPermissons){
          if (ContextCompat.checkSelfPermission(activity,value)!= PackageManager.PERMISSION_GRANTED){
              denyList.add(value);
              if (shouldShowRequestPermissionRationale(value)){
                  rationaleList.add(value);
              }
          }
      }
      map.put("denyList",denyList);
       map.put("rationaleList",rationaleList);
       return map;
   }
   private Activity getActivity(Object mObject){
       if (mObject instanceof  Fragment){
           return ((Fragment)mObject).getActivity();
       } else if (mObject instanceof Activity){
           return (Activity) mObject;
       }
       return null;
   }

    /**
     * 用户拒绝权限点击不在提醒时，下次请求权限，给出合适响应
     * @param permission
     * @return
     */
   private boolean shouldShowRequestPermissionRationale(String permission){
       if (mObject instanceof  Activity){
           return ActivityCompat.shouldShowRequestPermissionRationale((Activity)mObject,permission);

       }else if (mObject instanceof  Fragment){
           return ((Fragment) mObject).shouldShowRequestPermissionRationale(permission);
       }else {
           throw new IllegalArgumentException(mObject.getClass().getName()+" is not supported");
       }
   }
   public void setmIsPositive(boolean isPositive){
       this.mIsPositive=isPositive;
   }
}
