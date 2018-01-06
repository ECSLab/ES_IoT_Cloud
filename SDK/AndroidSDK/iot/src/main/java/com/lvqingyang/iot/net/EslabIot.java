package com.lvqingyang.iot.net;

import android.text.TextUtils;

import com.lvqingyang.iot.bean.Reponse;
import com.lvqingyang.iot.listener.GetDataListener;
import com.lvqingyang.iot.listener.IotListener;
import com.lvqingyang.iot.listener.PostMsgListener;
import com.lvqingyang.iot.tools.MyOkHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 一句话功能描述
 * 功能详细描述
 *
 * @author Lv Qingyang
 * @date 2017/12/2
 * @email biloba12345@gamil.com
 * @github https://github.com/biloba123
 * @blog https://biloba123.github.io/
 */
public class EslabIot {
    private static String API_KEY=null;
    private static final String TAG = "EslabIot";
    private static final String ORDER_ASC = "asc";
    private static final String ORDER_DESC = "desc";

    private EslabIot(){
    }

    /**
     * init sdk with project api_key
     * @param apiKey
     */
    public static void init(String apiKey){
        API_KEY=apiKey;
    }

    /**
     * http post whit form
     * @param url
     * @param datas
     * @param listener
     */
    private static void post(final String url, final List<MyOkHttp.NameValuePair> datas, final GetDataListener listener){
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                //okhttp post
                try {
                    String reponse = MyOkHttp.getInstance()
                            .postForm(url, datas);
                    subscriber.onNext(reponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.error(e);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            JSONObject object=new JSONObject(s);
                            String data=object.get("data").toString();
                            Reponse reponse=new Reponse(object.getInt("code"),
                                    object.getString("message"), TextUtils.equals(data, "null")?null:data);

                            listener.succ(reponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onError(e);
                        }
                    }
                });
    }

    /**
     * post message to the device
     * @param deviceId
     * @param data
     * @param listener
     */
    public static void postMessage(final int deviceId, final String data, final PostMsgListener listener){
        if(checkApiKey(listener)) {
            Observable.create(new Observable.OnSubscribe<String>() {

                @Override
                public void call(Subscriber<? super String> subscriber) {
                    //form
                    List<MyOkHttp.NameValuePair> datas
                            = new ArrayList<>();
                    datas.add(new MyOkHttp.NameValuePair("api_key", API_KEY));
                    datas.add(new MyOkHttp.NameValuePair("device_id", String.valueOf(deviceId)));
                    datas.add(new MyOkHttp.NameValuePair("data", data));

                    //okhttp post
                    try {
                        String reponse = MyOkHttp.getInstance()
                                .postForm(Api.POST_MESSAGE, datas);
                        subscriber.onNext(reponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.error(e);
                        }

                        @Override
                        public void onNext(String s) {
                            if (s.equals("PUBLISH_SUCCESS")) {
                                listener.succ(s);
                            }else {
                                onError(new Exception(s));
                            }
                        }
                    });
        }
    }

    /**
     * get history data in db
     * @param deviceId
     * @param listener
     */
    public static void getHistoryData(final int deviceId, final String order, final GetDataListener listener){
        if(checkApiKey(listener)) {
            //form
            List<MyOkHttp.NameValuePair> datas
                    = addApiKeyAndDevice(deviceId);
            datas.add(new MyOkHttp.NameValuePair("order", order));

            post(Api.GET_HISTORY_DATA, datas, listener);
        }
    }

    /**
     * get history data in db with asc order
     * @param deviceId
     * @param listener
     */
    public static void getHistoryData(final int deviceId, final GetDataListener listener){
        getHistoryData(deviceId, ORDER_ASC, listener);
    }


    /**
     * get last data in db
     * @param deviceId
     * @param listener
     */
    public static void getLastData(final int deviceId, final GetDataListener listener){
        if(checkApiKey(listener)) {
            //form
            List<MyOkHttp.NameValuePair> datas
                    = addApiKeyAndDevice(deviceId);

            post(Api.GET_LAST_DATA, datas, listener);
        }
    }

    /**
     * get data with time
     * @param deviceId
     * @param listener
     */
    public static void getDataWithTime(final int deviceId, Date startTime, Date endTime,
                                       String order, final GetDataListener listener){
        if(checkApiKey(listener)) {
            //form
            List<MyOkHttp.NameValuePair> datas
                    = addApiKeyAndDevice(deviceId);
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            datas.add(new MyOkHttp.NameValuePair("startTime", format.format(startTime)));
            datas.add(new MyOkHttp.NameValuePair("endTime", format.format(endTime)));
            datas.add(new MyOkHttp.NameValuePair("order", order));

            post(Api.GET_DATA_WITH_TIME, datas, listener);
        }
    }

    /**
     * get data with time in asc order
     * @param deviceId
     * @param listener
     */
    public static void getDataWithTime(final int deviceId, Date startTime, Date endTime,
                                        final GetDataListener listener){
        getDataWithTime(deviceId, startTime, endTime, ORDER_ASC, listener);
    }




    /**
     * add api_key and device_id to the form
     * @param deviceId
     * @return
     */
    private static List<MyOkHttp.NameValuePair> addApiKeyAndDevice(int deviceId) {
        List<MyOkHttp.NameValuePair> datas
                = new ArrayList<>();
        datas.add(new MyOkHttp.NameValuePair("apiKey", API_KEY));
        datas.add(new MyOkHttp.NameValuePair("deviceId", String.valueOf(deviceId)));
        return datas;
    }

    /**
     * check weathe api_key has been set
     * @param listener
     * @return
     */
    private static boolean checkApiKey(IotListener listener){
        if (TextUtils.isEmpty(API_KEY)) {
            listener.error(new Exception("Api_key of your project must be set!"));
            return false;
        }else {
            return true;
        }
    }
}
