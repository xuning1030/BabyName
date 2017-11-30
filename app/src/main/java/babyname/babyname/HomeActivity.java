package babyname.babyname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/23.
 */

public class HomeActivity extends FragmentActivity{
//        implements HomeFragment.titleSelectInterface {


    public static TabHost mTabHost;
    private TabManager mTabManager;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public TabInfo tabInfo;
    private RadioGroup mainGroup;
    private RadioButton tab_zonghe_rbt, tab_guwen_rbt, meBtn, tab_about_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("tab0").setIndicator("tab0"), HomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("tab1"), GuwenFragment.class, null);
//        mTabManager.addTab(mTabHost.newTabSpec("tab2").setIndicator("tab2"), MeFragment.class, null);
//        mTabManager.addTab(mTabHost.newTabSpec("tab3").setIndicator("tab3"), AboutFragment.class, null);
        findAllView();
    }

//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        try {
//            mSelectInterface = (titleSelectInterface) activity;
//        } catch (Exception e) {
//            throw new ClassCastException(activity.toString() + "must implement OnArticleSelectedListener");
//        }
//    }

    private void findAllView() {
        mainGroup = (RadioGroup) this.findViewById(R.id.tab_radio_group);
        tab_zonghe_rbt = (RadioButton) this.findViewById(R.id.tab_zonghe_rbt);
        tab_guwen_rbt = (RadioButton) this.findViewById(R.id.tab_guwen_rbt);
        tab_zonghe_rbt.setChecked(true);
        mainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int rid) {
                if (rid == R.id.tab_zonghe_rbt) {// 首页
                    mTabHost.setCurrentTabByTag("tab0");
                } else if (rid == R.id.tab_guwen_rbt) {// 订单
                    mTabHost.setCurrentTabByTag("tab1");
                }
//                else if (rid == R.id.tab_me_btn) {// 我
//                    mTabHost.setCurrentTabByTag("tab2");
//                } else if (rid == R.id.tab_about_btn) {// 关于我们
//                    mTabHost.setCurrentTabByTag("tab3");
//                }
            }
        });
    }


//    @Override
//    public void onTitleSelect(String title) {
//        FragmentManager manager = getSupportFragmentManager();
//        GuwenFragment fragment2 = (GuwenFragment)manager.findFragmentById(R.id.containertabcontent);
//        fragment2.setText(title);
//    }


    public class TabManager implements TabHost.OnTabChangeListener {
        private final HomeActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerID;
        // 保存tab
        private final Map<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        private TabInfo mLastTab;

        public TabManager(HomeActivity activity, TabHost tabHost, int containerID) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerID = containerID;
            mTabHost.setOnTabChangedListener(this);
        }

        class TabFactory implements TabHost.TabContentFactory {
            private Context mContext;

            TabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumHeight(0);
                v.setMinimumWidth(0);
                return v;
            }
        }

        // 加入tab
        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new TabFactory(mActivity));
            String tag = tabSpec.getTag();
            TabInfo info = new TabInfo(tag, clss, args);
            final FragmentManager fm = mActivity.getSupportFragmentManager();
            info.fragment = fm.findFragmentByTag(tag);
            // isDetached分离状态
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                String lastTabName = "";
                if (mLastTab != null) {
                    lastTabName = mLastTab.fragment.getTag().toString();
                }
                showTab(newTab);

            }
        }

        private void showTab(TabInfo newTab) {
            fragmentManager = mActivity.getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            // 脱离之前的tab
            if (mLastTab != null && mLastTab.fragment != null) {
                fragmentTransaction.detach(mLastTab.fragment);
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                    fragmentTransaction.add(mContainerID, newTab.fragment, newTab.tag);
                } else {
                    // 激活
                    fragmentTransaction.attach(newTab.fragment);
                }
            }
            mLastTab = newTab;

            fragmentTransaction.commitAllowingStateLoss();// commit();
            // 会在进程的主线程中，用异步的方式来执行,如果想要立即执行这个等待中的操作，就要调用这个方法
            // 所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
            fragmentManager.executePendingTransactions();

        }

    }

    class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _clss, Bundle _args) {
            tag = _tag;
            clss = _clss;
            args = _args;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        /**
         * 主页面按返回键 退出的时候 和按home建 一样的效果
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
