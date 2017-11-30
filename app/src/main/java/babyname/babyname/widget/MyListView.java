package babyname.babyname.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyListView extends ListView {

    private TextView mFocus;
    private MyListView instance;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        instance = this;
        instance.setOnKeyListener(mOnKeyListener);
    }

    private View.OnKeyListener mOnKeyListener=new View.OnKeyListener() {

        @Override
        public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            // TODO 自动生成的方法存根
            switch(arg2.getAction()){
                case KeyEvent.ACTION_DOWN:
                    if(arg1==KeyEvent.KEYCODE_DPAD_UP||(arg1==KeyEvent.KEYCODE_DPAD_DOWN))
                        moveFocus(arg1,arg0);
                    break;

            }

            return false;
        }

        private void moveFocus(int direction,View focus) {
            int index=instance.getSelectedItemPosition();
            //丢失焦点的时候返回。
            if(index==-1){
                return;
            }
            //获取下一个焦点
            int position;
            if(index<instance.getAdapter().getCount()-1&&direction==KeyEvent.KEYCODE_DPAD_DOWN)
                position =index+1-getFirstVisiblePosition();
            else if(index>0&&direction==KeyEvent.KEYCODE_DPAD_UP)
                position =index-1-getFirstVisiblePosition();
            else
                position = index-getFirstVisiblePosition();
            mFocus=(TextView) instance.getChildAt(position);
            //焦点为空返回
            if(mFocus==null)
                return;
            int[] loc = new int[2];
            instance.getLocationOnScreen(loc);

            int[] screenLocation = new int[2];
            mFocus.getLocationOnScreen(screenLocation);

            //这里的高度需要有一个调整值，不知道为什么。
            int height = ((TextView)mFocus).getHeight()+2;

            if (screenLocation[1] < loc[1] + 8*height) {
                instance.smoothScrollBy(instance.getScrollY() - height, 0);
            }
            if (screenLocation[1] > loc[1] + 8*height) {
                instance.smoothScrollBy(instance.getScrollY() + height, 0);
            }

        }
    };
}
