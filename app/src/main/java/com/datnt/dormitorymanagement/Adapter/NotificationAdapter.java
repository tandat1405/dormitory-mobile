package com.datnt.dormitorymanagement.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datnt.dormitorymanagement.InAppModel.Notification;
import com.datnt.dormitorymanagement.InAppModel.Payment;
import com.datnt.dormitorymanagement.R;

import java.util.List;

public class NotificationAdapter extends BaseAdapter {
    private List<Notification> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NotificationAdapter(Context mContext, List<Notification> listData) {
        this.context = mContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_notification, null);
            holder = new ViewHolder();
            holder.noti_Icon = convertView.findViewById(R.id.list_item_notification_image);
            holder.noti_Content = convertView.findViewById(R.id.list_item_notification_content);
            holder.noti_Time = convertView.findViewById(R.id.list_item_notification_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Notification notification = this.listData.get(position);
        if(notification.getNotiType().equals("Payment")){
            holder.noti_Icon.setImageResource(R.drawable.payment_noti);
        }
        if (notification.getNotiType().equals("Training point")){
            holder.noti_Icon.setImageResource(R.drawable.minus_notification);
        }
        holder.noti_Content.setText(notification.getNotiContent());
        holder.noti_Time.setText(notification.getNotiTimeReceived());
        return convertView;
    }

    static class ViewHolder {
        ImageView noti_Icon;
        TextView noti_Content;
        TextView noti_Time;
    }
}
