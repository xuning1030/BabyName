package babyname.babyname.Utils;

import android.app.Activity;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Administrator on 2017/4/7.
 */

public class Add_data {
    private String ok = "";

    public String isChinaPhoneLegal(String str, Activity activity) throws PatternSyntaxException//正则表达式，登录时验证电话号码
    {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);

        if (m.matches()) {
            StringBuilder sb = new StringBuilder(str), ss = sb.replace(3, 7, "****");
            ok = ss.toString();
        } else {
            Toast.makeText(activity, "您的手机号有误!", Toast.LENGTH_SHORT).show();
            ok = "";
        }

        return ok;
    }


}
