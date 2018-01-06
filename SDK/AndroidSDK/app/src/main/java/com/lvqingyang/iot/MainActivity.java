package com.lvqingyang.iot;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lvqingyang.iot.bean.Reponse;
import com.lvqingyang.iot.listener.GetDataListener;
import com.lvqingyang.iot.listener.PostMsgListener;
import com.lvqingyang.iot.net.EslabIot;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private android.widget.EditText etdevice;
    private android.widget.EditText etdata;
    private android.widget.Button btnpost;
    private android.widget.ImageView ivlight;
    public static final int LIGHT_ON = 295;
    public static final int LIGHT_OFF = 292;
    private static final String TAG = "MainActivity";
    private int mColorBlack= Color.parseColor("#ee000000");
    private int mColorWhite= Color.parseColor("#ffffff");
    private android.widget.RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.rl = (RelativeLayout) findViewById(R.id.rl);
        this.ivlight = (ImageView) findViewById(R.id.iv_light);
        ivlight.setTag(LIGHT_OFF);

        EslabIot.init("eslabtest");

        final PostMsgListener listener=new PostMsgListener() {
            @Override
            public void succ(String s) {
                if (BuildConfig.DEBUG) Log.d(TAG, "succ: "+s);
            }

            @Override
            public void error(Throwable e) {
                e.printStackTrace();
            }
        };

        ivlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (((int) ivlight.getTag())) {
                    case LIGHT_ON:{
                        EslabIot.postMessage(2, "OFF", listener);
                        ivlight.setImageResource(R.drawable.ic_light_off);
                        ivlight.setTag(LIGHT_OFF);
                        rl.setBackgroundColor(mColorBlack);
                        colorTrans(mColorWhite, mColorBlack);
                        break;
                    }
                    case LIGHT_OFF:{
                        EslabIot.postMessage(2, "ON", listener);
                        ivlight.setImageResource(R.drawable.ic_light_on);
                        ivlight.setTag(LIGHT_ON);
                        colorTrans(mColorBlack, mColorWhite);
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }
        });

        EslabIot.getDataWithTime(2,new Date(),new Date(), new GetDataListener() {
            @Override
            public void succ(Reponse r) {
                if (BuildConfig.DEBUG) Log.d(TAG, "succ: "+ r.getCode()+ " "+r.getMessage()+" "
                        +(r.getData()==null));
            }

            @Override
            public void error(Throwable e) {
                e.printStackTrace();
            }
        });
//        this.btnpost = (Button) findViewById(R.id.btn_post);
//        this.etdata = (EditText) findViewById(R.id.et_data);
//        this.etdevice = (EditText) findViewById(R.id.et_device);
//
//        EslabIot.init("eslabtest");
//
//        btnpost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EslabIot.postMessage(Integer.valueOf(etdevice.getText().toString()),
//                        etdata.getText().toString(), new PostMsgListener() {
//                    @Override
//                    public void succ(String s) {
//                    }
//
//                    @Override
//                    public void error(Throwable e) {
//                    }
//                });
//            }
//        });

    }

    private void colorTrans(int colorStart, int colorEnd) {
        ValueAnimator animator=ValueAnimator.ofInt(colorStart, colorEnd).setDuration(500);
        animator.setEvaluator(new ArgbEvaluator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        rl.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                });
        animator.start();

    }
}
