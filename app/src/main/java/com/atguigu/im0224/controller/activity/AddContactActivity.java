package com.atguigu.im0224.controller.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.atguigu.im0224.common.Model;
import com.atguigu.im0224.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;

public class AddContactActivity extends BaseActivity {

    @Bind(R.id.invite_btn_search)
    Button inviteBtnSearch;
    @Bind(R.id.invite_et_search)
    EditText inviteEtSearch;
    @Bind(R.id.invite_tv_username)
    TextView inviteTvUsername;
    @Bind(R.id.invite_btn_add)
    Button inviteBtnAdd;
    @Bind(R.id.invite_ll_item)
    LinearLayout inviteLlItem;
    @Bind(R.id.activity_invite_acitivity)
    LinearLayout activityInviteAcitivity;
    private String username;
    

    @Override
    protected void initListener() {

        inviteBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //获取输入的用户名
                 username = inviteEtSearch.getText().toString().trim();
                //校验
                if(TextUtils.isEmpty(username)) {
                    UIUtils.showToast("用户名不能为空");
                    return;
                }
                Model.getInstance().getGlobalThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //去本地服务器查询此联系人
                        if(getUser()) {
                            //服务器查有此人
                            UIUtils.UIThread(new Runnable() {
                                @Override
                                public void run() {
                                    inviteLlItem.setVisibility(View.VISIBLE);
                                    inviteTvUsername.setText(username);
                                }
                            });
                        }else {
                            //查无此人
                            UIUtils.showToast("查无此人");
                        }
                        
                    }
                });
                

            }
        });


        inviteBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //访问环信服务器进行联系人添加
                //第一个参数是环信ID 第二个参数是添加的原因

                try {
                    EMClient.getInstance().contactManager().addContact(username,"哈喽，约吗？");
                    UIUtils.showToast("添加联系人成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    UIUtils.showToast(e.getMessage());
                }
            }
        });
    }

    //去服务器查询添加的联系人
    private boolean getUser() {
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contact;
    }


}
