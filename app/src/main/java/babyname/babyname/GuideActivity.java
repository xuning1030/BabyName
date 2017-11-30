package babyname.babyname;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.lbsw.stat.LBStat;

/**
 * Created by  Rowi on 2017/4/13.
 */

public class GuideActivity extends BaseActivity {
    public static String sMallId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        LBStat.click(1000);
        GoHomeOrGoGuide();

    }


    private void GoHomeOrGoGuide() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Intent =new Intent(getApplication(),HomeActivity.class);
                startActivity(Intent);
                finish();
            }
        }, 1500);
    }

}
