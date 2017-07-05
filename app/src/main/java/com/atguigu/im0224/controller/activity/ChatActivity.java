package com.atguigu.im0224.controller.activity;

import android.widget.FrameLayout;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.Bind;

public class ChatActivity extends BaseActivity {


    @Bind(R.id.chat_fl)
    FrameLayout chatFl;


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.chat_fl,chatFragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

}
