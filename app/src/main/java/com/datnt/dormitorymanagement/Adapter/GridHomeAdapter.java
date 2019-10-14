package com.datnt.dormitorymanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datnt.dormitorymanagement.InAppModel.Home;
import com.datnt.dormitorymanagement.R;

import java.util.List;

public class GridHomeAdapter extends BaseAdapter {
    private List<Home> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public GridHomeAdapter(Context mContext, List<Home> listData) {
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
            convertView = layoutInflater.inflate(R.layout.grid_item_home, null);
            holder = new ViewHolder();
            holder.homeImage = convertView.findViewById(R.id.grid_home_image);
            holder.homeName = convertView.findViewById(R.id.grid_home_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Home home = this.listData.get(position);
        holder.homeImage.setImageResource(home.getImgRes());
        holder.homeName.setText(home.getName());
        return convertView;
    }
    static class ViewHolder {
        ImageView homeImage;
        TextView homeName;
    }
}
