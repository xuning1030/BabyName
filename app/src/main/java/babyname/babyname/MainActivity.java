package babyname.babyname;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.example.liangmutian.mypicker.TimePickerDialog;
import com.lbsw.stat.LBStat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import babyname.babyname.Utils.Constant;
import babyname.babyname.Utils.DatabaseUtils;
import babyname.babyname.Utils.SPUtil;
import babyname.babyname.Utils.timeCommon;
import babyname.babyname.adapter.MyAdapter;
import babyname.babyname.bean.GetHistoryOrderBean;
import babyname.babyname.widget.LimitEditText;


public class MainActivity extends Activity implements View.OnClickListener, AbsListView.OnScrollListener {

    private TextView home_question1, home_question2, home_question3, home_question4,
            home_question5, home_question6, home_question7, home_question8,
            home_question_answer1, home_question_answer2, home_question_answer3, home_question_answer4, home_question_answer5, home_question_answer6, home_question_answer7, home_question_answer8;
    private ScrollView home_scro;
    private LinearLayout home_btn_line, home_banner2_line, home_born_data_line, home_born_hour_line;
    private CheckBox home_check_nan, home_check_nv, home_dan_ck, home_shuang_ck, home_dingzi_ck;
    private Button home_check1, home_check2;
    private ImageView home_banner2_Img;
    private String Str_sex = "男";
    private String numzi="1";
    private ListView listView;
    private List<String> list;
    private MyAdapter adapter;
    private TimerTask time;
    private ImageView home_call_phone, home_qq_phone;
    private TextView home_born_data_tv, home_born_hour_tv;
    private Dialog dateDialog, timeDialog;
    private LinearLayout home_line_question1, home_line_question2, home_line_question3, home_line_question4, home_line_question5,
            home_line_question6, home_line_question7, home_line_question8;
    private Button home_btn_quming, home_bottom_btn, home_order_bt2, home_order_bt1;
    private JSONObject jo, jo2;
    private Timer timer;
    private int firstVisible;
    private int visibleItem;
    private int totalItem;
    private int index;
    private LimitEditText editText,home_xuanzi_edt;
    private View view1, view2;
    private String surname, brithday, brithHour;
    private TextView home_order_tv1, home_order_tv2, home_title;
    private String device = null;
    private GetHistoryOrderBean getHistoryOrderBean;
    private EditText home_order_edt1, home_order_edt2;
    private String order;
    private String order1 = null, status = null, paych = null;
    protected ProgressDialog mProgressDialog;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private int year, month, date;
    private String num;
    private String isPay = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        okhttpPay();
        findViewAll();
        mTime();
    }

    public void findViewAll() {
        home_scro = (ScrollView) this.findViewById(R.id.home_scro);
        home_btn_line = (LinearLayout) this.findViewById(R.id.home_btn_line);
        home_check1 = (Button) this.findViewById(R.id.home_check1);
        home_check1.setOnClickListener(this);
        home_btn_line.setOnClickListener(this);
        home_check2 = (Button) this.findViewById(R.id.home_check2);
        home_check2.setOnClickListener(this);
        home_check1.setTextColor(getResources().getColor(R.color._ffffff));
        home_question1 = (TextView) this.findViewById(R.id.home_question1);
        home_question2 = (TextView) this.findViewById(R.id.home_question2);
        home_question3 = (TextView) this.findViewById(R.id.home_question3);
        home_question4 = (TextView) this.findViewById(R.id.home_question4);
        home_question5 = (TextView) this.findViewById(R.id.home_question5);
        home_question6 = (TextView) this.findViewById(R.id.home_question6);
        home_question7 = (TextView) this.findViewById(R.id.home_question7);
        home_question8 = (TextView) this.findViewById(R.id.home_question8);
        home_line_question1 = (LinearLayout) this.findViewById(R.id.home_line_question1);
        home_line_question2 = (LinearLayout) this.findViewById(R.id.home_line_question2);
        home_line_question3 = (LinearLayout) this.findViewById(R.id.home_line_question3);
        home_line_question4 = (LinearLayout) this.findViewById(R.id.home_line_question4);
        home_line_question5 = (LinearLayout) this.findViewById(R.id.home_line_question5);
        home_line_question6 = (LinearLayout) this.findViewById(R.id.home_line_question6);
        home_line_question7 = (LinearLayout) this.findViewById(R.id.home_line_question7);
        home_line_question8 = (LinearLayout) this.findViewById(R.id.home_line_question8);
        home_xuanzi_edt= (LimitEditText) this.findViewById(R.id.home_xuanzi_edt);
        home_order_edt1 = (EditText) findViewById(R.id.home_order_edt1);
        home_order_edt2 = (EditText) findViewById(R.id.home_order_edt2);
        home_born_data_line = (LinearLayout) this.findViewById(R.id.home_born_data_line);
        home_born_hour_line = (LinearLayout) this.findViewById(R.id.home_born_hour_line);
        home_call_phone = (ImageView) this.findViewById(R.id.home_call_phone);
        home_call_phone.setOnClickListener(this);
        home_qq_phone = (ImageView) this.findViewById(R.id.home_qq_phone);
        home_qq_phone.setOnClickListener(this);

        home_question1.setOnClickListener(this);
        home_question2.setOnClickListener(this);
        home_question3.setOnClickListener(this);
        home_question4.setOnClickListener(this);
        home_question5.setOnClickListener(this);
        home_question6.setOnClickListener(this);
        home_question7.setOnClickListener(this);
        home_question8.setOnClickListener(this);
        home_born_hour_line.setOnClickListener(this);
        home_born_data_line.setOnClickListener(this);
        home_banner2_line = (LinearLayout) this.findViewById(R.id.home_banner2_line);
        home_banner2_Img = (ImageView) this.findViewById(R.id.home_banner2_Img);
        home_check_nan = (CheckBox) this.findViewById(R.id.home_check_nan);
        home_check_nv = (CheckBox) this.findViewById(R.id.home_check_nv);
        home_check_nan.setOnClickListener(this);
        home_check_nv.setOnClickListener(this);
        home_dan_ck = (CheckBox) this.findViewById(R.id.home_dan_ck);
        home_shuang_ck = (CheckBox) this.findViewById(R.id.home_shuang_ck);
        home_dingzi_ck = (CheckBox) this.findViewById(R.id.home_dingzi_ck);
        home_dan_ck.setOnClickListener(this);
        home_shuang_ck.setOnClickListener(this);
        home_dingzi_ck.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.lv);
        list = getList();
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setSelection(list.size());
        home_born_data_tv = (TextView) this.findViewById(R.id.home_born_data_tv);
        home_born_hour_tv = (TextView) this.findViewById(R.id.home_born_hour_tv);
        home_btn_quming = (Button) this.findViewById(R.id.home_btn_quming);
        home_btn_quming.setOnClickListener(this);
        home_bottom_btn = (Button) this.findViewById(R.id.home_bottom_btn);
        home_bottom_btn.setOnClickListener(this);
        view1 = (View) this.findViewById(R.id.view1);
        view2 = (View) this.findViewById(R.id.view2);
        timer();
        editText = (LimitEditText) this.findViewById(R.id.editText);
        home_order_tv1 = (TextView) this.findViewById(R.id.home_order_tv1);
        home_order_tv2 = (TextView) this.findViewById(R.id.home_order_tv2);
        home_order_tv1.setOnClickListener(this);
        home_order_tv2.setOnClickListener(this);
        String message = "<font color='#81694f'>温馨提示：</font>未付款订单只能通过订单号查询" + "\n" + ",如果订单号忘记，您可以点此<font color='red'>查看您的历史订单</font>。";
        Spanned msg = Html.fromHtml(message);
        home_order_tv1.setText(msg);
        home_order_tv2.setText(msg);
        SharedPreferences sharedPreferences = getSharedPreferences("test_user", Activity.MODE_PRIVATE);
        int be = sharedPreferences.getInt("num_pep", 482641);
        device = getUniquePsuedoID();
        SPUtil.put(getApplication(), "device", device);
        System.out.println("??????" + device);
        int a = (int) (10 + Math.random() * (20));
        int adc = be + a;
        String messages = "已为<font color='red'>" + adc + "</font>人进行了宝宝取名，姓名伴随人一生，所以影响人一生。命好、运好再加上一个吉祥好名，会让您将来功成名就，事事顺心。名字不仅是一个人的符号，更是一个人形象、品味的重要标志。古人云：赐子千金，不如教子一艺；教子一艺，不如赐子好名；不怕生错命，就怕起错名。";
        SPUtil.put(this, "num_pep", adc);

