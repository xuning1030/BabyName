package babyname.babyname;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lbsw.stat.LBStat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import babyname.babyname.Utils.Add_data;
import babyname.babyname.Utils.Constant;
import babyname.babyname.bean.FtResultBean;

/**
 * Created by Administrator on 2017/7/3.
 */

public class H5Activity extends BaseActivity implements View.OnClickListener{
    private WebView WVH5;
    private ProgressBar PBH5;
    private String url;
    private String id;
    private ImageView h5_btn;


    private float fMoney = 29.9f;
    private String payType = "pay";
    private Add_data add_data;
    private CheckBox master_check1,master_check2,master_check3,master_check4,master_check5;
    private EditText people_phone_edt;
    private LinearLayout alipay_master,wxpay_master;
    private String masterOrderno,masterMoney,masterPay,peoplePhone,order;
    @SuppressWarnings("deprecation")
    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface", "NewApi" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_activity);
        getIntentData();
        add_data=new Add_data();
        findAllVIew();
        System.out.println(",》《》《》《》"+order);
        setTitle("名字分析");
        WVH5.getSettings().setJavaScriptEnabled(true); // 加上这句话才能使用javascript方法
        WVH5.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        WVH5.requestFocus();// 如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件�?
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型�?
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：�?应内容大�?
         * 2、LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        WVH5.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        //启动缓存
        WVH5.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        WVH5.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        WVH5.getSettings().setSupportZoom(true);//支持缩放
        WVH5.addJavascriptInterface(new JavaScriptInterface(this), "demo");//h5回调java函数

