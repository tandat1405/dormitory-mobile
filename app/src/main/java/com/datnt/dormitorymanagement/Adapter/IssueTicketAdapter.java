package com.datnt.dormitorymanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datnt.dormitorymanagement.ApiResultModel.NewsResult;
import com.datnt.dormitorymanagement.ApiResultModel.ResultList;
import com.datnt.dormitorymanagement.R;

import java.util.List;

public class IssueTicketAdapter extends BaseAdapter {
    private List<ResultList> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public IssueTicketAdapter(Context mContext, List<ResultList> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_issue_ticket, null);
            holder = new ViewHolder();
            holder.ticket_Type = convertView.findViewById(R.id.tv_list_item_issue_type);
            holder.ticket_Owner = convertView.findViewById(R.id.tv_list_item_issue_owner_name);
            holder.ticket_OwnerRoom = convertView.findViewById(R.id.tv_list_item_issue_room_name);
            holder.ticket_Image = convertView.findViewById(R.id.iv_list_item_issue_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ResultList resultList = listData.get(position);
        holder.ticket_Type.setText("Loại yêu cầu: "+resultList.getTypeName());
        holder.ticket_Owner.setText("Tên sinh viên: "+resultList.getOwnerName());
        holder.ticket_OwnerRoom.setText("Phòng: "+resultList.getRoomName());
        if (resultList.getType() == 17){
            holder.ticket_Image.setImageResource(R.drawable.maintenance);
        }
        if (resultList.getType() == 18){
            holder.ticket_Image.setImageResource(R.drawable.complaint);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView ticket_Type;
        TextView ticket_Owner;
        TextView ticket_OwnerRoom;
        ImageView ticket_Image;
    }
}
