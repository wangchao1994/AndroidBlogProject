package com.raisesail.base_util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToastView;
    private static Toast mToast;
    private static long mFirstTime = 0 ;
    private static long mSecondTime = 0 ;
    private static String oldMsg;
    /**
     * 自定义短Toast调用
     * @param context 上下文
     * @param message 显示文本
     * @return void
     */
    public static void showToastView(final Context context, final String message) {
        if (mToastView != null){
            mToastView.cancel();
            mToastView = null;
        }
        mToastView = new Toast(context.getApplicationContext());
        mToastView.setDuration(Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_sys_show_toast, null);
        TextView textView = view.findViewById(R.id.sys_show_toast_txt);
        textView.setText(message);
        mToastView.setView(view);
        mToastView.setGravity(Gravity.CENTER, 0, 0);
        mToastView.show();
    }
    /**
     * 默认Toast调用
     * @param context 上下文
     * @param message 显示文本
     */
    public static void show(final Context context, final String message) {
        if (mToast == null){
            mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
            mToast.show();
            mFirstTime = System.currentTimeMillis() ;
        }else {
            mSecondTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(mSecondTime - mFirstTime > Toast.LENGTH_SHORT){
                    mToast.show() ;
                }
            }else{
                oldMsg = message ;
                mToast.setText(message) ;
                mToast.show() ;
            }
        }
        mFirstTime = mSecondTime ;
    }
}
