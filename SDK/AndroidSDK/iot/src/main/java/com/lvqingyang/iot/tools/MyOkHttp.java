package com.lvqingyang.iot.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 *　　┏┓　　  ┏┓+ +
 *　┏┛┻━ ━ ━┛┻┓ + +
 *　┃　　　　　　  ┃
 *　┃　　　━　　    ┃ ++ + + +
 *     ████━████     ┃+
 *　┃　　　　　　  ┃ +
 *　┃　　　┻　　  ┃
 *　┃　　　　　　  ┃ + +
 *　┗━┓　　　┏━┛
 *　　　┃　　　┃　　　　　　　　　　　
 *　　　┃　　　┃ + + + +
 *　　　┃　　　┃
 *　　　┃　　　┃ +  神兽保佑
 *　　　┃　　　┃    代码无bug！　
 *　　　┃　　　┃　　+　　　　　　　　　
 *　　　┃　 　　┗━━━┓ + +
 *　　　┃ 　　　　　　　┣┓
 *　　　┃ 　　　　　　　┏┛
 *　　　┗┓┓┏━┳┓┏┛ + + + +
 *　　　　┃┫┫　┃┫┫
 *　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━神兽出没━━━━━━
 * Author：LvQingYang
 * Date：2017/3/15
 * Email：biloba12345@gamil.com
 * Info：OkHttp网络请求
 */



public class MyOkHttp {
    private static MyOkHttp sMyOkHttp;
    private OkHttpClient mClient;

    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_XML
            = MediaType.parse("application/xml; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG
            = MediaType.parse("image/png");

    private static final String TAG = "MyOkHttp";

    private MyOkHttp(){
        mClient=new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static MyOkHttp getInstance(){
        if (sMyOkHttp == null) {
            sMyOkHttp=new MyOkHttp();
        }
        return sMyOkHttp;
    }

    /**
     * 同步发送GET请求
     * @param url
     * @return
     * @throws Exception
     */
    public String  get(String url) throws Exception {
        Request request=new Request.Builder()
                .url(url)
                .build();

        Response response=mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }else {
            throw new Exception("Request "+url+" failed!");
        }
    }

    /**
     * 同步发送GET请求，带参数
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String  getWithParams(String url, List<NameValuePair> params) throws Exception {
        return get(appendParams(url, params));
    }

    /**
     * 异步发送GET请求
     * @param url
     * @param callback
     */
    public void getAsync(String url, Callback callback){
        Request request=new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(callback);
    }

    /**
     * 异步发送GET请求，带参数
     * @param url
     * @param params
     * @param callback
     * @throws UnsupportedEncodingException
     */
    public void getAsyncWithParams(String url, List<NameValuePair> params, Callback callback) throws UnsupportedEncodingException {
        getAsync(appendParams(url, params), callback);
    }

    /**
     * GET请求拼接参数到url上
     * @param url
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public String appendParams(String url, List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder sb=new StringBuilder(url);
        sb.append("?");
        int len=params.size();
        for (int i = 0; i < len; i++) {
            NameValuePair pair=params.get(i);
            sb.append(URLEncoder.encode(pair.getName().toString(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(pair.getValue().toString(), "UTF-8"));

            if (i!=len-1) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    /**
     * POST表单
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String  postForm(String url,  List<NameValuePair> params) throws Exception {
        FormBody.Builder builder=new FormBody.Builder();
        for (NameValuePair param : params) {
            builder.add(param.name.toString(), param.value.toString());
        }
        FormBody formBody=builder.build();

        Request request=new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response=mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }else {
            throw new Exception("Request "+url+" failed!");
        }
    }

    /**
     * POST Json数据
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String postJson(String url, String json) throws Exception {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }else {
            throw new Exception("Request "+url+" failed!");
        }
    }

    /**
     * 参数键值对
     * @param <N>
     * @param <T>
     */
    public static class NameValuePair<N,T>{
        private N name;
        private T value;

        public NameValuePair(N name, T value) {
            this.name = name;
            this.value = value;
        }

        public N getName() {
            return name;
        }

        public void setName(N name) {
            this.name = name;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }


}

