package com.atguigu.im0224.controller.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.atguigu.im0224.R;
import com.atguigu.im0224.controller.activity.AddContactActivity;
import com.atguigu.im0224.utils.UIUtils;
import com.hyphenate.easeui.ui.EaseContactListFragment;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ContactListFragment extends EaseContactListFragment {

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void setUpView() {
        super.setUpView();

        //初始化Listview头布局
        initHeadView();

        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddContactActivity.class));
            }
        });
    }


    private void initHeadView() {
        View headView = View.inflate(getActivity(), R.layout.head_view,null);

        final LinearLayout friends = (LinearLayout) headView.findViewById(R.id.ll_new_friends);

        listView.addHeaderView(headView);

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast(friends+"");
            }
        });
    }
}
