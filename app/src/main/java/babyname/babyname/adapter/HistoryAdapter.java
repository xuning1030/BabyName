package babyname.babyname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import babyname.babyname.R;
import babyname.babyname.bean.HistoryBean;

/**
 * Created by Administrator on 2017/7/7.
 */

public class HistoryAdapter extends BaseAdapter {

    private List<HistoryBean> datas;
    private LayoutInflater inflater;
//    private ViewHolder holder = new ViewHolder();

    public HistoryAdapter(Context context, List<HistoryBean> datas) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas == null ? null : datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryAdapter.ViewHolder holder=null;
        if (convertView == null) {
            holder = new HistoryAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.history_list_item, null);
            holder.history_item_name = (TextView) convertView.findViewById(R.id.history_item_name);
            holder.history_item_order = (TextView) convertView.findViewById(R.id.history_item_order);
            convertView.setTag(holder);

        } else {
            holder = (HistoryAdapter.ViewHolder) convertView.getTag();
        }

        holder.history_item_name.setText(datas.get(position).getName());
        holder.history_item_order.setText(datas.get(position).getOrderno());

        return convertView;
    }

    private class ViewHolder {
        TextView history_item_name, history_item_order;
    }
}
