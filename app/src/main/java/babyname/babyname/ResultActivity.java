package babyname.babyname;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.lbsw.stat.LBStat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import babyname.babyname.Utils.Constant;
import babyname.babyname.Utils.NameData;
import babyname.babyname.Utils.NoDoubleClickUtils;
import babyname.babyname.Utils.SPUtil;
import babyname.babyname.adapter.NameAdapter;
import babyname.babyname.bean.FtResultBean;
import babyname.babyname.bean.NameListBean;
import babyname.babyname.fragment.NameDialogFramentpay;
import babyname.babyname.widget.AutoRefreshListView;
import okhttp3.Call;

public class ResultActivity extends BaseActivity implements View.OnClickListener {
    private String text = "";
    private AutoRefreshListView listview3;
    private NameAdapter adapter;
    private TextView Ordernumber;
    private List<NameData> list = new ArrayList<>();
    private Intent getIntent;
    private String order;
    private String status;
    private String paych;
    private String setUrl;
    private List<NameListBean> mdata = new ArrayList<>();
    private int pageindex = 1;
    private String history;


    private String orderno;
    private float fMoney = 29.9f;
    private String payType = "pay";
    private String pay_type;
    private String isPay = "0";//是否支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_result);
        getIntent = getIntent();
        order = getIntent.getStringExtra("order");
        status = getIntent.getStringExtra("status");
        paych = getIntent.getStringExtra("paych");
        history = getIntent.getStringExtra("history");
        LBStat.click(1068);
        okhttp();
        init();
        setTitle("取名结果");
        AdaPterVoid();
    }

    private void init() {
        listview3 = (AutoRefreshListView) findViewById(R.id.listview3);
        Ordernumber = (TextView) findViewById(R.id.Ordernumber);
        Ordernumber.setText("订单号：" + order);
        listview3.setIsFooterVisible(true);


    }

    private void AdaPterVoid() {
        adapter = new NameAdapter(this, mdata);
        listview3.setAdapter(adapter);
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!NoDoubleClickUtils.isDoubleClick()) {

                    //魅族版本
                    if (Constant.isMeizu) {
                        if (history.equals("0")) {
                            if (!isPay.equals("")) {
                                if (isPay.equals("1")) {
                                    GetUrl(mdata.get(position - 1).getId(),mdata.get(position - 1).getName());
                                } else {
                                    doOnclick();
                                }
                            }
                        } else if (history.equals("1")) {
                            GetUrl(mdata.get(position - 1).getId(),mdata.get(position - 1).getName());
                        }

                    } else {
                        //正常版本
                        GetUrl(mdata.get(position - 1).getId(),mdata.get(position - 1).getName());
                    }


                }


            }
        });
        listview3.setOnRefreshListener(new AutoRefreshListView.OnRefreshListener() {
            @Override
            public void toHeadRefresh() {
            }

            @Override
            public void toFooterRefresh() {

                //魅族版本
                if (Constant.isMeizu) {
                    if (history.equals("0")) {
                        if (!isPay.equals("")) {
                            if (isPay.equals("1")) {
                                pageindex++;
                                okhttp2();
                            } else {
                                doOnclick();
                            }
                        }
                    } else if (history.equals("1")) {
                        pageindex++;
                        okhttp2();
                    }
                } else {

                    //正常版本
                    pageindex++;
                    okhttp2();
                }


            }
        });
