package com.atguigu.im0224.common;

import android.content.Context;

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

    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){
        return service;
    }

    public void init(Context context){
        this.context = context;
    }

}
