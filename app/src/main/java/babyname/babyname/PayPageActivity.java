package babyname.babyname;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import babyname.babyname.Utils.Constant;
import babyname.babyname.Utils.ToastUtil;

public class PayPageActivity extends AppCompatActivity {

    private WebView mWv;//webView
    private String url;//支付url
    private String mBackUrl = "http://www.baidu.com";
    private String payMothed;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_page);
        init();
        readyPay();
    }

    /**
     * 拉起支付宝页面
     */
    private void readyPay() {

        mWv.loadUrl(url);

        mWv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                tv.setVisibility(View.GONE);
                System.out.println(url);
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
//                if (url.startsWith("intent://")) {
//                    url = url.replace("intent://", "weixin://");
//                    Intent intent = new Intent();
//                    intent.putExtra("translate_link_scene", 1);
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    startActivity(intent);
//                    finish();
//                    return true;
//                } else if (url.startsWith("http:") || url.startsWith("https:")) {
//                    mWv.loadUrl(url);
//                } else if (url.startsWith("alipays://") || url.startsWith("weixin://")) {
//                    try {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(url));
//                        startActivity(intent);
//                        finish();
//                    } catch (Exception e) {
//                        if (url.startsWith("weixin://")) {
//                            ToastUtil.showToast(PayPageActivity.this, "请安装微信");
//                        }
//                    }
//                }
//
//                try {
//                    Uri uri = Uri.parse(url);
//                    String show_url = uri.getQueryParameter("show_url");
//                    if(!TextUtils.isEmpty(show_url)) mBackUrl = show_url;
//                } catch (Exception e) {
//                }
//                //返回
//                if (url.startsWith(mBackUrl)) {
//                    finish();
//                }
//                //支付完成
//                if (url.contains("trade_status")) {
//                    finish();
//                }
//
                return true;
            }
        });
    }

    private void init() {
        mWv = (WebView) findViewById(R.id.wb);
        tv= (TextView) findViewById(R.id.tv);
        mWv.getSettings().setJavaScriptEnabled(true);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        url = bundle.getString(Constant.url);
    }
}
