package com.example.dell.iotplatformdemo.Base;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.iotplatformdemo.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Random;

import butterknife.ButterKnife;

public  abstract class BaseActivity extends AppCompatActivity {
protected String TAG;
    private FrameLayout mContentFl;
    private LinearLayout mEmptyLl;
    private LinearLayout mLoadFailLl;
    private AVLoadingIndicatorView mLoadingIndicatorView;
    private LinearLayout mLoadingLl;
    private TextView mRetryTv;
    private View mRootView;
    private Activity activity;
    public interface OnRightClickListener {
        void rightClick();
    }

    protected void onCreate(@Nullable Bundle paramBundle) {

        super.onCreate(paramBundle);
        TAG=this.getClass().getSimpleName();
        activity=this;
        Bundle localBundle=getIntent().getExtras();
        if (localBundle!=null)
            getBundleExtras(localBundle);
        setContentView( R.layout.layout_base);
        this.mContentFl=((FrameLayout)findViewById(R.id.fl));
        this.mEmptyLl=((LinearLayout)findViewById(R.id.layout_empty));
        this.mLoadFailLl=((LinearLayout)findViewById(R.id.layout_load_fail));
        this.mRetryTv=((TextView)findViewById(R.id.tv_retry));
        this.mRetryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.onRetryClick();
            }
        });
        this.mLoadingLl=(LinearLayout)findViewById(R.id.layout_loading);
        this.mLoadingIndicatorView=(AVLoadingIndicatorView)findViewById(R.id.avl);
        View localView=getLayoutInflater().inflate(getLayoutId(),null);
        if (localView.findViewById(R.id.tool_bar)==null){
          this.mRootView=localView;
        }else {
            this.mRootView=localView.findViewById(R.id.view_root);
        }
        localView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.mContentFl.addView(localView);
        initBind();
        initView();

        initData();
        setData();
    }
    protected abstract int getLayoutId();
    protected  void setListener(){};
    protected  abstract void initData();
    protected abstract void initView();
    protected abstract void setData();
    protected abstract void  getBundleExtras(Bundle bundle);
    private void initBind(){
        ButterKnife.bind(activity);
    }
    /**
     * 统一初始化titlebar
     */
    protected Toolbar initToolBar(String title) {
        ImageView ivBack = (ImageView) findViewById(R.id.tool_bar_back);
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsRelative(10, 0);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        return toolbar;
    }

    /**
     * 统一初始化titlebar右侧图片
     */
    protected Toolbar initToolBarRightImg(String title, int rightId, final OnRightClickListener listener) {
        ImageView ivBack = (ImageView) findViewById(R.id.tool_bar_back);
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsRelative(10, 0);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        ImageView ivRight = (ImageView) findViewById(R.id.iv_tool_bar_right);
        ivRight.setImageResource(rightId);
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
        return toolbar;
    }


    /**
     * 统一初始化titlebar右侧文字
     */
    protected Toolbar initToolBarRightTxt(String title, String right, final OnRightClickListener listener) {
        ImageView ivBack = (ImageView) findViewById(R.id.tool_bar_back);
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsRelative(10, 0);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        TextView tvRight = (TextView) findViewById(R.id.tv_tool_bar_right);
        tvRight.setText(right);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
        return toolbar;
    }
    protected void back() {
        if (activity != null) {
            activity.finish();
        }
    }

    protected void loadFail(){
        this.mLoadingIndicatorView.smoothToHide();
        this.mLoadingLl.setVisibility(View.GONE);
        this.mRootView.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.VISIBLE);

    }
    protected void loading(){
        this.mRootView.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.GONE);
        this.mLoadingLl.setVisibility(View.VISIBLE);
        this.mLoadingIndicatorView.smoothToShow();
    }
    protected void onRetryClick(){
        loading();
    }
    protected void loadComplete(){
        this.mLoadingIndicatorView.smoothToHide();;
        this.mLoadingLl.setVisibility(View.GONE);
        this.mLoadFailLl.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.GONE);
        this.mRootView.setVisibility(View.VISIBLE);
        Animation localAnimation= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        this.mRootView.setAnimation(localAnimation);
    }
    protected void showEmpty(){
        this.mLoadFailLl.setVisibility(View.GONE);
        this.mLoadingIndicatorView.smoothToHide();;
        this.mLoadingLl.setVisibility(View.GONE);
        this.mRootView.setVisibility(View.GONE);
        this.mEmptyLl.setVisibility(View.VISIBLE);
    }
    protected boolean checkInternet(){
        boolean isConnected=true;
        ConnectivityManager manager=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if (networkInfo==null||!networkInfo.isAvailable()){
            Toast.makeText(this,"网络连接不可用，请检查连接",Toast.LENGTH_SHORT).show();
            isConnected=false;
        }
        return isConnected;
    }
    public boolean onOptionItemSeleted(MenuItem paramItem){
        if (paramItem.getItemId()==android.R.id.home)
            finish();
        return true;
    }


}
