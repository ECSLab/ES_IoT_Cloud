package com.example.dell.iotplatformdemo.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.iotplatformdemo.Base.BaseActivity;
import com.example.dell.iotplatformdemo.Base.Utils.RecyclerViewDivider;
import com.example.dell.iotplatformdemo.Bean.Device;
import com.example.dell.iotplatformdemo.Bean.Scene;
import com.example.dell.iotplatformdemo.Fragement.AirConditionFragment;
import com.example.dell.iotplatformdemo.Fragement.AirIndexFragment;
import com.example.dell.iotplatformdemo.Fragement.BulbFragment;
import com.example.dell.iotplatformdemo.Fragement.FanFragment;
import com.example.dell.iotplatformdemo.Intereface.callBack;
import com.example.dell.iotplatformdemo.R;
import com.example.dell.iotplatformdemo.Tools.DataSave;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.device_left)
    TextView deviceLeft;
    @BindView(R.id.device_title)
    TextView deviceTitle;
    @BindView(R.id.add_device)
    ImageButton addDevice;
    @BindView(R.id.device_rv)
    RecyclerView deviceRv;
    @BindView(R.id.imageButton)
    TextView deleteIbn;
    @BindView(R.id.main_add_device)
    Button mainAddDevice;
    @BindView(R.id.device_empty)
    RelativeLayout deviceEmpty;
    @BindView(R.id.title_drawer)
    TextView titleDrawer;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.view_root)
    DrawerLayout viewRoot;

    private DataSave dataSave;
    private List<Scene> sceneList;
    private List<Device> deviceList;
    private Device mDevice = null;


    private BulbFragment bulbFragment;
    private AirConditionFragment airConditionFragment;
    private FanFragment fanFragment;
    private AirIndexFragment airIndexFragment;


    private Scene scene;
    private static int ADD_DEVICE_REQUEST_CODE = 0x01;
    private CommonAdapter<Device> mAdapter;
    private EmptyWrapper<Device> emptyWrapper;
    private boolean deleteVisible = false;
    private callBack mFragemntBulbCall, mFragmentFanCall, mFragmentAirIndexCall, mFragmentAirConditionCall;
    private static int CURRENT_FRAGMENT = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        addFragment();
        dataSave = new DataSave(getApplication(), "DevicePreference");
        //判断是否有数据存入
        if (!dataSave.getBoolean("initdata")) {
            Log.i(TAG, "保存数据");
            String[] scenes = {"客厅", "书房", "办公室", "卧室"};
            for (int i = 0; i < 4; i++) {

                sceneList.add(new Scene(i, scenes[i], 0, null));
            }
            dataSave.saveBoolean("initdata", true);
            dataSave.setDataList("sceneList", sceneList);
            Log.i(TAG, dataSave.getBoolean("initdata") + "初始化");
        }

        Log.i(TAG, "dataSave不为空");
        sceneList = dataSave.getDataList("sceneList");
        //设置场景名称
        Log.i(TAG, "场景名称：" + sceneList.get(0).getName());
        //解决解析时乱序问题
        for (int i = 0; i < sceneList.size(); i++) {
            Log.i(TAG, sceneList.get(i).getId() + "id");
            if (sceneList.get(i).getId() == 0) {
                Log.i(TAG, "获取scene");
                scene = sceneList.get(i);
                // deviceList=scene.getDeviceList();
                break;
            }
        }
        initScene(scene);
    }

    private void setAdapter() {
        mAdapter = new CommonAdapter<Device>(this, R.layout.item_device, deviceList) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void convert(final ViewHolder holder, final Device device, final int position) {
                Log.i(TAG, "绑定item");
                holder.setText(R.id.device_name, device.getDevice_name());
                if (device.getDevice_type().equals("灯泡")){
                   /* if (position == 0) {*/
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_ir_switch_1));
          /*          } else {
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_ir_switch_2))*/;
                    }
                else if (device.getDevice_type().equals("风扇")) {
                   /* if (position == 0)*/
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_fan_tag_1));
                  /*  else
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_fan_tag_2));*/
                } else if (device.getDevice_type().equals("空气指数")) {
                   /* if (position == 0)*/
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_air_evolution1));
                   /* else
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_air_evolution2));*/
                } else if (device.getDevice_type().equals("空调")) {
                  /*  if (position == 0)*/
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_air_tag_1));
                 /*   else
                        holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_air_tag_2));*/
                }
                holder.setVisible(R.id.delete_device, deleteVisible);
                holder.setOnClickListener(R.id.item_device, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDevice = deviceList.get(position);
                        deviceTitle.setText(device.getDevice_name());
                        if (deviceList.get(position).getDevice_type().equals("灯泡")) {
                            holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_ir_switch_1));
                            mFragemntBulbCall.fragmentcallBack(mDevice);
                            if (CURRENT_FRAGMENT != BulbFragment.FRAGMENT_CODE) {
                                CURRENT_FRAGMENT=BulbFragment.FRAGMENT_CODE;
                                getSupportFragmentManager().beginTransaction().
                                        show(bulbFragment).hide(airConditionFragment)
                                        .hide(fanFragment)
                                        .hide(airIndexFragment)
                                        .commit();
                                Log.i(TAG, "跳转bulbFragment");
                            }
                        } else if (deviceList.get(position).getDevice_type().equals("风扇")) {
                            holder.setImageDrawable(R.id.device_image, getDrawable(R.drawable.machine_fan_tag_1));
                            mFragmentFanCall.fragmentcallBack(mDevice);
                            if (CURRENT_FRAGMENT != FanFragment.FRAGMENT_CODE){
                                CURRENT_FRAGMENT=FanFragment.FRAGMENT_CODE;
                                getSupportFragmentManager().beginTransaction().show(fanFragment)
                                        .hide(bulbFragment)
                                        .hide(airConditionFragment)
                                        .hide(airIndexFragment)
                                        .commit();
                            Log.i(TAG, "跳转fanFragment");}
                        } else if (deviceList.get(position).getDevice_type().equals("空气指数")) {
                            CURRENT_FRAGMENT=AirIndexFragment.FRAGMENT_CODE;
                            mFragmentAirIndexCall.fragmentcallBack(mDevice);
                            getSupportFragmentManager().beginTransaction().show(airIndexFragment)
                                    .hide(airConditionFragment)
                                    .hide(bulbFragment)
                                    .hide(fanFragment)
                                    .commit();
                            Log.i(TAG,"跳转airIndexFragment");
                        }else if (deviceList.get(position).getDevice_type().equals("空调")){
                            CURRENT_FRAGMENT=AirConditionFragment.FRAGMENT_CODE;
                            mFragmentAirConditionCall.fragmentcallBack(mDevice);
                            getSupportFragmentManager().beginTransaction().show(airConditionFragment)
                                    .hide(airIndexFragment)
                                    .hide(bulbFragment)
                                    .hide(fanFragment)
                                    .commit();
                            Log.i(TAG,"跳转airConditionFragment");
                        }
                    }
                });
                holder.setOnClickListener(R.id.delete_device, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "点击删除" + position);
                        deviceList.remove(position);
                        scene.setDeviceList(deviceList);
                        for (int i = 0; i < sceneList.size(); i++)
                            if (scene.getId() == sceneList.get(i).getId()) {
                                sceneList.get(i).setDeviceList(deviceList);
                                sceneList.get(i).setNumber(sceneList.get(i).getDeviceList().size());
                            }
                        saveChange();
                        /*emptyWrapper.notifyItemRemoved(position);
                        emptyWrapper.notifyItemRangeChanged(position, emptyWrapper.getItemCount());
                        mAdapter.notifyItemRemoved(position);*/
                        initScene(scene);
                    }
                });

            }
        };
        emptyWrapper = new EmptyWrapper<Device>(mAdapter);
        emptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.layout_empty, deviceRv, false));
        deviceRv.setAdapter(emptyWrapper);
    }

    @Override
    protected void initView() {
        setDeleteVisible();
        sceneList = new ArrayList<>();
        deviceList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        deviceRv.setLayoutManager(layoutManager);
        deviceRv.setHasFixedSize(true);
        deviceRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));

    }

    private void AddDevicehide(Boolean isHide) {
        if (isHide)
            deviceEmpty.setVisibility(View.GONE);
        else
            deviceEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setData() {

    }

    private void addFragment() {
        bulbFragment = new BulbFragment();
        airConditionFragment = new AirConditionFragment();
        fanFragment = new FanFragment();
        airIndexFragment = new AirIndexFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_device, bulbFragment).add(R.id.fragment_device, airConditionFragment)
                .add(R.id.fragment_device, fanFragment)
                .add(R.id.fragment_device, airIndexFragment).commit();
        mFragemntBulbCall = bulbFragment;
        mFragmentFanCall = fanFragment;
        mFragmentAirConditionCall = airConditionFragment;
        mFragmentAirIndexCall = airIndexFragment;

        //   mFragmentACCall=airConditionFragment;
    }


    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.device_left, R.id.add_device, R.id.imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.device_left:
                Intent intent = new Intent(this, SceneActivity.class);
                startActivityForResult(intent, 0x02);
                break;
            case R.id.add_device:
                addDevice();
                break;
            case R.id.imageButton:
                Log.i(TAG, "点击删除");
                if (!deleteVisible) {
                    deleteIbn.setBackground(null);
                    deleteIbn.setText("取消");
                } else {
                    deleteIbn.setBackground(getResources().getDrawable(android.R.drawable.ic_menu_delete));
                    deleteIbn.setText(null);
                }
                deleteVisible = !deleteVisible;
                setAdapter();
                break;
        }
    }

    @OnClick(R.id.main_add_device)
    public void onViewClicked() {
        addDevice();

    }

    private void setDeleteVisible() {
        View view = View.inflate(this, R.layout.item_device, null);
        ImageButton imageButton = view.findViewById(R.id.delete_device);
        if (deleteVisible)
            imageButton.setVisibility(View.VISIBLE);
        else
            imageButton.setVisibility(View.INVISIBLE);
    }

    private void addDevice() {
        Intent intent = new Intent(this, AddDeviceActivity.class);
        startActivityForResult(intent, ADD_DEVICE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DEVICE_REQUEST_CODE && data.getBooleanExtra("addSuccess", false)) {
            AddDevicehide(true);
            mDevice = (Device) data.getSerializableExtra("mDevice");
            for (int i = 0; i < sceneList.size(); i++) {
                if (sceneList.get(i).getId() == scene.getId()) {
                    //添加
                    if(deviceList==null)
                        deviceList=new ArrayList<>();
                    deviceList.add(mDevice);
                    sceneList.get(i).setDeviceList(deviceList);
                    sceneList.get(i).setNumber(deviceList.size());
                    scene = sceneList.get(i);
                    break;
                }
            }
            Log.i(TAG, "保存");
            dataSave.setDataList("sceneList", sceneList);
            titleDrawer.setText(scene.getName());
            deviceLeft.setText(scene.getName());
            setAdapter();
            emptyWrapper.notifyItemInserted(emptyWrapper.getItemCount());
            mAdapter.notifyItemInserted(mAdapter.getItemCount());
            deviceTitle.setText(mDevice.getDevice_name());
            if (mDevice.getDevice_type().equals("灯泡")) {

                mFragemntBulbCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(bulbFragment).hide(airConditionFragment).hide(fanFragment).hide(airIndexFragment).commitAllowingStateLoss();
                CURRENT_FRAGMENT = BulbFragment.FRAGMENT_CODE;
            } else if (mDevice.getDevice_type().equals("风扇")) {
                fanFragment.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(fanFragment).hide(bulbFragment).hide(airConditionFragment).hide(airIndexFragment).commitAllowingStateLoss();
                CURRENT_FRAGMENT = FanFragment.FRAGMENT_CODE;
            } else if (mDevice.getDevice_type().equals("空气指数")) {
                mFragmentAirIndexCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(airIndexFragment).hide(fanFragment).hide(bulbFragment).hide(airConditionFragment).commitAllowingStateLoss();
            } else if (mDevice.getDevice_type().equals("空调")) {
                CURRENT_FRAGMENT=AirConditionFragment.FRAGMENT_CODE;
                mFragmentAirConditionCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(airConditionFragment).hide(airIndexFragment).hide(fanFragment).hide(bulbFragment).commitAllowingStateLoss();
            }
        } else {
            if (requestCode == 0x02 && data.getBooleanExtra("select", false)) {
                for (int i = 0; i < sceneList.size(); i++) {
                    if (sceneList.get(i).getId() == data.getIntExtra("sceneId", 0)) {
                        scene = sceneList.get(i);
                        break;
                    }
                }
                initScene(scene);
            }
        }
    }

    private void initScene(Scene scene) {
        deviceLeft.setText(scene.getName());
        //判断当前场景是否存在设备
        titleDrawer.setText(scene.getName());
        if (scene.getNumber() != 0) {
            Log.i(TAG, "隐藏添加");
            AddDevicehide(true);
            deviceList = scene.getDeviceList();
            setAdapter();
            emptyWrapper.notifyItemInserted(emptyWrapper.getItemCount());
            mAdapter.notifyItemInserted(mAdapter.getItemCount());
            Log.i(TAG, "设备view" + mAdapter.getItemCount());
            mDevice = deviceList.get(0);
            deviceTitle.setText(mDevice.getDevice_name());
            if (mDevice.getDevice_type().equals("灯泡")) {
                Log.i(TAG, "fragment可见");
                CURRENT_FRAGMENT = BulbFragment.FRAGMENT_CODE;
                mFragemntBulbCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(bulbFragment).hide(airConditionFragment).hide(fanFragment).hide(airIndexFragment).commitAllowingStateLoss();
            } else if (mDevice.getDevice_type().equals("风扇")) {
                CURRENT_FRAGMENT = FanFragment.FRAGMENT_CODE;
                mFragmentFanCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(fanFragment).hide(bulbFragment).hide(airConditionFragment).hide(airIndexFragment).commitAllowingStateLoss();
            }else if (mDevice.getDevice_type().equals("空调")){
                CURRENT_FRAGMENT=AirConditionFragment.FRAGMENT_CODE;
                mFragmentAirConditionCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(airConditionFragment).hide(fanFragment).hide(bulbFragment).hide(airIndexFragment).commitAllowingStateLoss();
            }else if (mDevice.getDevice_type().equals("空气指数")){
                CURRENT_FRAGMENT=AirIndexFragment.FRAGMENT_CODE;
                mFragmentAirIndexCall.fragmentcallBack(mDevice);
                getSupportFragmentManager().beginTransaction().show(airIndexFragment).hide(fanFragment).hide(bulbFragment).hide(airConditionFragment).commitAllowingStateLoss();
            }
        } else {
            AddDevicehide(false);
            deviceList = new ArrayList<>();
            setAdapter();
        }
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }

    private void saveChange() {
        dataSave.setDataList("sceneList", sceneList);
    }

    @Override
    protected void onDestroy() {
        //  dataSave.setDataList("sceneList",sceneList);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
