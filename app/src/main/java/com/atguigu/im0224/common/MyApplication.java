package com.atguigu.im0224.common;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by Administrator on 2017/7/1.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
     // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        options.setAutoAcceptGroupInvitation(false);
       //初始化
        EMClient.getInstance().init(this, options);
      //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
