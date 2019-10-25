package com.datnt.dormitorymanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datnt.dormitorymanagement.ApiResultModel.NewsResult;
import com.datnt.dormitorymanagement.InAppModel.Home;
import com.datnt.dormitorymanagement.InAppModel.News;
import com.datnt.dormitorymanagement.R;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<NewsResult> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsAdapter(Context mContext, List<NewsResult> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_news, null);
            holder = new ViewHolder();
            holder.news_Title = convertView.findViewById(R.id.list_item_news_title);
            holder.news_Content = convertView.findViewById(R.id.list_item_news_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsResult news = this.listData.get(position);
        holder.news_Title.setText(news.getTitle());
        holder.news_Content.setText(news.getContent());
        return convertView;
    }

    static class ViewHolder {
        TextView news_Title;
        TextView news_Content;
    }
}
