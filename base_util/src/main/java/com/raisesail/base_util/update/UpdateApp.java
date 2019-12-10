package com.raisesail.base_util.update;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.raisesail.base_util.R;

import org.jetbrains.annotations.NotNull;

import constacne.UiType;
import listener.Md5CheckResultListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class UpdateApp {
    private static final String TAG = UpdateApp.class.getSimpleName();
    private UpdateAppUtils updateAppUtils;
    private Context mContext;
    private UiConfig mUiConfig;
    private UpdateConfig mUpdateConfig;

    public UpdateApp(Context context) {
        mContext = context;
        updateAppUtils = UpdateAppUtils.getInstance();
        mUiConfig = getUiConfig();
        mUpdateConfig = getUpdateConfig();
    }

    /**
     * 删除已经安装的apk
     */
    public void deleteInstalledApk() {
        if (updateAppUtils != null) {
            updateAppUtils.deleteInstalledApk();
        }
    }

    /**
     * 配置UIConfig
     *
     * @return UiConfig
     */
    private UiConfig getUiConfig() {
        UiConfig uiConfig = new UiConfig();
        uiConfig.setUpdateBtnBgColor(mContext.getColor(R.color.colorAccent));
        uiConfig.setUpdateBtnTextColor(Color.WHITE);
        uiConfig.setUiType(UiType.PLENTIFUL);
        return uiConfig;
    }

    /**
     * 配置UpdateConfig
     *
     * @return UpdateConfig
     */
    private UpdateConfig getUpdateConfig() {
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.setNeedCheckMd5(true);
        updateConfig.setNotifyImgRes(R.drawable.ic_update_logo);
        return updateConfig;
    }

    /**
     * 开始更新
     *
     * @param url     String
     * @param content String
     */
    public void startUpdate(String url, String content) {
        updateAppUtils.apkUrl(url)
                .updateTitle(mContext.getResources().getString(R.string.update_title))
                .updateContent(content)
                .uiConfig(mUiConfig)
                .updateConfig(mUpdateConfig)
                .setMd5CheckResultListener(new Md5CheckResultListener() {
                    @Override
                    public void onResult(boolean result) {
                        Log.d(TAG, "[updateApplication] onResult:" + result);
                    }
                })
                .setUpdateDownloadListener(new UpdateDownloadListener() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "[updateApplication] onStart:");
                    }

                    @Override
                    public void onDownload(int progress) {
                        Log.d(TAG, "[updateApplication] onDownload:" + progress);
                    }
                    @Override
                    public void onFinish() {
                        Log.d(TAG, "[updateApplication] onFinish:");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.d(TAG, "[updateApplication] onError:" + e);
                    }
                })
                .update();
    }
}
