package babyname.babyname;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/7/3.
 */

public class BaseActivity extends Activity {
    protected ProgressDialog mProgressDialog;
    protected final int ResultNo = 100001;//
    protected final int MoreResultNo = 100003;//
//    protected BaseBean baseBean;
//    protected BaseBean tempBean;
    protected Handler handler;
    protected Thread thread;
    private TextView titleName;
    private Button titleReturn;
//    protected String requestType = "1";// 请求数据类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
    }

    protected void setTitle(String title) {
        titleName = (TextView) findViewById(R.id.title_text);
        titleName.setText(title);
        titleReturn = (Button) findViewById(R.id.title_left_btn);
        titleReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    protected void showTipMsg(String Msg) {
        mProgressDialog.setMessage(Msg);
        mProgressDialog.show();
    }

    @Override
    protected void onDestroy() {
        mProgressDialog.dismiss();
        super.onDestroy();
    }

//    protected void initData(final String url, final int ResultOk, final int ResultNo) {
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                baseBean = new ServerInterface<BaseBean>(BaseBean.class).getServerDataWithGet(url);
//                if (baseBean == null) {
//                    baseBean = new BaseBean();
//                    DataBean dataBean = new DataBean();
//                    dataBean.setErrMsg("网络问题！请求失败");
//                    baseBean.setStatus("error");
//                    baseBean.setData(dataBean);
//                }
//                if (!baseBean.getStatus().equals("ok")) {
//                    if (baseBean.getData().getErrMsg().equals("用户ID错误")
//                            || baseBean.getData().getErrMsg().equals("验证出错")) {
//                        mProgressDialog.dismiss();
//                        Intent intent = new Intent(BaseActivity.this, Techi_LoginActivity.class);
//                        startActivityForResult(intent, Struts.base_login);
//                    } else {
//                        handler.sendEmptyMessage(ResultNo);
//                    }
//                } else {
//                    handler.sendEmptyMessage(ResultOk);
//                }
//            }
//        });
//        thread.start();
//    }
//
//    protected void initData(final String url, final List<NameValuePair> paramList, final int ResultOk,
//                            final int ResultNo) {
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                baseBean = new ServerInterface<BaseBean>(BaseBean.class).getServerDataWithPost(url, paramList);
//
//                if (Thread.interrupted()) {
//                    return;
//                }
//
//                if (baseBean == null) {
//                    baseBean = new BaseBean();
//                    DataBean dataBean = new DataBean();
//                    dataBean.setErrMsg("网络问题！请求失败");
//                    baseBean.setStatus("error");
//                    baseBean.setData(dataBean);
//                }
//                if (!baseBean.getStatus().equals("ok")) {
//                    // if (baseBean.getData().getErrMsg() == null) {
//                    // baseBean = new BaseBean();
//                    // DataBean dataBean = new DataBean();
//                    // dataBean.setErrMsg("暂无数据");
//                    // baseBean.setStatus("error");
//                    // baseBean.setData(dataBean);
//                    // handler.sendEmptyMessage(ResultNo);
//                    // } else
//                    if (baseBean.getData().getErrMsg().equals("用户ID错误")
//                            || baseBean.getData().getErrMsg().equals("验证出错")) {
//                        mProgressDialog.dismiss();
//                        Intent intent = new Intent(BaseActivity.this, Techi_LoginActivity.class);
//                        startActivityForResult(intent, Struts.base_login);
//                    } else {
//                        handler.sendEmptyMessage(ResultNo);
//                    }
//                } else {
//                    handler.sendEmptyMessage(ResultOk);
//                }
//
//                // if (baseBean == null) {
//                // baseBean = new BaseBean();
//                // DataBean dataBean = new DataBean();
//                // dataBean.setErrMsg("网络问题！请求失败");
//                // baseBean.setStatus("error");
//                // baseBean.setData(dataBean);
//                // } else if (!baseBean.getStatus().equals("ok")) {
//                // if (baseBean.getData().getErrMsg().equals("用户ID错误")
//                // || baseBean.getData().getErrMsg().equals("验证出错")) {
//                // Intent intent = new Intent(BaseActivity.this,
//                // Techi_LoginActivity.class);
//                // startActivityForResult(intent, Struts.base_login);
//                // } else {
//                // handler.sendEmptyMessage(ResultNo);
//                // }
//                // } else if (baseBean.getStatus().equals("ok")) {
//                // handler.sendEmptyMessage(ResultOk);
//                // }
//            }
//        });
//        thread.start();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case Struts.base_login:
//                if (resultCode == RESULT_OK) {
//                    showTipMsg("数据加载中·····");
//                    startRequestUrl();
//                } else if (resultCode == RESULT_CANCELED) {
//                    finish();
//                }
//            default:
//                break;
//        }
//
//    }

    /**
     * 请求网络数据
     *
     * @paramrequestType
     *            一个页面可能有多个请求方式 请求类型
     */
    public void startRequestUrl() {
    }

}
