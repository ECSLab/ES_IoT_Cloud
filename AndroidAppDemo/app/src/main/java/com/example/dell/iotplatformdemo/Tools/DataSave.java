package com.example.dell.iotplatformdemo.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.dell.iotplatformdemo.Bean.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/12/11.
 */

public class DataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public DataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (datalist==null||datalist.size()<=0) {
            editor.clear().commit();
            return;
        }
        //转换成json数据，再保存
        Log.i("savedata","保存T数据");
        String strJson = JSON.toJSONString(datalist);
        editor.clear().commit();
        saveBoolean("initdata",true);
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public  List<Scene> getDataList(String tag) {
        List<Scene> datalist=new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Log.i("getdata","获得数据");
        datalist = (List<Scene>) JSON.parseArray(strJson, Scene.class);
        return datalist;

    }
    public void saveBoolean(String tag,boolean flag){
        editor.putBoolean(tag, flag);
        editor.commit();
    }
    public boolean getBoolean(String tag){

        return (preferences.getBoolean(tag,false));
    }

}
