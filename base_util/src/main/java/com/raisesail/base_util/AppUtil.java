package com.raisesail.base_util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AppUtil {

    public static int getVersionCode(Context mContext) {
        if (mContext != null) {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException ignored) {
                Log.d("PackageManager","[getVersionCode]:"+ignored);
            }
        }
        return 0;
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("SplashActivity", "Exception", e);
        }
        return "V "+versionName;
    }
    /**
     * 根据包名获取App的Icon
     * @param mContext
     */
    public Drawable getAppIcon(Context mContext) {
        if (mContext == null)return null;
        try {
            PackageManager packageManager = mContext.getPackageManager();
            ApplicationInfo info = packageManager.getApplicationInfo(mContext.getPackageName(), 0);
            return info.loadIcon(packageManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mContext.getResources().getDrawable(R.drawable.ic_update_logo, mContext.getTheme());
    }
}
