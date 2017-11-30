package babyname.babyname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lbsw.stat.LBStat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import babyname.babyname.Utils.Constant;
import babyname.babyname.adapter.MyAdapter;
import babyname.babyname.bean.FtResultBean;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/7.
 */

public class SetMoneyActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {

    private TextView money1, money2, money3;
    private String  pay_money="880";
    private EditText je;
    private LinearLayout zfb,wx;
    private String orderno;
    private String payMothed;
    private String payType = "reward";
    private float fMoney;
    private String Wxorderno;
    private Timer timer;
    private ListView listView2;
    private List<String> list;
    private int index;
    private MyAdapter adapter;
    private int firstVisible;
    private int visibleItem;
    private int totalItem;
    private String first_name;
    private String sex;
    private String [] strsex=new String[]{"女士","先生"};
    private int second;
    private TextView set_money_tv;

    private  String   url;

    private String[] Str = new String[] {
            "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "楮", "卫", "蒋", "沈", "韩", "杨",
            "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜",
            "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
            "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐",
            "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常",
            "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄",
            "和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧",
            "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "梁",
            "杜", "阮", "蓝", "闽", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭",
            "梅", "盛", "林", "刁", "锺", "徐", "丘", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍",
            "虞", "万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗",
            "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚",
            "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊", "於", "惠", "甄", "麹", "家", "封",
            "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓",
            "牧", "隗", "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫",
            "宁", "仇", "栾", "暴", "甘", "斜", "厉", "戎", "祖", "武", "符", "刘", "景", "詹", "束", "龙",
            "叶", "幸", "司", "韶", "郜", "黎", "蓟", "薄", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂",
            "索", "咸", "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双",
            "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍",
            "郤", "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "郏", "浦", "尚", "农",
            "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾", "鱼", "容",
            "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘",
            "匡", "国", "文", "寇", "广", "禄", "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆",
            "师", "巩", "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空",
            "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红",
            "游", "竺", "权", "逑", "盖", "益", "桓", "公", "仉", "督", "晋", "楚", "阎", "法", "汝", "鄢",
            "涂", "钦", "岳", "帅", "缑", "亢", "况", "后", "有", "琴", "归", "海", "墨", "哈", "谯", "笪",
            "年", "爱", "阳", "佟", "商", "牟", "佘", "佴", "伯", "赏"
    };

    private int random ;
    private int intSex;
    private int be;
    private String pay_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_money_main);

        setTitle("打赏");

        findViewAll();


    }

    private void findViewAll() {
        SharedPreferences sharedPreferences = getSharedPreferences("test_user", Activity.MODE_PRIVATE);
        be=sharedPreferences.getInt("test_users",10);
        be=be-401110;
        money1 = (TextView) this.findViewById(R.id.money1);
        money2 = (TextView) this.findViewById(R.id.money2);
        money3 = (TextView) this.findViewById(R.id.money3);
        set_money_tv= (TextView) this.findViewById(R.id.set_money_tv);
        je= (EditText) this.findViewById(R.id.je);
        money1.setOnClickListener(this);
        money2.setOnClickListener(this);
        money3.setOnClickListener(this);
        zfb= (LinearLayout) this.findViewById(R.id.zfb);
        zfb.setOnClickListener(this);
        wx= (LinearLayout) this.findViewById(R.id.wx);
        wx.setOnClickListener(this);
        listView2= (ListView) this.findViewById(R.id.lv2);
        list = getList();
        adapter = new MyAdapter(this, list);
        listView2.setAdapter(adapter);
        listView2.setOnScrollListener(this);
        listView2.setSelection(list.size());
        set_money_tv.setText(be+"");
        timer();

    }


    /**
     * 获取数据
     *
     * @return
     */
    public List<String> getList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            second=(int)(10+Math.random()*(59-10+1));
            random = (int) ( Math.random () * 442 );
            intSex=(int) ( Math.random () * 2 );
            first_name=Str[random];
            sex=strsex[intSex];

            list.add(second+"秒前    "+ "2017***" + String.valueOf(i+1) + String.valueOf(i) + String.valueOf(i + 2)  + "      "+first_name+sex+"打赏了"+String.valueOf(i + 8)+"元");
        }
        return list;
    }

    private void timer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        index += 1;
                        if (index >= listView2.getCount()) {
                            index = 0;

                        }
                        listView2.smoothScrollToPosition(index);
                    }
                });
            }
        }, 0, 500);
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
            listView2.setSelection(list.size() + 2);
        } else if (firstVisible + visibleItem > adapter.getCount() - 2) {//到底部添加数据
            listView2.setSelection(firstVisible - list.size());
        }
