package babyname.babyname;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lbsw.stat.LBStat;

import java.util.HashMap;
import java.util.Map;

import babyname.babyname.Utils.DatabaseUtils;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyApplication extends Application {

    // 应用实例
    private static MyApplication instance;
    private Map<String,Object> pareMap;
    private String channel;//渠道号

    public static MyApplication getInstance() {
        return instance;
    }

    public Map<String, Object> getPareMap() {
        if (null == pareMap){
            pareMap = new HashMap<>();
        }
        return pareMap;
    }

    public void setPareMap(Map<String, Object> pareMap){
        this.pareMap = pareMap;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //创建数据库
        DatabaseUtils.initHelper(this,"babyquming.db");

        //支付统计
        CountPay();

    }

    private void CountPay() {
        String from = getFrom();//渠道号
        //判空
        if (from == null || "".equals(from)) {
            from = "test";
        }

        Log.v("Application", "from" + from);
        //统计初始化
        LBStat.init(MyApplication.this, "getname", "babyname", from, "011.mxitie.com");
        LBStat.start();
        LBStat.active();
    }

    /**
     * 获取渠道号
     * @return 渠道
     */
    public String getFrom() {

        try {
            ApplicationInfo info = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                Object metaDatperfer = info.metaData.get("CHANNEL");
                if (metaDatperfer != null) {
                    channel = metaDatperfer.toString();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }


}
