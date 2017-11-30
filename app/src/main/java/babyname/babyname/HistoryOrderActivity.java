package babyname.babyname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import babyname.babyname.Utils.Constant;
import babyname.babyname.Utils.NoDoubleClickUtils;
import babyname.babyname.adapter.HistoryAdapter;
import babyname.babyname.bean.HistoryBean;

/**
 * Created by Administrator on 2017/7/6.
 */

public class HistoryOrderActivity extends BaseActivity {

    private String device;
    private ListView  history_list;
    private List<HistoryBean> mdata = new ArrayList<>(); ;
    private HistoryAdapter adapter;
    private TextView history_tv;
    private String isPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_order);
        setTitle("历史订单");
        SharedPreferences sharedPreferences = getSharedPreferences("test_user", Activity.MODE_PRIVATE);
        device=sharedPreferences.getString("device","");
        isPay=sharedPreferences.getString("ispay","0");
        history_tv= (TextView) findViewById(R.id.history_tv);
        history_list= (ListView)findViewById(R.id.history_list);
        if (Constant.isMeizu){
            if (isPay.equals("1")){
                okhttp();
            }
        }else{
            okhttp();
        }
        adapter = new HistoryAdapter(this, mdata);
        history_list.setAdapter(adapter);
        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    Intent intent = new Intent();
                    intent.setClass(HistoryOrderActivity.this, ResultActivity.class);
                    intent.putExtra("status",mdata.get(position).getStatus());
                    intent.putExtra("paych",mdata.get(position).getPaych());
                    intent.putExtra("order",mdata.get(position).getOrderno());
                    intent.putExtra("history","1");
                    startActivity(intent);
                }

            }
        });
    }
    private void okhttp() {
        showTipMsg("加载中.....");
        String url = "http://api.webqm.itobike.com/cgi/api.ashx/HistoryOrderno?device="+device;
        System.out.println(">>>>>>>>>"+url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                Toast.makeText(HistoryOrderActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response, int id) {
                mProgressDialog.dismiss();
                Log.i("Tag", response);
//                        System.out.println(response+"<<<<");
                JSONArray arr = null;
                try {
                    JSONObject jo = new JSONObject(response);
                    arr = jo.getJSONArray("result_content");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = null;
                        temp = (JSONObject) arr.get(i);
                        String orderno = temp.getString("orderno");
                        String status = temp.getString("status");
                        String paych = temp.getString("paych");
                        String name = temp.getString("name");
                        mdata.add(new HistoryBean(orderno, status, paych,name));
                        adapter.notifyDataSetChanged();
                    }
                    if (mdata.size()==0){
                        history_tv.setText("暂无历史，请下单后查询");
                    }else {
                        history_tv.setText("以下是您的历史订单，点击查看详情");
                    }
//                    System.out.println(">>>>>>>>"+mdata.get(0).getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAfter(int id) {

            }
        });
    }
}
