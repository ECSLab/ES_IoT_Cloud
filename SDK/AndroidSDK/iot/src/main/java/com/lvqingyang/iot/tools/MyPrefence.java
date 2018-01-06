package com.lvqingyang.iot.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Author：LvQingYang
 * Date：2017/3/15
 * Email：biloba12345@gamil.com
 * Github：https://github.com/biloba123
 * Info：封装SharpPrefence
 */



public class MyPrefence {
    private static MyPrefence mPrefence;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Gson mGson;

    //default int value
    public static final int DEFAULT_INT = -1;
    //default float value
    public static final float DEFAULT_FLOAT = 0f;

    private static final String KEY_IS_LOGIN = "IS_LOGIN";
    private static final String KEY_USER = "USER";


    private MyPrefence(Context context){
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        mEditor=mSharedPreferences.edit();
        mGson=new Gson();
    }

    public synchronized static MyPrefence getInstance(Context context) {
        if ( mPrefence== null) {
            mPrefence = new MyPrefence(context);
        }
        return mPrefence;
    }

    //save and get string
    public void saveString(String tag,String value){
        mEditor.putString(tag,value).apply();
    }

    public String getString(String tag){
        return  getString(tag,null);
    }

    public String getString(String tag,String def){
        return  mSharedPreferences.getString(tag,def);
    }

    //save and get int
    public void saveInt(String tag,int value){
        mEditor.putInt(tag,value).apply();
    }

    public int getInt(String tag){
        return mSharedPreferences.getInt(tag,DEFAULT_INT);
    }

    public int getInt(String tag,int def){
        return mSharedPreferences.getInt(tag,def);
    }

    //save and get bool
    public void saveBool(String tag,boolean value){
        mEditor.putBoolean(tag,value).apply();
    }

    public boolean getBool(String tag){
        return getBool(tag,false);
    }

    public boolean getBool(String tag,boolean def){
        return mSharedPreferences.getBoolean(tag,def);
    }

    //save and get object
    public void saveObject(String tag,Object obj){
        mEditor.putString(tag,mGson.toJson(obj)).apply();
    }

    public <T> T getObject(String tag,Class<T> classOfT){
        return (T)mGson.fromJson(getString(tag),classOfT);
    }

    public void clearData(String key){
        mEditor.remove(key)
                .apply();
    }

}
