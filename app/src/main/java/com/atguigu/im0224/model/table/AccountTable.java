package com.atguigu.im0224.model.table;

/**
 * Created by Administrator on 2017/7/1.
 */
//        create table student(id text primary key,name text)
//
//        insert into student(id,name) values('10','小福')
//
//        insert into student(name) values('小福')
//
//        insert into student(id,name) values('1','小仓')
//
//        insert into student(id,name) values('2','志玲')
//
//        update student set  id = '3' where name = '志玲'
//
//        select name from student where id = '1'
//
//        delete from student where id = '1'

public class AccountTable {

    public static final String TABLE_NAME = "account";
    public static final String COL_USERNAME = "username";
    public static final String COL_HXID = "hxid";
    public static final String COL_PHOTO = "photo";
    public static final String COL_NICK = "nick";

    public static final String CREATE_TABLE = "create table "+TABLE_NAME+"("
            +COL_HXID+" text primary key, "
            +COL_USERNAME +" text, "
            +COL_PHOTO +" text, "
            +COL_NICK +" text)";
}
