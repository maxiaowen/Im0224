package com.atguigu.im0224.common;

import android.content.Context;

import com.atguigu.im0224.model.bean.UserInfo;
import com.atguigu.im0224.model.dao.AccountDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/1.
 */

public class Model {

    private Model(){}

    private static Model model = new Model();

    public static Model getInstance (){
        return model;
    }

    private Context context;
    private AccountDAO accountDAO;

    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){
        return service;
    }

    public void init(Context context){
        this.context = context;
        this.accountDAO = new AccountDAO(context);
    }

    //登录成功以后保存用户数据
    public void loginSuccess(UserInfo userInfo){

        //添加用户
        accountDAO.addAccount(userInfo);

    }
    public AccountDAO getAccountDAO(){
        return accountDAO;
    }

}
