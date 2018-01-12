package com.example.dell.iotplatformdemo.Fragement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dell.iotplatformdemo.Base.BaseFragment;
import com.example.dell.iotplatformdemo.Bean.Device;
import com.example.dell.iotplatformdemo.Bean.WebDevice;
import com.example.dell.iotplatformdemo.Intereface.callBack;
import com.example.dell.iotplatformdemo.R;
import com.lvqingyang.iot.bean.Reponse;
import com.lvqingyang.iot.listener.GetDataListener;
import com.lvqingyang.iot.net.EslabIot;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirIndexFragment extends BaseFragment implements callBack {
    public static int FRAGMENT_CODE = 0x0c;
    @BindView(R.id.air_index_tem1)
    ImageView airIndexTem1;
    @BindView(R.id.air_index_tem2)
    ImageView airIndexTem2;
    @BindView(R.id.air_index_tem3)
    ImageView airIndexTem3;
    @BindView(R.id.index_wet)
    TextView indexWet;
    @BindView(R.id.air_index_tem)
    CardView airIndexTem;
    @BindView(R.id.air_index_pm)
    CardView airIndexPm;
    Unbinder unbinder;
    @BindView(R.id.pm_index_pm1)
    TextView pmIndex1;
    @BindView(R.id.pm_index_pm2)
    TextView pmIndexPm2;
    private Device mDevice;
    private static int AIR_INDEX_CODE;
    Map<String, Object> airIndex = new HashMap<>();
    private static int[] tempImgIds = new int[]{R.drawable.ac_0, R.drawable.ac_1, R.drawable.ac_2, R.drawable.ac_3, R.drawable.ac_4, R.drawable.ac_5, R.drawable.ac_6, R.drawable.ac_7, R.drawable.ac_8, R.drawable.ac_9};


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_air_index;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        airIndexTem.setVisibility(View.GONE);
        airIndexPm.setVisibility(View.GONE);

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    public void fragmentcallBack(Device device) {
        mDevice = device;
     /*   if (mDevice.getDevice_type().equals("pm指数")) {
            AIR_INDEX_CODE = 1;
        } else if (mDevice.getDevice_type().equals("温湿度")) {
            AIR_INDEX_CODE = 2;
        }*/
        getAirIndex();
    }

    private void getAirIndex() {
        EslabIot.getLastData(Integer.parseInt(mDevice.getDevice_id()), new GetDataListener() {
            @Override
            public void succ(Reponse reponse) {
                Log.i(TAG, "获取数据成功" + reponse.getData());
                WebDevice device = JSON.parseObject(reponse.getData(), WebDevice.class);
                JSONObject jsonObject = JSON.parseObject(device.getDataValue());
                Log.i(TAG, "转换2");
                airIndex = new HashMap<>();
                if (jsonObject.get("pm2.5") != null) {
                    AIR_INDEX_CODE = 1;
                    airIndex.put("pm2.5", jsonObject.getFloatValue("pm2.5"));
                    airIndex.put("pm10", jsonObject.getFloatValue("pm10"));

                } else if (jsonObject.get("temperature").toString() != null) {
                    Log.i(TAG, "转换温湿度");
                    AIR_INDEX_CODE = 2;
                    airIndex.put("humidity", jsonObject.getIntValue("humidity"));
                    airIndex.put("temperature", jsonObject.getIntValue("temperature"));

                }

                setAirIndexView();
            }

            @Override
            public void error(Throwable throwable) {

            }
        });
    }

    private void setAirIndexView() {
        if (AIR_INDEX_CODE == 2) {
            //显示温湿度
            Log.i(TAG, "显示温湿度");
            airIndexPm.setVisibility(View.GONE);
            airIndexTem.setVisibility(View.VISIBLE);
            int temp = Integer.parseInt(airIndex.get("temperature").toString());
            int wet = Integer.parseInt(airIndex.get("humidity").toString());
            if (temp < 0) {
                airIndexTem1.setImageDrawable(getResources().getDrawable(R.drawable.s_remove));
                temp = -temp;
                if (temp / 10 != 0) {
                    airIndexTem2.setImageDrawable(getResources().getDrawable(tempImgIds[temp / 10]));
                } else {
                    airIndexTem2.setImageDrawable(getResources().getDrawable(tempImgIds[temp % 10]));
                }
            } else if (temp == 0) {
                airIndexTem1.setVisibility(View.GONE);
                airIndexTem2.setImageDrawable(getResources().getDrawable(tempImgIds[0]));
            } else {
                if (temp / 10 != 0) {
                    airIndexTem1.setImageDrawable(getResources().getDrawable(tempImgIds[temp / 10]));
                } else {
                    airIndexTem1.setVisibility(View.GONE);
                }
                airIndexTem2.setImageDrawable(getResources().getDrawable(tempImgIds[temp % 10]));

            }
            indexWet.setText(airIndex.get("humidity").toString());
        } else if (AIR_INDEX_CODE == 1) {
            Log.i(TAG,"显示pm指数");
            airIndexTem.setVisibility(View.GONE);
            airIndexPm.setVisibility(View.VISIBLE);
            pmIndex1.setText(airIndex.get("pm2.5").toString());
            pmIndexPm2.setText(airIndex.get("pm10").toString());
        }
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
}
