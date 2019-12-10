package com.raisesail.base_net;

import android.content.Context;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRetrofit {

    private static final long TIMEOUT = 1000L;
    private OkHttpClient.Builder mOkHttpClient;
    private static volatile HttpRetrofit mHttpRetrofit;
    private HttpsUtil.SSLParams sslParams;

    public HttpRetrofit(Context context) {
        initRetrofit(context);
    }

    public static HttpRetrofit getInstance(Context context) {
        if (mHttpRetrofit == null) {
            synchronized (HttpRetrofit.class) {
                if (mHttpRetrofit == null) {
                    mHttpRetrofit = new HttpRetrofit(context);
                }
            }
        }
        return mHttpRetrofit;
    }

    private void initRetrofit(Context context) {
        try {
            //使用12306认证证书
            sslParams = HttpsUtil.getSslSocketFactory(context,
                    new InputStream[]{context.getAssets().open("app.cer")}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient.addInterceptor(httpLoggingInterceptor);
        }
    }

    /**
     * create api imp
     * https访问需要添加证书认证
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> clazz, String baseUrl) {
        URL mBaseUrl = null;
        try {
            mBaseUrl = new URL(baseUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (mBaseUrl != null) {
            boolean isHttps = mBaseUrl.getProtocol().toUpperCase().equals("HTTPS");
            if (sslParams != null && isHttps) {
                mOkHttpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                mOkHttpClient.hostnameVerifier(HttpsUtil.getHostnameVerifier());
            }
        }
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient.build())
                .build()
                .create(clazz);
    }

}
