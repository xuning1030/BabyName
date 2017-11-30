package babyname.babyname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import babyname.babyname.R;
import babyname.babyname.Utils.NameData;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ResultsAdapter_wuge extends BaseAdapter {

    private List<NameData> datas;
    private LayoutInflater inflater;
    private ViewHolder holder = new ViewHolder();

    public ResultsAdapter_wuge(Context context, List<NameData> datas) {
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

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.results_page_wei_shang_item, null);

            holder.tv3 = (TextView) convertView.findViewById(R.id.wuge);
            holder.tv1 = (TextView) convertView.findViewById(R.id.Mtitle);
            holder.tv2 = (TextView) convertView.findViewById(R.id.Mtext);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv3.setText(datas.get(position).getWuge());
        holder.tv1.setText(datas.get(position).getName());
        holder.tv2.setText(datas.get(position).getNumber());

        return convertView;
    }

    private class ViewHolder {
        TextView tv1, tv2,tv3;
    }
}
