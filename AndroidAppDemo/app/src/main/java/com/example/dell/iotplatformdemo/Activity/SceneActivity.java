package com.example.dell.iotplatformdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.iotplatformdemo.Base.BaseActivity;
import com.example.dell.iotplatformdemo.Base.Utils.RecyclerViewDivider;
import com.example.dell.iotplatformdemo.Bean.Scene;
import com.example.dell.iotplatformdemo.R;
import com.example.dell.iotplatformdemo.Tools.DataSave;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SceneActivity extends BaseActivity {

    @BindView(R.id.tool_bar_back)
    ImageView toolBarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_tool_bar_right)
    ImageView ivToolBarRight;
    @BindView(R.id.scene_rv)
    RecyclerView sceneRv;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    private List<Scene> sceneList;
    private DataSave dataSave;
    private int imgId[] = {R.drawable.scene_livingroom, R.drawable.scene_f_studyroom, R.drawable.scene_officeroom, R.drawable.scene_bedroom};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scene;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("场景");
        dataSave = new DataSave(getApplication(), "DevicePreference");
        sceneList = dataSave.getDataList("sceneList");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        sceneRv.setLayoutManager(layoutManager);
        CommonAdapter<Scene> adapter = new CommonAdapter<Scene>(this, R.layout.item_scene, sceneList) {
            @Override
            protected void convert(ViewHolder holder, final Scene scene, int position) {
                holder.setImageDrawable(R.id.scene_img, getResources().getDrawable(imgId[position]));
                holder.setText(R.id.scene_name, scene.getName());
                holder.setText(R.id.number, scene.getNumber() + "个遥控器");
               holder.setOnClickListener(R.id.item_scene, new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent();
                       intent.putExtra("sceneId",scene.getId());
                       intent.putExtra("select",true);
                       setResult(0x02,intent);
                       finish();
                   }
               });
            }


        };
        sceneRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
       sceneRv.setAdapter(adapter);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tool_bar_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra("select",false);
        setResult(0x02,intent);
        super.onBackPressed();
    }
}
