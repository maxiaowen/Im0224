package com.atguigu.im0224.common;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.atguigu.im0224.model.bean.InvitationInfo;
import com.atguigu.im0224.model.bean.UserInfo;
import com.atguigu.im0224.utils.SPUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2017/7/3.
 */

public class GlobalListener {

    private LocalBroadcastManager manager;

    public GlobalListener (Context context){
        EMClient.getInstance().contactManager().setContactListener(emContactListener);

        //本地广播
        /*
        * 本地广播 和 全局广播的区别？
        * 本地广播 ： 只有本应用可以收到
        * 全局广播 ： 所有的应用都可以收到
        *
        * */
        manager = LocalBroadcastManager.getInstance(context);
    }

    //设置全局监听
    private EMContactListener emContactListener = new EMContactListener() {

        //收到好友邀请  别人加你
        @Override
        public void onContactInvited(String username, String reason) {

            //添加邀请
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setUserInfo(new UserInfo(username,username));
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);

            Model.getInstance().getManager().getInvitationDAO().addInvitation(invitationInfo);


            //保存小红点
            SPUtils.getInstence().save(SPUtils.NEW_INVITE,true);

            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));


        }

        //好友请求被同意  你加别人的时候 别人同意了
        @Override
        public void onContactAgreed(String username) {

            //添加邀请信息
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setUserInfo(new UserInfo(username,username));
            invitationInfo.setReason("邀请成功");
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);

            Model.getInstance().getManager().getInvitationDAO().addInvitation(invitationInfo);

            //保存小红点
            SPUtils.getInstence().save(SPUtils.NEW_INVITE,true);

            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));

        }



        //被删除时回调此方法
        @Override
        public void onContactDeleted(String username) {

            //删除邀请信息
            Model.getInstance().getManager().getInvitationDAO().removeInvitation(username);

            //删除联系人
            Model.getInstance().getManager().getContactDAO().deleteContactByHxid(username);

            //发送广播
            manager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));

        }


        //增加了联系人时回调此方法  当你同意添加好友
        @Override
        public void onContactAdded(String username) {

            //添加联系人
            Model.getInstance().getManager().getContactDAO().saveContact(new UserInfo(username,username),true);

            //发送广播
            manager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));

        }

        //好友请求被拒绝  你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {

            //保存小红点
            SPUtils.getInstence().save(SPUtils.NEW_INVITE,true);

            //发送广播
            manager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));
        }
    };


}
