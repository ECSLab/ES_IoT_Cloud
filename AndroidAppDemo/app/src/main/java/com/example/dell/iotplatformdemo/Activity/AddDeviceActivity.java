package com.example.dell.iotplatformdemo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dell.iotplatformdemo.Base.BaseActivity;
import com.example.dell.iotplatformdemo.Bean.Device;
import com.example.dell.iotplatformdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDeviceActivity extends BaseActivity {



    @BindView(R.id.title_back)
    ImageButton titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.airCondition)
    TextView airCondition;
    @BindView(R.id.fengshan)
    TextView fengshan;
    @BindView(R.id.kongqi)
    TextView kongqi;
    @BindView(R.id.blub)
    TextView blub;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device;
    }

    @Override
    protected void initData() {
        title.setText("添加设备");

    }

    @Override
    protected void initView() {

        // StatusBarUtil.setColor(this, getResources().getColor(R.color.white),0);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }


    @Override
    public void onBackPressed() {
        //
        Intent intent = new Intent();
        intent.putExtra("addSuccess", false);
        //    intent.putExtra("device",device);
        setResult(0x01, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_back, R.id.title, R.id.airCondition, R.id.fengshan, R.id.kongqi, R.id.blub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                onBackPressed();
                break;
            case R.id.title:
                break;
            case R.id.airCondition:
                addDevice("空调");
                break;
            case R.id.fengshan:
                addDevice("风扇");
                break;
            case R.id.kongqi:
                addDevice("空气指数");
                break;
            case R.id.blub:
                addDevice("灯泡");
                break;
        }
    }
    private void addDevice(final String deviceType){
        View addDeviceDialog=View.inflate(this,R.layout.add_device_dialog,null);

        final EditText deviceId=addDeviceDialog.findViewById(R.id.device_id);
        final EditText deviceName=addDeviceDialog.findViewById(R.id.device_name);
        AlertDialog dialog=new AlertDialog.Builder(this).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Device device=new Device(deviceId.getText().toString(),deviceName.getText().toString(),deviceType);
                Intent intent=new Intent();
                intent.putExtra("addSuccess",true);
                intent.putExtra("mDevice",device);
                setResult(0x01,intent);
                finish();
            }
        }).create();
        dialog.setTitle("添加设备");
        dialog.setView(addDeviceDialog);
        dialog.show();

    }
}
