package com.atguigu.im0224.controller.activity;

import android.widget.ListView;

import com.atguigu.im0224.R;
import com.atguigu.im0224.base.BaseActivity;
import com.atguigu.im0224.common.Model;
import com.atguigu.im0224.controller.adapter.InviteAdapter;
import com.atguigu.im0224.model.bean.InvitationInfo;
import com.atguigu.im0224.utils.SPUtils;

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
  * inviteAdapter的回调方法
  *
  * */
    InviteAdapter.OnInviteListener onInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void invitedSuccess(InvitationInfo info) {
            //接受邀请
        }

        @Override
        public void invitedReject(InvitationInfo info) {

            //拒绝邀请
        }
    };

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

}
