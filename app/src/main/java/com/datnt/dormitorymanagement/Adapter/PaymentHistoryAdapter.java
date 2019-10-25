package com.datnt.dormitorymanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datnt.dormitorymanagement.InAppModel.News;
import com.datnt.dormitorymanagement.InAppModel.Payment;
import com.datnt.dormitorymanagement.R;

import java.util.List;

public class PaymentHistoryAdapter extends BaseAdapter {
    private List<Payment> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public PaymentHistoryAdapter(Context mContext, List<Payment> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_payment_history, null);
            holder = new ViewHolder();
            holder.payment_Type = convertView.findViewById(R.id.list_item_payment_type);
            holder.payment_Price = convertView.findViewById(R.id.list_item_payment_price);
            holder.payment_Date = convertView.findViewById(R.id.list_item_payment_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Payment payment = this.listData.get(position);
        holder.payment_Type.setText(payment.getPaymentType());
        holder.payment_Price.setText(payment.getPaymentPrice());
        holder.payment_Date.setText(payment.getPaymentDate());
        return convertView;
    }

    static class ViewHolder {
        TextView payment_Type;
        TextView payment_Price;
        TextView payment_Date;
    }
}
