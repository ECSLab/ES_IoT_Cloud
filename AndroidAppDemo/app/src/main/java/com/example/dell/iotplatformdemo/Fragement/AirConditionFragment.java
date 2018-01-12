package com.example.dell.iotplatformdemo.Fragement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.dell.iotplatformdemo.Base.BaseFragment;
import com.example.dell.iotplatformdemo.Bean.Device;
import com.example.dell.iotplatformdemo.Intereface.callBack;
import com.example.dell.iotplatformdemo.R;
import com.lvqingyang.iot.listener.PostMsgListener;
import com.lvqingyang.iot.net.EslabIot;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirConditionFragment extends BaseFragment implements callBack {
    public static int FRAGMENT_CODE = 0x0c;
    @BindView(R.id.air_condition_tem1)
    ImageView airConditionTem1;
    @BindView(R.id.air_condition_tem2)
    ImageView airConditionTem2;
    @BindView(R.id.air_condition_du)
    ImageView airConditionDu;
    @BindView(R.id.air_condition_mode_img)
    ImageView airConditionModeImg;
    @BindView(R.id.wind_speed_img)
    ImageView windSpeedImg;
    @BindView(R.id.wind_UD_img)
    ImageView windUDImg;
    @BindView(R.id.wind_LR_img)
    ImageView windLRImg;
    @BindView(R.id.air_condition_view)
    CardView airConditionView;
    @BindView(R.id.air_condition_turn)
    Button airConditionTurn;
    @BindView(R.id.air_condition_mode)
    Button airConditionMode;
    @BindView(R.id.wind_speed)
    Button windSpeed;
    @BindView(R.id.air_UD_scavenging)
    Button airUDScavenging;
    @BindView(R.id.air_RL_scavenging)
    Button airRLScavenging;
    @BindView(R.id.temp_add)
    ImageButton tempAdd;
    Unbinder unbinder;
    private Device mDevice;
    private Map<String,Object> map;
    private static int [] windSpeedImgIds=new int[]{R.drawable.quick_auto1,R.drawable.air_wind1,R.drawable.air_wind3,R.drawable.air_wind5};
    private static int[] tempImgIds = new int[]{R.drawable.ac_0, R.drawable.ac_1, R.drawable.ac_2, R.drawable.ac_3, R.drawable.ac_4, R.drawable.ac_5, R.drawable.ac_6, R.drawable.ac_7, R.drawable.ac_8, R.drawable.ac_9};

    private int[] speed=new int[]{0,1,2,3};
    private int mode=1,temp=20,UD=0,LR=0,air_switch=0,wind_speed=0;


    public AirConditionFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_air_condition;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
      airConditionView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    public void fragmentcallBack(Device device) {
        mDevice=device;
        Log.i(TAG,"跳转");

       // getAirConditionState();
    }
    /*private void getAirConditionState(){
        Log.i(TAG,"deviceId"+mDevice.getDevice_id());
        EslabIot.getLastData(mDevice.getDevice_id(), new GetDataListener() {
            @Override
            public void succ(Reponse reponse) {
                Log.i(TAG,"获取数据成功"+reponse.getData());
                WebDevice device= JSON.parseObject(reponse.getData(),WebDevice.class);
                JSONObject jsonObject=JSON.parseObject(device.getDataValue());
                mode=jsonObject.getIntValue("mode");
               air_switch=jsonObject.getIntValue("switch");
               wind_speed=jsonObject.getIntValue("wind_speed");
               wind_speed=speed[wind_speed];
               UD=jsonObject.getIntValue("UD_scavenging");
               LR=jsonObject.getIntValue("LR_scavenging");
               temp=jsonObject.getIntValue("temp");
                updateView();
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }
*/
    private void updateView(){
        if (air_switch==0){
            airConditionView.setVisibility(View.INVISIBLE);
        }else {
            airConditionView.setVisibility(View.VISIBLE);
            if (mode==0){
                airConditionModeImg.setImageDrawable(getResources().getDrawable(R.drawable.cold));
            }else {
                airConditionModeImg.setImageDrawable(getResources().getDrawable(R.drawable.hot));
            }
           windSpeedImg.setImageDrawable(getResources().getDrawable(windSpeedImgIds[wind_speed]));
            if (UD==0){
                windUDImg.setVisibility(View.INVISIBLE);
            }else {
                windUDImg.setVisibility(View.VISIBLE);
            }
            if (LR==0){
                windLRImg.setVisibility(View.INVISIBLE);
            }else {
                windLRImg.setVisibility(View.VISIBLE);
            }
            airConditionTem1.setImageDrawable(getResources().getDrawable(tempImgIds[temp/10]));
            airConditionTem2.setImageDrawable(getResources().getDrawable(tempImgIds[temp%10]));
        }
    }
    private void postMessage(){
        Log.i(TAG,"deviceId"+mDevice.getDevice_id()+mDevice.getDevice_name());
        Map map=new HashMap();
        map.put("mode",mode);
        map.put("switch",air_switch);
        map.put("wind_speed",wind_speed);
        map.put("UD_scavenging",UD);
        map.put("LR_scavenging",LR);
        map.put("temp",temp);
        String jsonStr=JSON.toJSONString(map);
        Log.i(TAG,"json"+jsonStr);
        EslabIot.postMessage(Integer.parseInt(mDevice.getDevice_id()), jsonStr, new PostMsgListener() {
            @Override
            public void succ(String s) {
                Log.i(TAG,"发送成功");
            }

            @Override
            public void error(Throwable throwable) {
            Log.i(TAG,"fas失败");
            }
        });
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

    @OnClick({R.id.air_condition_turn, R.id.air_condition_mode, R.id.wind_speed, R.id.air_UD_scavenging, R.id.air_RL_scavenging, R.id.temp_add,R.id.temp_decrease})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.air_condition_turn:
               if (air_switch==1){
                   air_switch=0;
                   airConditionTurn.setText("开");
               }
               else{
                   air_switch=1;
                   mode=1;
                   UD=0;
                   LR=0;
                   temp=22;
                   wind_speed=0;
                  airConditionTurn.setText("关");
               }
               updateView();
               postMessage();
                break;
            case R.id.air_condition_mode:
                if (air_switch==1){
                    if (mode==0){
                        mode=1;
                        temp=22;
                    }else{
                        mode=0;
                        temp=18;
                    }
                updateView();
                postMessage();
                }
                break;
            case R.id.wind_speed:
                if (air_switch==1){
                  wind_speed=speed[(wind_speed+1)%4];
                  updateView();
                  postMessage();
                }
                break;
            case R.id.air_UD_scavenging:
                if (air_switch==1){
                if (UD==0){
                    UD=1;
                    LR=0;
                }else
                    UD=0;
                updateView();
                postMessage();
                }
                break;
            case R.id.air_RL_scavenging:
                if (air_switch==1){
              if (LR==0){
                  LR=1;
                  UD=0;
              }else {
                  LR=0;
              }
              updateView();
              postMessage();
                }
                break;
            case R.id.temp_add:
                if (air_switch==1){
                if (temp<30){
                    temp++;
                    updateView();
                    postMessage();
                }
                }
                break;
            case R.id.temp_decrease:
                if (air_switch==1){
                    if (temp>16){
                        temp--;
                        updateView();
                        postMessage();
                    }
                }
                break;
        }
    }
}
