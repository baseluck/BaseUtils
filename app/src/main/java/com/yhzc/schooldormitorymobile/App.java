package com.yhzc.schooldormitorymobile;

import android.app.Application;
import android.os.Environment;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yhzc.schooldormitorymobile.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.yhzc.schooldormitorymobile.http.TokenInterceptor;
import com.yhzc.schooldormitorymobile.utils.Cache;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 16:57
 * @描述:
 */
@SuppressWarnings("AliDeprecation")
public class App extends Application {

    public static final String BUGLY_APP_ID = "271121d38a";

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();
        initLogUtils();

//        try {
//            OkHttpClient builder = new OkHttpClient.Builder()
//                    .addInterceptor(new TokenInterceptor())
//                    .build();
//            OkGo.getInstance().setOkHttpClient(builder);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        TokenInterceptor loggingInterceptor = new TokenInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);


        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build());

    }

    private void initOkGo() {

        HttpHeaders headers = new HttpHeaders();
        if (!StringUtils.isEmpty(Cache.getToken())) {
            headers.put("Authorization", Cache.getToken());
        }

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3)
                .addCommonHeaders(headers);

    }

    private void initLogUtils() {
        LogUtils.getConfig().setGlobalTag("logutils");
    }

    private void initBugly() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 1000;
        Beta.largeIconId = R.mipmap.ic_launcher;
        Beta.smallIconId = R.mipmap.ic_launcher;
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.showInterruptedStrategy = true;
        Bugly.init(getApplicationContext(), BUGLY_APP_ID, true);
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_APP_ID, true);
    }
}
