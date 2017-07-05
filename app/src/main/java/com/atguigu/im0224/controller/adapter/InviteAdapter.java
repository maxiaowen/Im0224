package com.atguigu.im0224.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.im0224.R;
import com.atguigu.im0224.model.bean.InvitationInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/4.
 */

public class InviteAdapter extends BaseAdapter {

    private Context context;

    public InviteAdapter(Context context) {
        this.context = context;
    }

    private List<InvitationInfo> datas = new ArrayList<>();

    public void refresh(List<InvitationInfo> list) {
        //校验
        if (list != null && list.size() >= 0) {
            this.datas.clear();
            this.datas.addAll(list);
        }
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_invite, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final InvitationInfo info = datas.get(position);

        viewHolder.tvInviteName.setText(info.getUserInfo().getUsername());
        viewHolder.btInviteAccept.setVisibility(View.GONE);
        viewHolder.btInviteReject.setVisibility(View.GONE);

        //邀请信息的三种状态
        if(info.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT) {
            if(info.getReason() == null) {
                viewHolder.tvInviteReason.setText("接受邀请");
            }else {
                viewHolder.tvInviteReason.setText(info.getReason());
            }
        }

        if(info.getStatus() == InvitationInfo.InvitationStatus.NEW_INVITE) {
            if(info.getReason() == null) {
                viewHolder.tvInviteReason.setText("新邀请");
            }else {
                viewHolder.tvInviteReason.setText(info.getReason());
            }

            viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
            viewHolder.btInviteReject.setVisibility(View.VISIBLE);

            //接受邀请的监听
            viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onInviteListener != null) {
                        onInviteListener.invitedSuccess(info);
                    }
                }
            });
            
            //拒绝的监听
            viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onInviteListener != null) {
                        onInviteListener.invitedReject(info);
                    }
                }
            });

        }
        
        if(info.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {
            if(info.getReason() == null) {
                viewHolder.tvInviteReason.setText("邀请被接受");
            }else {
                viewHolder.tvInviteReason.setText(info.getReason());
            }
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_invite_name)
        TextView tvInviteName;
        @Bind(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @Bind(R.id.bt_invite_accept)
        Button btInviteAccept;
        @Bind(R.id.bt_invite_reject)
        Button btInviteReject;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnInviteListener onInviteListener;

    public void setOnInviteListener(OnInviteListener onInviteListener){

        this.onInviteListener = onInviteListener;
    }

    //回调接口
    public interface OnInviteListener{
        void invitedSuccess(InvitationInfo info); //接受邀请
        void invitedReject(InvitationInfo info); //拒绝邀请
    }
}