//
//            }
//        };
//        timer.schedule(task, 100, firstVisible);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.money1:
                pay_money="880";
                money1.setBackgroundResource(R.drawable.check_yuanjianbiao);
                money2.setBackgroundResource(R.drawable.yuanjiaobian);
                money3.setBackgroundResource(R.drawable.yuanjiaobian);
                break;
            case R.id.money2:
                pay_money="1880";
                money2.setBackgroundResource(R.drawable.check_yuanjianbiao);
                money1.setBackgroundResource(R.drawable.yuanjiaobian);
                money3.setBackgroundResource(R.drawable.yuanjiaobian);
                break;
            case R.id.money3:
                pay_money="18880";
                money3.setBackgroundResource(R.drawable.check_yuanjianbiao);
                money2.setBackgroundResource(R.drawable.yuanjiaobian);
                money1.setBackgroundResource(R.drawable.yuanjiaobian);
                break;
            case R.id.zfb:
                pay_type="alipay";
                url = "http://project4.itobike.com/cgi/pay.ashx/pay.do?orderamt=";
                if (!"".equals(je.getText().toString())) {
                    getAliUrl(je.getText().toString(),url);
                } else {
                    getAliUrl(pay_money,url);
                }
                break;
            case R.id.wx:
                pay_type="weixin";
                url="http://project4.itobike.com/cgi/pay.ashx/pay.do?orderamt=";
                if (!"".equals(je.getText().toString())) {
                    getAliUrl(je.getText().toString(),url);
                } else {
                    getAliUrl(pay_money,url);
                }
                break;
        }

    }

    /**
     * 获取支付宝订单号
     * @param money
     */
    private void getAliUrl(String money,String url) {
        //订单号
        orderno = System.currentTimeMillis()+"";
//        payMothed = "alipay";
        fMoney = Float.parseFloat(money)/100;
        LBStat.pay(pay_type, orderno, false, payType, fMoney, "xxb");
        System.out.println("？？？？？？？？？？"+money);
//        String url = http://api.webqm.itobike.com/cgi/pay.ashx/dian5pay.pay_dashang?orderamt=";
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url+money+"&orderno="+orderno+"&paytype="+pay_type);
        intent.setData(content_url);
//        Intent intent = new Intent(SetMoneyActivity.this, PayPageActivity.class);
//        Bundle bundle = new Bundle();
//        intent.putExtra("bundle", bundle);
//        bundle.putString(Constant.url, url+money+"&orderno="+orderno+"&paytype="+pay_type);
        startActivity(intent);
        System.out.println(url+money+"&orderno="+orderno);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (orderno != null) {
//            if ("weixin".equals(payMothed)) {//微信
//                WxpayWay();
//            }
//            if ("alipay".equals(payMothed)) {//支付宝
            payWay();//判断支付状态

//            }
        }
    }


    /**
     * 根据订单号查询支付状态，支付宝
     */
    private void payWay() {
        String url = "http://project4.itobike.com/cgi/pay.ashx/order/info.json?orderno=" + orderno+"&paytype="+pay_type;
        System.out.println(url);

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
            System.out.println(",><><><><>==成功");
//            ToastUtil.showToast(OrderActivity.this, "支付成功!");
            orderno = null;
            LBStat.click(1068);
        } else {
            //ToastUtil.showToast(getActivity(), "支付失败!");
        }
    }
}