//        mProgressDialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy:
                text = Ordernumber.getText().toString();
                ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(text.substring(4, text.length()));
                Toast.makeText(this, "复制成功。", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //支付弹窗
    public void doOnclick() {

        NameDialogFramentpay dialogFragment = new NameDialogFramentpay();

        dialogFragment.setAliPayActivityListener(new NameDialogFramentpay.AlipayActivityListener() {
            @Override
            public void AlipayActivityListener() {
                pay_type = "alipay";
                getAliUrl(pay_type);

            }
        });

        dialogFragment.setWxPayActivityListener(new NameDialogFramentpay.WxpayActivityListener() {
            @Override
            public void WxpayActivityListener() {
                pay_type = "weixin";
                getAliUrl(pay_type);
            }
        });

        dialogFragment.show(getFragmentManager(), "pay");

    }


    /**
     * 获取支付宝订单号
     */
    private void getAliUrl(String pay_type) {
        //订单号
        fMoney = 29.9f;
        orderno = "getName" + System.currentTimeMillis();
        String url = "http://project4.itobike.com/cgi/pay.ashx/pay.do?orderamt=" + Constant.pay2990 + "&orderno=" + orderno + "&paytype=" + pay_type;
        LBStat.pay(pay_type, orderno, false, payType, fMoney, "xxb");
        Intent intent = new Intent(ResultActivity.this, PayPageActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("bundle", bundle);
        bundle.putString(Constant.url, url);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (orderno != null) {
            payWay();
        }
    }
    /**
     * 根据订单号查询支付状态，支付宝
     */
    private void payWay() {
        String url = "http://project4.itobike.com/cgi/pay.ashx/order/info.json?orderno=" + orderno + "&paytype=" + pay_type;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        saveResult(response);
                    }
                });
    }


    /**
     * 支付宝支付成功或失败
     *
     * @param response 返回状态码
     */
    private void saveResult(String response) {
        Gson gson = new Gson();
        System.out.println(response);
        FtResultBean bean = gson.fromJson(response, FtResultBean.class);
        boolean isSuccess = "A001".equals(bean.getResponseCode());//支付是否成功
        if (isSuccess) {//成功//错题集
            LBStat.pay(pay_type, orderno, true, payType, fMoney, "xxb");
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            isPay="1";
            SPUtil.put(getApplication(), "ispay", "1");
            orderno = null;
        } else {
            Intent intent = new Intent();
            intent.setClass(this, PayResultActivity.class);
            intent.putExtra("num", order);
            intent.putExtra("orderno", orderno);
            intent.putExtra("pay_type", pay_type);
            startActivity(intent);
            orderno = null;
        }
    }

    private void okhttp() {
        showTipMsg("加载中.....");
        String url = "http://api.webqm.itobike.com/cgi/api.ashx/GetOrder_status_notify?status=" + status + "&paych=" + paych + "&orderno=" + order + "&orderamt=" + Constant.pay2990 + "&pageindex=" + pageindex;
        System.out.println(">>>>>>>>>" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                Toast.makeText(ResultActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                mProgressDialog.dismiss();
                Log.i("Tag", response);
                System.out.println(response+"<<<<");
                okhttp2();
//                JSONArray arr = null;
//                try {
//                    JSONObject jo = new JSONObject(response);
//                    arr = jo.getJSONArray("result_content");
//                    for (int i = 0; i < arr.length(); i++) {
//                        JSONObject temp = null;
//                        temp = (JSONObject) arr.get(i);
//                        String id1 = temp.getString("id");
//                        String man = temp.getString("man");
//                        String surname = temp.getString("surname");
//                        String name = temp.getString("name");
//                        String mark = temp.getString("mark");
//                        mdata.add(new NameListBean(id1, man, surname, name, mark));
//                        adapter.notifyDataSetChanged();
//                    }
//                    if (pageindex > 1) {
//                        mdata.addAll(mdata);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onAfter(int id) {
                listview3.onRefreshFinished(true);
//                        Intent intent1=new Intent();
//                        intent1.setClass(ResultActivity.this, OrderActivity.class);
//                        startActivity(intent1);
            }
        });
    }


    private void okhttp2() {
        String url = "http://api.webqm.itobike.com/cgi/api.ashx/GetNameinfpPage?orderno=" + order + "&pageindex=" + pageindex;
        System.out.println(">>>>>>>>>" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                Toast.makeText(ResultActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("Tag", response);
                JSONArray arr = null;
                try {
                    JSONObject jo = new JSONObject(response);
                    arr = jo.getJSONArray("result_content");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = null;
                        temp = (JSONObject) arr.get(i);
                        String id1 = temp.getString("id");
                        String man = temp.getString("man");
                        String surname = temp.getString("surname");
                        String name = temp.getString("name");
                        String mark = temp.getString("mark");
                        mdata.add(new NameListBean(id1, man, surname, name, mark));
                        adapter.notifyDataSetChanged();
                    }
//                            if (pageindex>1){
//                                mdata.addAll(mdata);
//                            }
                    System.out.println(">>>>>>>>" + mdata.get(0).getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAfter(int id) {
                listview3.onRefreshFinished(true);
            }
        });
    }
    private void GetUrl(String id,String name) {
        String url = "http://api.webqm.itobike.com/cgi/api.ashx/resultByid?id=" + id+"&name="+name;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Toast.makeText(ResultActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        setUrl = response;
                    }
                    @Override
                    public void onAfter(int id) {
                        Intent intent1 = new Intent();
                        intent1.setClass(ResultActivity.this, H5Activity.class);
                        intent1.putExtra("url", setUrl);
                        intent1.putExtra("order", order);
                        startActivity(intent1);
                    }
                });


    }

}
