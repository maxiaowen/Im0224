package com.atguigu.im0224.controller.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.atguigu.im0224.R;
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
