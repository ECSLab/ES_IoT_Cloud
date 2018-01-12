package com.example.dell.iotplatformdemo.Fragement;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.example.dell.iotplatformdemo.Base.BaseFragment;
import com.example.dell.iotplatformdemo.Bean.WebDevice;
import com.example.dell.iotplatformdemo.Bean.Device;
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
public class BulbFragment extends BaseFragment implements callBack {
    @BindView(R.id.bulb_image)
    ImageView bulbImage;
    @BindView(R.id.bulb_turn)
    ImageButton bulbTurn;
    Unbinder unbinder;
    @BindView(R.id.bulb_one)
    RadioButton bulbOne;
    @BindView(R.id.bulb_two)
    RadioButton bulbTwo;
    @BindView(R.id.bulb_three)
    RadioButton bulbThree;
    @BindView(R.id.bulb_four)
    RadioButton bulbFour;
    private Device device;
    public static int FRAGMENT_CODE = 0x0a;
    private boolean bulb_on = false;
    private Drawable drawable_bulb_off;
    private Drawable drawable_bulb_on;
    private char[] bulbState={0,0,0,0};
    private WebDevice webDevice;
    private int currentBulb = 1;
    private Boolean Connected=false;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_bulb;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        drawable_bulb_off = getResources().getDrawable(R.drawable.lightbulb_off);
        drawable_bulb_on = getResources().getDrawable(R.drawable.lightbulb_on);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }


    @Override
    public void fragmentcallBack(Device device) {
        Log.i(TAG, device.getDevice_name());
        this.device=device;
        getBulbState();
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

    @OnClick(R.id.bulb_turn)
    public void onViewClicked() {
        if (Connected) {
            if (bulbState[currentBulb - 1] == '0') {
                bulbState[currentBulb - 1] = '1';
            } else {
                bulbState[currentBulb - 1] = '0';
            }
            postMessage(new String(bulbState));
        }
    }

    private void postMessage(String msg) {
        Log.i(TAG,"发送的数据"+msg);
        EslabIot.postMessage(Integer.parseInt(device.getDevice_id()),msg, new PostMsgListener() {
            @Override
            public void succ(String s) {
                Log.i(TAG, "发送成功" + s);
                if (bulb_on) {
                    turnOff();
                } else {
                    turnOn();
                }
                //   getBulbState();
            }

            @Override
            public void error(Throwable throwable) {
                Log.i(TAG, "发送消息失败" + throwable.getCause()+throwable.getMessage());
            }
        });
    }

    private void turnOn() {
        bulbImage.setBackgroundResource(R.drawable.lightbulb_on);
        bulb_on = true;
    }

    private void turnOff() {
        bulbImage.setBackgroundResource(R.drawable.lightbulb_off);
        bulb_on = false;
    }

    private void getBulbState() {
Log.i("设备id",device.getDevice_id()+"");
        EslabIot.getLastData(Integer.parseInt(device.getDevice_id()), new GetDataListener() {
            @Override
            public void succ(Reponse reponse) {
                if (reponse.getCode() == 0) {
                    Log.i(TAG, "获取数据成功" + reponse.getMessage() + "数据：" + reponse.getData());
                    webDevice = JSON.parseObject(reponse.getData(), WebDevice.class);
                    Log.i(TAG, "当前状态" + webDevice.getDeviceId() + webDevice.getDataValue());
                    String dataValue = webDevice.getDataValue();
                    Connected=true;
                    bulbState = dataValue.toCharArray();
                    if (bulbState[currentBulb-1]=='0'){
                        turnOff();
                    }else {
                        turnOn();;
                    }
                }
            }

            @Override
            public void error(Throwable throwable) {
                Log.i(TAG, "获取数据失败" + throwable.getCause() + throwable.getMessage());
            }
        });
    }

    @OnClick({R.id.bulb_one, R.id.bulb_two, R.id.bulb_three, R.id.bulb_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bulb_one:
                currentBulb=1;
                break;
            case R.id.bulb_two:
                currentBulb=2;
                break;
            case R.id.bulb_three:
                currentBulb=3;
                break;
            case R.id.bulb_four:
                currentBulb=4;
                break;
        }if (Connected) {
            if (bulbState[currentBulb - 1] == '0') {
                turnOff();
            } else {
                turnOn();
                ;
            }
        }
    }
}
