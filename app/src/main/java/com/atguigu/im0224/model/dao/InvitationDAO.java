package com.atguigu.im0224.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im0224.model.bean.InvitationInfo;
import com.atguigu.im0224.model.bean.UserInfo;
import com.atguigu.im0224.model.db.DBHelper;
import com.atguigu.im0224.model.table.InvitationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class InvitationDAO {

    private DBHelper dbHelper;

    public InvitationDAO(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    // 添加邀请
    public void addInvitation(InvitationInfo invitationInfo){

        if(invitationInfo == null) {
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(InvitationTable.COL_REASON,invitationInfo.getReason());
        contentValues.put(InvitationTable.COL_USER_HXID,invitationInfo.getUserInfo().getHxid());
        contentValues.put(InvitationTable.COL_USER_NAME,invitationInfo.getUserInfo().getUsername());
        contentValues.put(InvitationTable.COL_STATE,invitationInfo.getStatus().ordinal());

        database.replace(InvitationTable.TABLE_NAME,null,contentValues);
    }

    // 获取所有邀请信息
    public List<InvitationInfo> getInvitations(){

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String sql = "select * from " + InvitationTable.TABLE_NAME;

        Cursor cursor = database.rawQuery(sql, null);

        List<InvitationInfo> infos = new ArrayList<>();
        while (cursor.moveToNext()){
            InvitationInfo invitationInfo = new InvitationInfo();

            invitationInfo.setReason(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_REASON)));
            invitationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InvitationTable.COL_STATE))));
            UserInfo userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_NAME)));
            invitationInfo.setUserInfo(userInfo);

            //将invitationInfo添加到集合中
            infos.add(invitationInfo);
        }

        cursor.close();

        return infos;
    }


    // 删除邀请
    public void removeInvitation(String hxId){
        if(TextUtils.isEmpty(hxId)) {
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(InvitationTable.TABLE_NAME,InvitationTable.COL_USER_HXID +"=?",new String[]{hxId});
    }

    // 更新邀请状态
    public void updateInvitationStatus(InvitationInfo.InvitationStatus invitationStatus,String hxId){
        if(TextUtils.isEmpty(hxId)) {
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(InvitationTable.COL_STATE,invitationStatus.ordinal());
        database.update(InvitationTable.TABLE_NAME,contentValues,InvitationTable.COL_USER_HXID + "=?",new String[]{hxId});
    }




    // 将int类型状态转换为邀请的状态
    private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }
        return null;
    }

}
