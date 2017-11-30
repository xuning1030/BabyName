package babyname.babyname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import babyname.babyname.R;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<String> list){
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }
    /**
     * 将数据循环展示三遍
     */
    @Override
    public int getCount() {

        if(list != null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {

        return list.get(arg0 % list.size());
    }

    @Override
    public long getItemId(int arg0) {
        return arg0 % list.size();
    }
    @Override
    public View getView(int postition, View convertView, ViewGroup arg2) {
        ViewHoler viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.list_item, null);
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHoler) convertView.getTag();
        }
        viewHolder.tvText.setText(list.get(postition % list.size()));//取余展示数据
        return convertView;
    }

    static class ViewHoler{
        TextView tvText;
    }

}