//        String  be1=sharedPreferences.getString("device","");
        home_title = (TextView) this.findViewById(R.id.home_title);
        Spanned msgs = Html.fromHtml(messages);
        home_title.setText(msgs);
//        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
//        device = TelephonyMgr.getDeviceId();
        home_order_bt1 = (Button) findViewById(R.id.home_order_bt1);
        home_order_bt1.setOnClickListener(this);
        home_order_bt2 = (Button) findViewById(R.id.home_order_bt2);
        home_order_bt2.setOnClickListener(this);

    }

    protected void showTipMsg(String Msg) {
        mProgressDialog.setMessage(Msg);
        mProgressDialog.show();
    }


    /**
     * 获取数据
     *
     * @return
     */
    public List<String> getList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            list.add("2017***" + String.valueOf(i + 1) + String.valueOf(i) + String.valueOf(i + 2) + "      " + timeCommon.getCurrentTimeString("yyyy-MM-dd") + "      " + "29.9元");
        }
        return list;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 设置滚动监听，当滚动到第二个时，跳到地list.size()+2个，滚动到倒数第二个时，跳到中间第二个，setSelection时，
     * 由于listView滚动并未停止，所以setSelection后会继续滚动，不会出现突然停止的现象
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        firstVisible = firstVisibleItem;
        visibleItem = visibleItemCount;
        totalItem = totalItemCount;
