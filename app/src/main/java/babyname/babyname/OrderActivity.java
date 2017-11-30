package babyname.babyname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lbsw.stat.LBStat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.List;

import babyname.babyname.Utils.Add_data;
import babyname.babyname.Utils.Constant;
import babyname.babyname.Utils.DatabaseUtils;
import babyname.babyname.bean.FtResultBean;
import babyname.babyname.bean.GetHistoryOrderBean;
import babyname.babyname.fragment.NameDialogFramentpay;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/5.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView home_order_title,order_txt_nowyear, order_txt_nowyear1, order_txt_oldyear, order_txt_oldyear1, order_txt_name, order_txt_name1, order_txt_sex, order_txt_sex1, order_txt_num;
    private TextView txt_lineation;//原价（）
    private LinearLayout wxpay_layout,alipay_layout;
    private String nowyear, oldyear, name, sex, num;
    private String messages ;
    private String orderno;
    private ImageView doOnclick1,doOnclick2,doOnclick3;
    private String payType = "pay";
    private float fMoney = 29.9f;
    private WebView wv;
    private String url;
    private String pay_type;

    private String order1 = null, status = null, paych = null;
    private Add_data add_data;

    private CheckBox master_check1,master_check2,master_check3,master_check4,master_check5;
    private EditText people_phone_edt;
    private LinearLayout alipay_master,wxpay_master;
    private String masterOrderno,masterMoney,masterPay,peoplePhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_main);
        wv = new WebView(this);
        wv.getSettings().setJavaScriptEnabled(true);
        add_data=new Add_data();
        OderData();
        init();

    }

    private void OderData() {
        List<GetHistoryOrderBean> list = DatabaseUtils.getHelper().queryAll(GetHistoryOrderBean.class);
        nowyear = list.get(list.size()-1).getLunar().toString();//
        sex = list.get(list.size()-1).getMan().toString();//性别
        num = list.get(list.size()-1).getOrderno().toString();//订单号
        name = list.get(list.size()-1).getSurname().toString();//
        oldyear = list.get(list.size()-1).getSolar().toString();
//        orderno=num;


    }

    private void init() {
        setTitle("宝宝取名");
        doOnclick1= (ImageView) findViewById(R.id.doOnclick1);
        doOnclick2= (ImageView) findViewById(R.id.doOnclick2);
        doOnclick3= (ImageView) findViewById(R.id.doOnclick3);
        order_txt_name1 = (TextView) findViewById(R.id.order_txt_name1);
        order_txt_sex1 = (TextView) findViewById(R.id.order_txt_sex1);
        order_txt_nowyear1 = (TextView) findViewById(R.id.order_txt_nowyear1);
        order_txt_nowyear = (TextView) findViewById(R.id.order_txt_nowyear);
        order_txt_oldyear1 = (TextView) findViewById(R.id.order_txt_oldyear1);
        order_txt_oldyear = (TextView) findViewById(R.id.order_txt_oldyear);
        order_txt_name = (TextView) findViewById(R.id.order_txt_name);
        order_txt_sex = (TextView) findViewById(R.id.order_txt_sex);
        order_txt_num = (TextView) findViewById(R.id.order_txt_num);
        txt_lineation = (TextView) findViewById(R.id.txt_lineation);
        home_order_title = (TextView) findViewById(R.id.home_order_title);
        txt_lineation.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        wxpay_layout = (LinearLayout) findViewById(R.id.wxpay_layout);
        wxpay_layout.setOnClickListener(this);
        alipay_layout= (LinearLayout) this.findViewById(R.id.alipay_layout);
        alipay_layout.setOnClickListener(this);
        doOnclick1.setOnClickListener(this);
        doOnclick2.setOnClickListener(this);
        doOnclick3.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("test_user", Activity.MODE_PRIVATE);
        int be=sharedPreferences.getInt("num_pep",4852641);
        messages = "已为<font color='red'>"+be+"</font>人进行了宝宝取名，姓名伴随人一生，所以影响人一生。命好、运好再加上一个吉祥好名，会让您将来功成名就，事事顺心。名字不仅是一个人的符号，更是一个人形象、品味的重要标志。古人云：赐子千金，不如教子一艺；教子一艺，不如赐子好名；不怕生错命，就怕起错名。";
        Spanned msg= Html.fromHtml(messages);
        home_order_title.setText(msg);

        order_txt_nowyear1.setText(nowyear);
        order_txt_nowyear.setText(nowyear);
        order_txt_oldyear1.setText(oldyear);
        order_txt_oldyear.setText(oldyear);
        order_txt_name.setText(name);
        order_txt_name1.setText(name);
        order_txt_sex.setText(sex);
        order_txt_sex1.setText(sex);
        order_txt_num.setText(num);

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

    //支付弹窗
    public void doOnclick() {

        NameDialogFramentpay dialogFragment = new NameDialogFramentpay();

        dialogFragment.setAliPayActivityListener(new NameDialogFramentpay.AlipayActivityListener() {
            @Override
            public void AlipayActivityListener() {
                pay_type="alipay";
                getAliUrl(pay_type);

            }
        });

        dialogFragment.setWxPayActivityListener(new NameDialogFramentpay.WxpayActivityListener() {
            @Override
            public void WxpayActivityListener() {
                pay_type="weixin";
                getAliUrl(pay_type);
            }
        });

        dialogFragment.show(getFragmentManager(), "pay");

    }

    /**
     * 获取支付宝订单号
     */
    private void getAliUrl(String pay_type ) {
        //订单号
        fMoney = 29.9f;
        orderno = "getName"+System.currentTimeMillis();
        url = "http://project4.itobike.com/cgi/pay.ashx/pay.do?orderamt=" + Constant.pay2990 + "&orderno="+ orderno + "&paytype="+pay_type;
        LBStat.pay(pay_type, orderno, false, payType, fMoney, "xxb");

        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
//        startActivity(Intent.createChooser(intent,getString(R.string.about_cherry_choice_browser)));
//        Intent intent = new Intent(OrderActivity.this, PayPageActivity.class);
//        Bundle bundle = new Bundle();
//        intent.putExtra("bundle", bundle);
//        bundle.putString(Constant.url, url);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.wxpay_layout:
                pay_type="weixin";
                getAliUrl(pay_type);
                break;
            case R.id.alipay_layout:
                pay_type="alipay";
                getAliUrl(pay_type);
                break;
            case R.id.doOnclick1:
                doOnclick();
                break;
            case R.id.doOnclick2:
                doOnclick();
                break;
            case R.id.doOnclick3:
                doOnclick();
                break;
            case R.id.master_check1:
              masterMoney="18800";
                fMoney=188f;
//                masterMoney="1";
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
//        Intent intent = new Intent(OrderActivity.this, PayPageActivity.class);
//        Bundle bundle = new Bundle();
//        intent.putExtra("bundle", bundle);
//        bundle.putString(Constant.url, url);
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
        startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (orderno != null) {
            payWay();
        }
        if(masterOrderno!=null){
            payMasterWay();
        }
    }
//    LBStat.pay("wx", Wxorderno, true, payType, fMoney, "xxb");


    /**
     * 根据订单号查询支付状态，支付宝
     */
    private void payWay() {
        String url = "http://project4.itobike.com/cgi/pay.ashx/order/info.json?orderno=" + orderno+"&paytype="+pay_type;
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
            Intent intent = new Intent();
            intent.setClass(this, ResultActivity.class);
            status="TRADE_SUCCESS";
            intent.putExtra("status",status);
            intent.putExtra("paych",pay_type);
            intent.putExtra("order",num);
            startActivity(intent);
            orderno = null;
        } else {
            Intent intent= new Intent();
            intent.setClass(this,PayResultActivity.class);
            intent.putExtra("num",num);
            intent.putExtra("orderno",orderno);
            intent.putExtra("pay_type",pay_type);
            startActivity(intent);
            orderno=null;
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
        String url = "http://api.webqm.itobike.com/cgi/api.ashx/GetDaShiOrder?orderno="+num+"&Phone="+peoplePhone+"&orderamt="+masterMoney;
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
                            Toast.makeText(OrderActivity.this, "预约大师成功，请等待大师联系", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
