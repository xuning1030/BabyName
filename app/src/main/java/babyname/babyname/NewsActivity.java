package babyname.babyname;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/7/3.
 */

public class NewsActivity extends Activity{
    private WebView WVH5;
    private ProgressBar PBH5;
    private String url="http://webqm.dudutx.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_activity);

        WVH5 = (WebView) findViewById(R.id.WVH5);
        PBH5 = (ProgressBar) findViewById(R.id.PBH5);

        WVH5.loadUrl(url);
        WVH5.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //支持App内部JavaScript交互
        WVH5.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        WVH5.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WVH5.getSettings().setLoadWithOverviewMode(true);
        //设置可以支持缩放
        WVH5.getSettings().setSupportZoom(true);
        //扩大比例的缩放
        WVH5.getSettings().setUseWideViewPort(true);
        //设置是否出现缩放工具
        WVH5.getSettings().setBuiltInZoomControls(true);
    }
}
