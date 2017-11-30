package babyname.babyname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import babyname.babyname.R;
import babyname.babyname.bean.NameListBean;

/**
 * Created by Administrator on 2017/7/5.
 */

public class NameAdapter extends BaseAdapter {

    private List<NameListBean> datas;
    private LayoutInflater inflater;
//   private  ViewHolder holder =new ViewHolder();

    public NameAdapter(Context context, List<NameListBean> datas) {
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
         ViewHolder holder =null;
        if (convertView == null) {
            holder= new ViewHolder();
            convertView = inflater.inflate(R.layout.name_result_item, null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.name);
            holder.tv2 = (TextView) convertView.findViewById(R.id.Number);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NameListBean bean = (NameListBean) getItem(position);

        holder.tv1.setText(bean.getName());
        holder.tv2.setText(bean.getMark());

        return convertView;
    }

    private class ViewHolder {
        TextView tv1, tv2;
    }

}
