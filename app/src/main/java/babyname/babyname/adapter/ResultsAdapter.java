package babyname.babyname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import babyname.babyname.R;
import babyname.babyname.Utils.ResultsData;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ResultsAdapter extends BaseAdapter {
    private List<ResultsData> datas;
    private LayoutInflater inflater;
    private ViewHolder holder = new ViewHolder();

    public ResultsAdapter(Context context, List<ResultsData> datas) {
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

        if(convertView==null){

            convertView=inflater.inflate(R.layout.results_page_shen_item,null);
            holder.tv1= (TextView) convertView.findViewById(R.id.tv1);
            holder.tv2= (TextView) convertView.findViewById(R.id.tv2);
            holder.tv3= (TextView) convertView.findViewById(R.id.tv3);
            holder.tv4= (TextView) convertView.findViewById(R.id.tv4);
            holder.tv5= (TextView) convertView.findViewById(R.id.tv5);
            holder.tv6= (TextView) convertView.findViewById(R.id.tv6);
            holder.tv7= (TextView) convertView.findViewById(R.id.tv7);
            holder.tv8= (TextView) convertView.findViewById(R.id.tv8);
            holder.tv9= (TextView) convertView.findViewById(R.id.tv9);
            holder.tv10= (TextView) convertView.findViewById(R.id.tv10);
            holder.tv11= (TextView) convertView.findViewById(R.id.tv11);
            holder.tv12= (TextView) convertView.findViewById(R.id.tv12);
            convertView.setTag(holder);

        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.tv1.setText(datas.get(position).getTv1());
        holder.tv2.setText(datas.get(position).getTv2());
        holder.tv3.setText(datas.get(position).getTv3());
        holder.tv4.setText(datas.get(position).getTv4());
        holder.tv5.setText(datas.get(position).getTv5());
        holder.tv6.setText(datas.get(position).getTv6());
        holder.tv7.setText(datas.get(position).getTv7());
        holder.tv8.setText(datas.get(position).getTv8());
        holder.tv9.setText(datas.get(position).getTv9());
        holder.tv10.setText(datas.get(position).getTv10());
        holder.tv11.setText(datas.get(position).getTv11());
        holder.tv12.setText(datas.get(position).getTv12());

        return convertView;
    }

    private class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;
    }
}
