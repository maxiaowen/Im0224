package com.atguigu.im0224.controller.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.atguigu.im0224.common.Model;
import com.hyphenate.chat.EMClient;

public class WelcomeAcyivity extends BaseActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void initListener() {

        //第一个参数是倒计时的总时长，倒计时时间间隔
        //倒计时结束
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                //倒计时结束
                selectChageActivity();
            }
        }.start();
    }

    //选择进入哪个界面
    private void selectChageActivity() {

//        new Thread() {
//            public void run() {
//                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
//                if (isLogin) {
//                    //登录过
//                    startActivity(new Intent(WelcomeAcyivity.this, MainActivity.class));
//                    finish();
//                } else {
//                    //没有登录过
//                    startActivity(new Intent(WelcomeAcyivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//        }.start();

        Model.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = EMClient.getInstance().isLoggedInBefore();
                if (isLogin) {
                    //登录过
                    startActivity(new Intent(WelcomeAcyivity.this, MainActivity.class));
                    finish();
                } else {
                    //没有登录过
                    startActivity(new Intent(WelcomeAcyivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //取消倒计时
        countDownTimer.cancel();
    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_acyivity;
    }
}
