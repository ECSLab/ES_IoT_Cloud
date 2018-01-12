package com.example.dell.iotplatformdemo.Fragement;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.dell.iotplatformdemo.Base.BaseFragment;
import com.example.dell.iotplatformdemo.Bean.Device;
import com.example.dell.iotplatformdemo.Bean.WebDevice;
import com.example.dell.iotplatformdemo.Intereface.callBack;
import com.example.dell.iotplatformdemo.R;
import com.lvqingyang.iot.bean.Reponse;
import com.lvqingyang.iot.listener.GetDataListener;
import com.lvqingyang.iot.listener.PostMsgListener;
import com.lvqingyang.iot.net.EslabIot;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FanFragment extends BaseFragment implements callBack {
    @BindView(R.id.fan_img)
    ImageView fanImg;
    @BindView(R.id.fan_turn)
    ImageButton fanTurn;
    Unbinder unbinder;
    private Device mFan;
    private boolean fanOn = false;
    public static int FRAGMENT_CODE=0x0f;


    public FanFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_fan;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Drawable fanBagDraw = getResources().getDrawable(R.drawable.fan_fore);
        Bitmap bitmapbag = ((BitmapDrawable) fanBagDraw).getBitmap();
        Drawable fanLeaf = getResources().getDrawable(R.drawable.fan_leaf);
        Bitmap bitmap = ((BitmapDrawable) fanLeaf).getBitmap();
        Bitmap fan = toConformBitmap(bitmapbag, bitmap);
        fanImg.setImageBitmap(fan);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    private Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        //在 0，0坐标开始画入bg
        cv.drawBitmap(background, 0, 0, null);
        //draw fg into
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        //在 0，0坐标开始画入fg ，可以从任意位置画入
        cv.drawBitmap(foreground, 0, 1000, paint);
        //save all clip
        paint.setXfermode(null);
        return newbmp;
    }

    @Override
    public void fragmentcallBack(Device device) {
        mFan = device;
        getFanState();
    }

    private void getFanState() {
        EslabIot.getLastData(Integer.parseInt(mFan.getDevice_id()), new GetDataListener() {
            @Override
            public void succ(Reponse reponse) {
                if (reponse.getCode() == 0) {
                    Log.i(TAG, "获取数据成功" + reponse.getMessage() + "数据：" + reponse.getData());
                    WebDevice fan = JSON.parseObject(reponse.getData(), WebDevice.class);
                    Log.i(TAG, "当前状态" + fan.getDeviceId() + fan.getDataValue());
                    String dataValue = fan.getDataValue();
                    if (dataValue.equals("OFF")) {
                        Log.i(TAG,"关闭排气扇");
                        turnOff();
                    } else {
                        Log.i(TAG,"打开排气扇");
                        turnOn();
                    }
                }
            }


            @Override
            public void error(Throwable throwable) {
                Log.i(TAG, "获取数据失败" + throwable.getCause() + throwable.getMessage());
            }
        });

    }
    public void startRotate(){
        Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.version_image_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if(operatingAnim!=null){
           fanImg.startAnimation(operatingAnim);
        }
    }

    private void turnOff() {
        fanOn = false;
        fanImg.clearAnimation();
    }

    private void turnOn() {
        fanOn = true;
        startRotate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fan_img, R.id.fan_turn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fan_img:
                break;
            case R.id.fan_turn:
                postMessage();
                break;
        }
    }
    private void postMessage(){
        String msg;
        if (fanOn) {
             msg = "OFF";
        }else {
          msg="ON";
        }
        EslabIot.postMessage(Integer.parseInt(mFan.getDevice_id()), msg, new PostMsgListener() {
            @Override
            public void succ(String s) {
                Log.i(TAG,"发送成功");
                if (fanOn)
                    turnOff();
                else
                    turnOn();
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }
}

