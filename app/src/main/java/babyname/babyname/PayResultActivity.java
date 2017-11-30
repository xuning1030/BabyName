package babyname.babyname;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lbsw.stat.LBStat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import babyname.babyname.Utils.Constant;
import babyname.babyname.Utils.NoDoubleClickUtils;
import babyname.babyname.bean.FtResultBean;

/**
 * Created by Administrator on 2017/7/31.
 */

public class PayResultActivity extends BaseActivity implements View.OnClickListener {

    private int recLen = 0;
    private TextView pay_top_tv, pay_order_tv, pay_time_tv, pay_phone_tv, pay_qq_tv;
    private LinearLayout pay_line;
    private Button wait_btn, pay_btn;
    private Intent getIntent;
    private String num, orderno, pay_type;
    private float fMoney = 29.9f;
    private String payType = "pay";
    private String status;
    private Boolean iscare = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        getIntent = getIntent();
        num = getIntent.getStringExtra("num");
        orderno = getIntent.getStringExtra("orderno");
        pay_type = getIntent.getStringExtra("pay_type");
        findViewAll();


    }

    private void findViewAll() {
        pay_top_tv = (TextView) findViewById(R.id.pay_top_tv);
        pay_order_tv = (TextView) findViewById(R.id.pay_order_tv);
        pay_time_tv = (TextView) findViewById(R.id.pay_time_tv);
        pay_phone_tv = (TextView) findViewById(R.id.pay_phone_tv);
        pay_qq_tv = (TextView) findViewById(R.id.pay_qq_tv);
        pay_order_tv.setText("订单号：" + num);
        pay_line = (LinearLayout) findViewById(R.id.pay_line);
        wait_btn = (Button) findViewById(R.id.wait_btn);
        pay_btn = (Button) findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(this);
        wait_btn.setOnClickListener(this);
        pay_qq_tv.setOnClickListener(this);
        pay_phone_tv.setOnClickListener(this);

    }

    final Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recLen++;
                    if (recLen % 10 == 0) {
                        payWay();
                    }
                    if (recLen >= 180) {
                        pay_top_tv.setText("当前消耗时间：3分钟");
                        pay_time_tv.setText("您的订单出现问题，请联系我们的客服人员！");
                    } else {
                        pay_top_tv.setText("当前消耗时间：" + recLen + "秒");
                        pay_time_tv.setText("您的订单已生成，后台正在处理中，请稍等，后台已处理时间：" + recLen + "秒");
                        pay_line.setVisibility(View.VISIBLE);
                    }

            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wait_btn:
               finish();
                break;
            case R.id.pay_btn:
                if(!NoDoubleClickUtils.isDoubleClick()) {
                    new Thread(new MyThread()).start();      // start thread
                    pay_btn.setEnabled(false);
                }
                break;
            case R.id.pay_phone_tv:
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17196338071"));
                startActivity(intent1);
                break;
            case R.id.pay_qq_tv:
                if (!isQQClientAvailable(this)) {
                    Toast.makeText(PayResultActivity.this, "未安装QQ，请拨打电话", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=3282726464";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

                break;
            default:
                break;
        }


    }


    @Override
    protected void onStop() {
        iscare=false;
        super.onStop();
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
            status = "TRADE_SUCCESS";
            okhttp();
            orderno = null;
        } else {

        }
    }


    private void okhttp() {
        showTipMsg("加载中.....");
        String url = "http://api.hehun.itobike.com/cgi/api.ashx/GetOrder_status_notify?status=" + status + "&paych=" + pay_type + "&orderno=" + num + "&orderamt=" + Constant.pay2990;
        System.out.println(">>>>>>>>>" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                mProgressDialog.dismiss();
                Toast.makeText(PayResultActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                mProgressDialog.dismiss();
                Log.i("Tag", response);
                if (response.length() > 20) {
                    setH5(response);
                }
//

            }

            @Override
            public void onAfter(int id) {

            }
        });
    }

    private void setH5(String response) {
        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
        intent.putExtra("status",status);
        intent.putExtra("paych",pay_type);
        intent.putExtra("order",num);
        startActivity(intent);

    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
//                LogUtils.e("pn = "+pn);
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    public class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (iscare) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }

            }
        }
    }
}