        if(Build.VERSION.SDK_INT >= 19) {//页面finish后再发起图片加载
            WVH5.getSettings().setLoadsImagesAutomatically(true);
        } else {
            WVH5.getSettings().setLoadsImagesAutomatically(false);
        }
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//加载白块同时界面闪烁现象 关闭硬件加�?
			WVH5.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}*/

        WVH5.setWebChromeClient(new WebChromeClient() {
            // 进度�?
            public void onProgressChanged(WebView view, int newProgress) {
                PBH5.setProgress(newProgress);
                if (newProgress == 100) {
                    // 这里是设置activity的标题， 也可以根据自己的�?��做一些其他的操作
                    PBH5.setVisibility(View.GONE);
                } else {
                    PBH5.setProgress(newProgress);
                }
            }
        });

        WVH5.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                PBH5.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {//页面finish后再发起图片加载
                if(!WVH5.getSettings().getLoadsImagesAutomatically()) {
                    WVH5.getSettings().setLoadsImagesAutomatically(true);
                }
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.proceed();  // 接受信任�?��网站的证�?
                Toast.makeText(H5Activity.this, "", Toast.LENGTH_LONG);
                handler.cancel();   // 默认操作 不处�?
                // handler.handleMessage(null);  // 可做其他处理
            }
        });

        WVH5.setOnLongClickListener(new View.OnLongClickListener() {//屏蔽系统长按复制 粘贴事件
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        Map<String,String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Referer", "http://www.baidu.com");//防止盗链
        WVH5.loadUrl(url,extraHeaders);
    }

    private void getIntentData(){
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        order=intent.getStringExtra("order");
    }
    private void findAllVIew() {
        WVH5 = (WebView) findViewById(R.id.WVH5);
        PBH5 = (ProgressBar) findViewById(R.id.PBH5);
        h5_btn= (ImageView) this.findViewById(R.id.h5_btn);
        h5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(H5Activity.this,SetMoneyActivity.class);
                startActivity(intent);
            }
        });
        people_phone_edt= (EditText) findViewById(R.id.people_phone_edt);
        master_check1= (CheckBox) findViewById(R.id.master_check1);
        master_check2= (CheckBox) findViewById(R.id.master_check2);
        master_check3= (CheckBox) findViewById(R.id.master_check3);
        master_check4= (CheckBox) findViewById(R.id.master_check4);
        master_check5= (CheckBox) findViewById(R.id.master_check5);
        master_check1.setOnClickListener(this);
        master_check2.setOnClickListener(this);
        master_check3.setOnClickListener(this);
        master_check4.setOnClickListener(this);
        master_check5.setOnClickListener(this);
        alipay_master= (LinearLayout) findViewById(R.id.alipay_master);
        wxpay_master= (LinearLayout) findViewById(R.id.wxpay_master);
        alipay_master.setOnClickListener(this);
        wxpay_master.setOnClickListener(this);




    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (WVH5.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            WVH5.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    class JavaScriptInterface{
        Context mContext;
        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }
        /** Show a toast from the web page
         * 由Js调用执行Native本地Java方法
         */
        @JavascriptInterface
        public void showToast(String toast) {
            //Log.d("TAG", "Js Invoker Native Function");
            WVH5.loadUrl("javascript:init('abc')");
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.master_check1:
                masterMoney="18800";
                fMoney=188f;
                master_check2.setChecked(false);
                master_check3.setChecked(false);
                master_check4.setChecked(false);
                master_check5.setChecked(false);
                break;
            case R.id.master_check2:
                masterMoney="26800";
                fMoney=268f;
                master_check1.setChecked(false);
                master_check3.setChecked(false);
                master_check4.setChecked(false);
                master_check5.setChecked(false);
                break;
            case R.id.master_check3:
                masterMoney="56800";
                fMoney=568f;
                master_check2.setChecked(false);
                master_check1.setChecked(false);
                master_check4.setChecked(false);
                master_check5.setChecked(false);
                break;
            case R.id.master_check4:
                masterMoney="96800";
                fMoney=968f;
                master_check2.setChecked(false);
                master_check3.setChecked(false);
                master_check1.setChecked(false);
                master_check5.setChecked(false);
                break;
            case R.id.master_check5:
                masterMoney="106800";
                fMoney=1068f;
                master_check2.setChecked(false);
                master_check3.setChecked(false);
                master_check4.setChecked(false);
                master_check1.setChecked(false);
                break;
            case R.id.alipay_master:
                masterPay="alipay";
                getMasterPay();
                break;
            case R.id.wxpay_master:
                masterPay="weixin";
                getMasterPay();
                break;
            default:
                break;
        }

    }

    private void getMasterPay() {
        peoplePhone=people_phone_edt.getText().toString();
        String ok = add_data.isChinaPhoneLegal(peoplePhone,this);
        if (!master_check1.isChecked()&&!master_check2.isChecked()&&!master_check3.isChecked()&&!master_check4.isChecked()&&!master_check5.isChecked()){
            Toast.makeText(this, "请选择大师", Toast.LENGTH_SHORT).show();
        }else{
            if (!ok.equals("")){
                //订单号
                masterOrderno = "getName"+System.currentTimeMillis();
                url = "http://project4.itobike.com/cgi/pay.ashx/pay.do?orderamt=" +masterMoney + "&orderno="+ masterOrderno + "&paytype="+masterPay;
                LBStat.pay(masterPay, masterOrderno, false, payType, fMoney, "qmmaster");

//                Intent intent= new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                Uri content_url = Uri.parse(url);
//                intent.setData(content_url);
                Intent intent = new Intent(H5Activity.this, PayPageActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("bundle", bundle);
                bundle.putString(Constant.url, url);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(masterOrderno!=null){
            payMasterWay();
        }
    }


    /**
     * 根据订单号查询支付状态，支付宝
     */
    private void payMasterWay() {
        String url = "http://project4.itobike.com/cgi/pay.ashx/order/info.json?orderno=" + masterOrderno+"&paytype="+masterPay;
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
                        saveResultMaster(response);
                    }
                });
    }


    /**
     * 支付宝支付成功或失败
     *
     * @param response 返回状态码
     */
    private void saveResultMaster(String response) {
        Gson gson = new Gson();
        System.out.println(response);
        FtResultBean bean = gson.fromJson(response, FtResultBean.class);
        boolean isSuccess = "A001".equals(bean.getResponseCode());//支付是否成功
        if (isSuccess) {//成功//错题集
            LBStat.pay(masterPay, masterOrderno, true, payType, fMoney, "qmmaster");
            setMasterPhone();
            masterOrderno = null;
        } else {
            Toast.makeText(this, "预约大师失败，请重新支付预约", Toast.LENGTH_SHORT).show();
            masterOrderno=null;
        }
    }
    private void setMasterPhone() {
        String url = "http://api.webqm.itobike.com/cgi/api.ashx/GetDaShiOrder?orderno="+order+"&Phone="+peoplePhone+"&orderamt="+masterMoney;
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
                        if (response.equals("ok")){
                            Toast.makeText(H5Activity.this, "预约大师成功，请等待大师联系", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
