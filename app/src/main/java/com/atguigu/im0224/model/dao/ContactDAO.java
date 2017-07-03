package com.atguigu.im0224.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im0224.model.bean.UserInfo;
import com.atguigu.im0224.model.db.DBHelper;
import com.atguigu.im0224.model.table.ContactTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ContactDAO {

    private DBHelper dbHelper;

    public ContactDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    // 获取所有联系人
    public List<UserInfo> getContacts() {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //"=1"是联系人的状态
        String sql = "select * from " + ContactTable.TABLE_NAME
                + " where " + ContactTable.COL_IS_CONTACT + "=1";

        Cursor cursor = database.rawQuery(sql, null);

        List<UserInfo> userInfos = new ArrayList<>();

        while (cursor.moveToNext()) {

            UserInfo userInfo = new UserInfo();
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));

            userInfos.add(userInfo);
        }

        cursor.close();

        return userInfos;
    }

    // 通过环信id获取联系人单个信息
    public UserInfo getContactByHx(String hxid) {
        //校验
        if (TextUtils.isEmpty(hxid)) {
            return null;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String sql = "select * from " + ContactTable.TABLE_NAME
                + " where " + ContactTable.COL_USER_HXID + "=?";

        Cursor cursor = database.rawQuery(sql, new String[]{hxid});
        UserInfo userInfo = new UserInfo();
        while (cursor.moveToNext()) {
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));
        }

        cursor.close();
        return userInfo;
    }

    // 通过环信id获取用户联系人信息
    public List<UserInfo> getContactByHx(List<String> hxids) {
        //校验
        if (hxids == null || hxids.size() == 0) {
            return null;
        }
        List<UserInfo> userInfos = new ArrayList<>();
        for (int i = 0; i < hxids.size(); i++) {
            UserInfo userInfo = getContactByHx(hxids.get(i));

            userInfos.add(userInfo);
        }

        return userInfos;
    }

    // 保存单个联系人
    public void saveContact(UserInfo userInfo, boolean isMyContact){

        //校验
        if(userInfo == null) {
            throw new NullPointerException("userInfo 不能为空");
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.COL_USER_HXID,userInfo.getHxid());
        contentValues.put(ContactTable.COL_USER_NAME,userInfo.getUsername());
        contentValues.put(ContactTable.COL_USER_NICK,userInfo.getNick());
        contentValues.put(ContactTable.COL_USER_PHOTO,userInfo.getPhoto());
        contentValues.put(ContactTable.COL_IS_CONTACT,isMyContact?1:0);

        database.replace(ContactTable.TABLE_NAME,null,contentValues);

    }

    // 保存联系人信息
    public void saveContacts(List<UserInfo> userInfos,boolean isMyContact){
        
        //校验
        if(userInfos == null || userInfos.size() == 0) {
            return;
        }

        for(int i = 0; i < userInfos.size(); i++) {
          saveContact(userInfos.get(i),isMyContact);
        }
    }

    // 删除联系人信息
    public void deleteContactByHxid(String hxid){
        if(TextUtils.isEmpty(hxid)) {
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(ContactTable.TABLE_NAME,ContactTable.COL_USER_HXID + "=?",new String[]{hxid});
    }
}
