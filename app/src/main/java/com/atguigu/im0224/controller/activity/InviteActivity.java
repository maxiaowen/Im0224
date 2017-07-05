package com.atguigu.im0224.controller.activity;

import android.widget.ListView;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.atguigu.im0224.common.Model;
import com.atguigu.im0224.controller.adapter.InviteAdapter;
import com.atguigu.im0224.model.bean.InvitationInfo;
import com.atguigu.im0224.utils.SPUtils;
import com.atguigu.im0224.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.Bind;

public class InviteActivity extends BaseActivity {


    @Bind(R.id.lv_invite)
    ListView lvInvite;

    private InviteAdapter adapter;

    @Override
    protected void initListener() {

    }


    @Override
    protected void initData() {

        //设置小红点的状态
        SPUtils.getInstence().save(SPUtils.NEW_INVITE, false);

        //设置适配器
        adapter = new InviteAdapter(this);
        adapter.setOnInviteListener(onInviteListener);
        lvInvite.setAdapter(adapter);

        //给适配器设置数据
        refreshData();
    }
    /*
      界面数据展示需要考虑三大数据源
    * 网络
    *
    * 本地
    *
    * 内存和页面
    *
    *
    * */
    public void refreshData() {
        //从数据库获取数据
        List<InvitationInfo> infos = Model.getInstance().getManager().getInvitationDAO().getInvitations();
        if(infos != null) {
            adapter.refresh(infos);
        }
    }

    /*
* inviteAdapter的回调方法
*
* */
    InviteAdapter.OnInviteListener onInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void invitedSuccess(final InvitationInfo info) {
            //接受邀请
            //开启分线程
            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    //得到hxid
                    String hxid = info.getUserInfo().getHxid();
                    try {
                        //网络传输数据，告诉对方我接受邀请啦
                        EMClient.getInstance().contactManager().acceptInvitation(hxid);

                        //改变本地邀请数据信息的状态为已接收
                        Model.getInstance().getManager().getInvitationDAO().
                                updateInvitationStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT,hxid);

                        //内存和网页, 从新请求数据并刷新
                        UIUtils.UIThread(new Runnable() {
                            @Override
                            public void run() {
                                UIUtils.showToast("添加成功");
                                refreshData();//刷新
                            }
                        });


                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        UIUtils.showToast(e.getMessage());
                    }
                }
            });
        }

        @Override
        public void invitedReject(final InvitationInfo info) {
            //拒绝邀请
            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    String hxid = info.getUserInfo().getHxid();

                    try {
                        //从网络告诉对方我拒绝好友邀请了
                        EMClient.getInstance().contactManager().declineInvitation(hxid);

                        //从本地数据库删除此条邀请
                        Model.getInstance().getManager().getInvitationDAO().removeInvitation(hxid);

                        //内存和页面
                        UIUtils.UIThread(new Runnable() {
                            @Override
                            public void run() {
                                UIUtils.showToast("已拒绝");
                                refreshData();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        UIUtils.showToast(e.getMessage());
                    }
                }
            });
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

}
