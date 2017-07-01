package com.atguigu.im0224.controller.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.atguigu.im0224.common.Model;
import com.atguigu.im0224.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.login_et_username)
    EditText loginEtUsername;
    @Bind(R.id.login_et_password)
    EditText loginEtPassword;
    @Bind(R.id.login_btn_register)
    Button loginBtnRegister;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;
    @Bind(R.id.activity_login)
    LinearLayout activityLogin;

    @Override
    protected void initListener() {

        //登录按钮的点击事件
        loginBtnLogin.setOnClickListener(new LoginOnClickListener());

        //注册按钮的点击事件
        loginBtnRegister.setOnClickListener(new RegisterOnClickListener());

    }

    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            //获取输入的值
            final String username = loginEtUsername.getText().toString().trim();
            final String password = loginEtPassword.getText().toString().trim();
            //校验
            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                showToas("用户名或密码不能为空");
                return;
            }

            //开启一个分线程，联网请求数据
            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    EMClient.getInstance().login(username, password, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            //登录成功 处理一些特殊的信息 如保存用户数据
                            String currentUser = EMClient.getInstance().getCurrentUser();
                            Model.getInstance().loginSuccess(new UserInfo(currentUser,currentUser));

                            //跳转界面
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToas("登录成功");
                                }
                            });

                            //结束当前页面
                            finish();
                        }

                        @Override
                        public void onError(int i, final String s) {

                            //登录失败
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToas(s);
                                }
                            });
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                }
            });
        }
    }

    class RegisterOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            final String username = loginEtUsername.getText().toString().trim();
            final String password = loginEtPassword.getText().toString().trim();

            //校验
            if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username)){
                showToas("用户名或密码不能为空");
                return;
            }

            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(username,password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToas("注册成功");
                            }
                        });
                    } catch (final HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToas(e.getMessage());
                            }
                        });
                    }
                }
            });
        }
    }



    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


}
