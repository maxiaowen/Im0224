package com.atguigu.im0224.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2017/7/1.
 */

public class MyApplication extends Application{

    private static Context context;
    private static Handler handler;
    private static int pid;

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getPid() {
        return pid;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        //初始化环信
//        EMOptions options = new EMOptions();
//     // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//
//        options.setAutoAcceptGroupInvitation(false);
//       //初始化
//        EMClient.getInstance().init(this, options);
//      //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);

        //初始化环信sdk
        EMOptions options = new EMOptions();//配置一些功能
        //是否自动接受群组邀请
        options.setAutoAcceptGroupInvitation(false);
        //是否自动接受邀请
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this,options);

        //初始化Model
        Model.getInstance().init(this);

        context = this;
        handler = new Handler();
        pid = android.os.Process.myPid();
    }
}
