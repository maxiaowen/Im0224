package com.atguigu.im0224.common;

import android.content.Context;

import com.atguigu.im0224.model.bean.UserInfo;
import com.atguigu.im0224.model.dao.AccountDAO;
import com.atguigu.im0224.model.manager.HelperManager;
import com.atguigu.im0224.utils.SPUtils;

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

    private HelperManager manager;

    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){
        return service;
    }

    public void init(Context context){
        this.context = context;
        this.accountDAO = new AccountDAO(context);

        //初始化全局监听
        new GlobalListener(context);
    }

    //登录成功以后保存用户数据
    public void loginSuccess(UserInfo userInfo){

        //添加用户
        accountDAO.addAccount(userInfo);

        if(manager != null) {
            manager.close();
        }

        //创建HelperManager
        manager = new HelperManager(context,userInfo.getUsername()+".db");

        //初始化SPUtils

        SPUtils.getInstence().init(context,userInfo.getUsername());

    }
    public AccountDAO getAccountDAO(){
        return accountDAO;
    }

    /*
        * 返回HelperManager的对象
        * */
    public HelperManager getManager() {
        return manager;
    }
}
