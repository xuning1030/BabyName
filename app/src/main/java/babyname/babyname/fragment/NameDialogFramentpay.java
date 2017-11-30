package babyname.babyname.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import babyname.babyname.R;
import babyname.babyname.Utils.DatabaseUtils;
import babyname.babyname.bean.GetHistoryOrderBean;


/**
 * Created by Administrator on 2017/7/5.
 */

public class NameDialogFramentpay extends DialogFragment {
    private LinearLayout mAliPay;
    private LinearLayout mWxPay;
    private ImageView mclose_shut;
    private TextView tv_strikethrough;
    private String nowyear, oldyear, name, sex, num;
    private TextView order_dialog_txt_name, order_dialog_txt_nowyear, order_dialog_txt_oldyear, order_dialog_txt_sex, order_dialog_txt_order;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    dismiss();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.order_paydialog, container);
        mAliPay = (LinearLayout) view.findViewById(R.id.alipay_layouts);
        mWxPay = (LinearLayout) view.findViewById(R.id.wxpay_layouts);
        mclose_shut = (ImageView) view.findViewById(R.id.shut_close);
        tv_strikethrough = (TextView) view.findViewById(R.id.tv_strikethrough);
        tv_strikethrough.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        order_dialog_txt_name = (TextView) view.findViewById(R.id.order_dialog_txt_name);
        order_dialog_txt_nowyear = (TextView) view.findViewById(R.id.order_dialog_txt_nowyear);
        order_dialog_txt_oldyear = (TextView) view.findViewById(R.id.order_dialog_txt_oldyear);
        order_dialog_txt_sex = (TextView) view.findViewById(R.id.order_dialog_txt_sex);
        order_dialog_txt_order = (TextView) view.findViewById(R.id.order_dialog_txt_order);
        OderData();

        mclose_shut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mWxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mwxpayActivityListener.WxpayActivityListener();
                dismiss();
            }
        });
        mAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                malipayActivityListener.AlipayActivityListener();
                dismiss();
            }
        });
        return view;
    }


    public interface AlipayActivityListener {

        void AlipayActivityListener();

    }

    private AlipayActivityListener malipayActivityListener;

    public void setAliPayActivityListener(AlipayActivityListener alipayListener) {
        this.malipayActivityListener = alipayListener;
    }


    /*
    wx
     */
    public interface WxpayActivityListener {

        void WxpayActivityListener();

    }

    private WxpayActivityListener mwxpayActivityListener;

    public void setWxPayActivityListener(WxpayActivityListener ifinishListener) {
        this.mwxpayActivityListener = ifinishListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void OderData() {
        List<GetHistoryOrderBean> list = DatabaseUtils.getHelper().queryAll(GetHistoryOrderBean.class);
        nowyear = list.get(list.size() - 1).getLunar().toString();//
        sex = list.get(list.size() - 1).getMan().toString();//性别
        num = list.get(list.size() - 1).getOrderno().toString();//订单号
        name = list.get(list.size() - 1).getSurname().toString();//
        oldyear = list.get(list.size() - 1).getSolar().toString();
        order_dialog_txt_name.setText(name);
        order_dialog_txt_nowyear.setText(nowyear);
        order_dialog_txt_oldyear.setText(oldyear);
        order_dialog_txt_sex.setText(sex);
        order_dialog_txt_order.setText(num);

    }
}
