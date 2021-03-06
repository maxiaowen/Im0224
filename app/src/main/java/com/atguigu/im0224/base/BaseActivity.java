package com.atguigu.im0224.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);


        ininView();
        initData();
        initListener();
    }

    protected abstract void initListener();

    protected abstract void initData();

    private void ininView() {

    }

    public void showToas(String massage){
        Toast.makeText(BaseActivity.this, massage, Toast.LENGTH_SHORT).show();
    }

    public abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
