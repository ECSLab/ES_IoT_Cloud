package com.example.dell.iotplatformdemo.Base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
protected  String TAG;
protected AppCompatActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity=(AppCompatActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments()!=null){
            getBundleExtras(getArguments());
        }
        if (getContentViewId()!=0){
            return inflater.inflate(getContentViewId(),null);
        }
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG=this.getClass().getSimpleName();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      ButterKnife.bind(view);
        setListener();
        initData();
        setData();
    }
    protected abstract int getContentViewId();
    //设置监听
    protected abstract void setListener();
    //初始化数据
    protected abstract void initData();
    //设置数据
    protected abstract void setData();


    protected abstract void getBundleExtras(Bundle bundle);
}