//        timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
        /**到顶部添加数据关键<span style="font-size:14px;">代码</span>*/
        if (firstVisible <= 2) {
            listView.setSelection(list.size() + 2);
        } else if (firstVisible + visibleItem > adapter.getCount() - 2) {//到底部添加数据
            listView.setSelection(firstVisible - list.size());
        }
//
//            }
//        };
//        timer.schedule(task, 100, firstVisible);
    }

    private void timer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        index += 1;
                        if (index >= listView.getCount()) {
                            index = 0;

                        }
                        listView.smoothScrollToPosition(index);
                    }
                });
            }
        }, 0, 500);

//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                int index =4;
//                if(index >= listView.getCount()) {
//                    index = 0;
//                }
//                listView.smoothScrollToPosition(index);
//
//            }
//
//        };  timer.schedule(task, 100);


    }


    @Override
    protected void onDestroy() {
        mProgressDialog.dismiss();
        super.onDestroy();
        timer.cancel();

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.home_question1:
                home_line_question1.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question2:
                home_line_question2.setVisibility(View.VISIBLE);
                home_line_question1.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question3:
                home_line_question3.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question1.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question4:
                home_line_question4.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question1.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question5:
                home_line_question5.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question1.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question6:
                home_line_question6.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question1.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question7:
                home_line_question7.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question1.setVisibility(View.GONE);
                home_line_question8.setVisibility(View.GONE);

                break;
            case R.id.home_question8:
                home_line_question8.setVisibility(View.VISIBLE);
                home_line_question2.setVisibility(View.GONE);
                home_line_question3.setVisibility(View.GONE);
                home_line_question4.setVisibility(View.GONE);
                home_line_question5.setVisibility(View.GONE);
                home_line_question6.setVisibility(View.GONE);
                home_line_question7.setVisibility(View.GONE);
                home_line_question1.setVisibility(View.GONE);

                break;
            case R.id.home_check1:
                home_check2.setTextColor(getResources().getColor(R.color._beb4aa));
                home_check1.setTextColor(getResources().getColor(R.color._ffffff));
                home_banner2_line.setVisibility(View.GONE);
                home_banner2_Img.setVisibility(View.VISIBLE);
                view1.setBackgroundResource(R.color._ffffff);
                view2.setBackgroundResource(R.color._81694f);
                break;
            case R.id.home_check2:
                home_check1.setTextColor(getResources().getColor(R.color._beb4aa));
                home_check2.setTextColor(getResources().getColor(R.color._ffffff));
                home_banner2_Img.setVisibility(View.GONE);
                home_banner2_line.setVisibility(View.VISIBLE);
                view2.setBackgroundResource(R.color._ffffff);
                view1.setBackgroundResource(R.color._81694f);
                break;
            case R.id.home_check_nan:
                home_check_nv.setChecked(false);
                Str_sex = "男";
                break;
            case R.id.home_check_nv:
                home_check_nan.setChecked(false);
                Str_sex = "女";
                break;
            case R.id.home_dingzi_ck:
                home_shuang_ck.setChecked(true);
                home_dan_ck.setChecked(false);
                numzi="2";
                LBStat.click(1076);
                break;
            case R.id.home_dan_ck:
                home_shuang_ck.setChecked(false);
                home_dingzi_ck.setChecked(false);
                numzi="1";
                LBStat.click(1074);
                break;
            case R.id.home_shuang_ck:
                home_dan_ck.setChecked(false);
                numzi="2";
                LBStat.click(1075);
            case R.id.home_bottom_btn:
//                if (!surname.equals("") && !brithday.equals("") && !brithHour.equals("")) {
//                    okhttpPost();
//                }else{
//                    home_scro.scrollTo(0,0);
//                }
                okhttpPost();
//                intent.setClass(this, OrderActivity.class);
//                startActivity(intent);

                break;

            case R.id.home_btn_quming:
                okhttpPost();
                break;
            case R.id.home_born_data_line:
                showDateDialog(DateUtil.getDateForString(year + "-" + month + "-" + date));
                break;
            case R.id.home_born_hour_line:
                showTimePick();
                break;
            case R.id.home_order_tv1:
                intent.setClass(this, HistoryOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.home_order_tv2:
                intent.setClass(this, HistoryOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.home_order_bt1:
                order = home_order_edt1.getText().toString();
                System.out.println("++++++++" + order);
                if (!"".equals(home_order_edt1.getText().toString())) {
                    paych = "null";
                    status = "null";
                    okhttp2();
                } else {
                    Toast.makeText(MainActivity.this, "请输入订单号", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(MainActivity.this, "功能开发中，敬请期待", Toast.LENGTH_SHORT).show();
                break;

            case R.id.home_order_bt2:
                order = home_order_edt2.getText().toString();
                System.out.println("++++++++" + order);
                if (!"".equals(home_order_edt2.getText().toString())) {
                    paych = "null";
                    status = "null";
                    okhttp2();
                } else {
                    Toast.makeText(MainActivity.this, "请输入订单号", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.home_call_phone:
                PackageManager pm = getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED ==
                        pm.checkPermission("android.permission.CALL_PHONE", "packageName"));
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17196338071"));
                startActivity(intent1);
                break;
            case R.id.home_qq_phone:
                if (!isQQClientAvailable(this)) {
                    Toast.makeText(MainActivity.this, "未安装QQ，请拨打电话", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=3282730877";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

                break;

            default:
                break;
        }

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


    private void okhttp2() {

        String url = "http://api.webqm.itobike.com/cgi/api.ashx/getOrderinfo?orderno=" + order;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                Toast.makeText(MainActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("Tag", response);
                System.out.println(response + "<<<<");
                try {
                    jo = new JSONObject(response);
                    jo2 = jo.getJSONObject("result_content");
                    order1 = jo2.getString("orderno");
                    status = jo2.getString("status");
                    paych = jo2.getString("paych");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAfter(int id) {
                if (paych.equals("null")) {
                    Toast.makeText(MainActivity.this, "不存在该订单，请重新下单", Toast.LENGTH_SHORT).show();
                }
                if (paych != null && status.equals("TRADE_SUCCESS")) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ResultActivity.class);
                    intent.putExtra("status", status);
                    intent.putExtra("paych", paych);
                    intent.putExtra("order", order1);
                    startActivity(intent);
                }
            }
        });
    }

    private void okhttpPost() {
        surname = editText.getText().toString();
        brithday = home_born_data_tv.getText().toString();
        brithHour = home_born_hour_tv.getText().toString();
        if(home_dan_ck.isChecked()){
            home_xuanzi_edt.setText("");
        }
        if (surname.equals("")) {
            Toast.makeText(MainActivity.this, "请填写姓氏", Toast.LENGTH_SHORT).show();
        } else {
            if (brithday.equals("")) {
                Toast.makeText(MainActivity.this, "请填写出生日期", Toast.LENGTH_SHORT).show();
            } else {
                if (brithHour.equals("")) {
                    Toast.makeText(MainActivity.this, "请填写出生时辰", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (!home_check_nan.isChecked() && !home_check_nv.isChecked()) {
            Toast.makeText(MainActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
        } else {
            if (home_dingzi_ck.isChecked()){
                if(!home_xuanzi_edt.getText().toString().equals("")){
            if (!surname.equals("") && !brithday.equals("") && !brithHour.equals("")) {
                showTipMsg("加载中.....");
                String url = "http://api.webqm.itobike.com/cgi/api.ashx/getQuMingInfo?surname=" + surname + "&man=" + Str_sex + "&brithday=" + brithday + "&brithHour=" + brithHour +"&num=" +numzi+"&dzb="+home_xuanzi_edt.getText().toString()+  "&device=" + device;
                System.out.println(">>>>>>>>>>>>" + url);
                OkHttpUtils
                        .post()
                        .url(url)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {
                                mProgressDialog.dismiss();
//                                Toast.makeText(MainActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                mProgressDialog.dismiss();
                                Log.i("Tag", response);
                                System.out.println(response + "<<<<");
                                try {
                                    jo = new JSONObject(response);
                                    jo2 = jo.getJSONObject("result_content");
                                    String surname = jo2.getString("surname");
                                    String man = jo2.getString("man");
                                    String lunar = jo2.getString("lunar");
                                    String solar = jo2.getString("solar");
                                    String orderno = jo2.getString("orderno");
                                    num = orderno;
                                    getHistoryOrderBean = new GetHistoryOrderBean(surname, man, lunar, solar, orderno);
                                    DatabaseUtils.getHelper().save(getHistoryOrderBean);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onAfter(int id) {
                                if (isPay.equals("1")) {
                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, ResultActivity.class);
                                    intent.putExtra("status", "TRADE_SUCCESS");
                                    intent.putExtra("paych", "weixin");
                                    intent.putExtra("order", num);
                                    intent.putExtra("history", "0");
                                    startActivity(intent);
                                } else {
                                    if (getHistoryOrderBean != null) {
                                        //魅族版本
                                        if (Constant.isMeizu) {
                                            Intent intent = new Intent();
                                            intent.setClass(MainActivity.this, ResultActivity.class);
                                            intent.putExtra("status", "TRADE_SUCCESS");
                                            intent.putExtra("paych", "weixin");
                                            intent.putExtra("order", num);
                                            intent.putExtra("history", "0");
                                            startActivity(intent);
                                        } else {
                                            //正常版本
                                            Intent intent1 = new Intent();
                                            intent1.setClass(MainActivity.this, OrderActivity.class);
                                            startActivity(intent1);
                                            LBStat.click(1067);
                                        }

                                    } else {
                                        Toast.makeText(MainActivity.this, "不存在该姓氏，请重新输入", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
            }
            }else{
                    Toast.makeText(MainActivity.this, "请输入要定的字", Toast.LENGTH_SHORT).show();
                }
            }else{
                home_xuanzi_edt.setText("");
                if (!surname.equals("") && !brithday.equals("") && !brithHour.equals("")) {
                    showTipMsg("加载中.....");
                    String url = "http://api.webqm.itobike.com/cgi/api.ashx/getQuMingInfo?surname=" + surname + "&man=" + Str_sex + "&brithday=" + brithday + "&brithHour=" + brithHour +"&num=" +numzi+"&dzb="+home_xuanzi_edt.getText().toString()+  "&device=" + device;
                    System.out.println(">>>>>>>>>>>>" + url);
                    OkHttpUtils
                            .post()
                            .url(url)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(okhttp3.Call call, Exception e, int id) {
                                    mProgressDialog.dismiss();
//                                Toast.makeText(MainActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    mProgressDialog.dismiss();
                                    Log.i("Tag", response);
                                    System.out.println(response + "<<<<");
                                    try {
                                        jo = new JSONObject(response);
                                        jo2 = jo.getJSONObject("result_content");
                                        String surname = jo2.getString("surname");
                                        String man = jo2.getString("man");
                                        String lunar = jo2.getString("lunar");
                                        String solar = jo2.getString("solar");
                                        String orderno = jo2.getString("orderno");
                                        num = orderno;
                                        getHistoryOrderBean = new GetHistoryOrderBean(surname, man, lunar, solar, orderno);
                                        DatabaseUtils.getHelper().save(getHistoryOrderBean);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onAfter(int id) {
                                    if (isPay.equals("1")) {
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this, ResultActivity.class);
                                        intent.putExtra("status", "TRADE_SUCCESS");
                                        intent.putExtra("paych", "weixin");
                                        intent.putExtra("order", num);
                                        intent.putExtra("history", "0");
                                        startActivity(intent);
                                    } else {
                                        if (getHistoryOrderBean != null) {
                                            //魅族版本
                                            if (Constant.isMeizu) {
                                                Intent intent = new Intent();
                                                intent.setClass(MainActivity.this, ResultActivity.class);
                                                intent.putExtra("status", "TRADE_SUCCESS");
                                                intent.putExtra("paych", "weixin");
                                                intent.putExtra("order", num);
                                                intent.putExtra("history", "0");
                                                startActivity(intent);
                                            } else {
                                                //正常版本
                                                Intent intent1 = new Intent();
                                                intent1.setClass(MainActivity.this, OrderActivity.class);
                                                startActivity(intent1);
                                                LBStat.click(1067);
                                            }

                                        } else {
                                            Toast.makeText(MainActivity.this, "不存在该姓氏，请重新输入", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            });
                }
            }
        }

    }


    /**
     * date选择
     *
     * @param date
     */
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new com.example.liangmutian.mypicker.DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                home_born_data_tv.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));

            }

            @Override
            public void onCancel() {

            }
        }).setSelectYear(date.get(0) - 1).setSelectMonth(date.get(1) - 1).setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    private void showTimePick() {
        if (timeDialog == null) {

            TimePickerDialog.Builder builder = new TimePickerDialog.Builder(this);
            timeDialog = builder.setOnTimeSelectedListener(new TimePickerDialog.OnTimeSelectedListener() {
                @Override
                public void onTimeSelected(int[] times) {
                    home_born_hour_tv.setText(times[0] + ":" + times[1]);

                }
            }).create();
        }
        timeDialog.show();

    }

    //时间
    private void mTime() {
//        times = System.currentTimeMillis();
//        final Calendar mCalendar = Calendar.getInstance();
//        mCalendar.setTimeInMillis(times);
//        int mHour = mCalendar.get(Calendar.HOUR);
//        int mMinuts = mCalendar.get(Calendar.MINUTE);
        Time t = new Time("GMT+8");  //加上Time
        t.setToNow(); // 取得系统时间。
        year = t.year;
        month = t.month + 1;
        date = t.monthDay;
        DateFormat df = new SimpleDateFormat("HH:mm");
        String s = df.format(new Date());
        home_born_hour_tv.setHint(s);
        home_born_data_tv.setHint(year + "-" + month + "-" + date);


    }


    public static String getUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }


    private void okhttpPay() {
        String AppName = "宝宝取名-起名大师1127";
        String AppChannel = "2001";
        String url = "http://api.majiang.itobike.com/cgi/api.ashx/Channel?AppName=" + AppName + "&AppChannel=" + AppChannel;
        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
//                        Log.i("Tag", response);
                        isPay = response;
                    }
                });


    }


}
