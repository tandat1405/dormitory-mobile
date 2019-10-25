package com.datnt.dormitorymanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datnt.dormitorymanagement.InAppModel.News;
import com.datnt.dormitorymanagement.InAppModel.RoomFees;
import com.datnt.dormitorymanagement.R;

import java.util.List;

public class RoomFeesAdapter extends BaseAdapter {
    private List<RoomFees> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public RoomFeesAdapter(Context mContext, List<RoomFees> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_list_room, null);
            holder = new ViewHolder();
            holder.roomName = convertView.findViewById(R.id.list_item_fee_room_name);
            holder.electricNumber = convertView.findViewById(R.id.list_item_fee_electric_number);
            holder.waterNumber = convertView.findViewById(R.id.list_item_fee_water_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RoomFees roomFee = this.listData.get(position);
        holder.roomName.setText(roomFee.getRoomName());
        holder.electricNumber.setText(roomFee.getElectricNumber()+"");
        holder.waterNumber.setText(roomFee.getWaterNumber()+"");
        return convertView;
    }

    static class ViewHolder {
        TextView roomName;
        TextView waterNumber;
        TextView electricNumber;
    }
}
