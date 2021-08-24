package com.yhzc.schooldormitorymobile.http;

import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.yhzc.schooldormitorymobile.ui.login.LoginActivity;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/23 17:45
 * @描述:
 */
public class TokenInterceptor implements Interceptor {

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private volatile HttpLoggingInterceptor.Level printLevel = HttpLoggingInterceptor.Level.BODY;
    private java.util.logging.Level colorLevel = java.util.logging.Level.INFO;
    private final Logger logger;

    public enum Level {NONE, BASIC, HEADERS, BODY}

    public TokenInterceptor(String tag) {
        logger = Logger.getLogger(tag);
    }

    public void setPrintLevel(HttpLoggingInterceptor.Level level) {
        if (printLevel == null) {
            throw new NullPointerException("printLevel == null. Use Level.NONE instead.");
        }
        printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        colorLevel = level;
    }

    private void log(String message) {
        logger.log(colorLevel, message);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();


        Request.Builder builder = original
                .newBuilder();
        String needToken = original.header("NeedToken");
        String checkToken = original.header("checkToken");

        if (checkToken == null && "true".equals(needToken)) {
            checkToken();
            builder.header("Authorization", Cache.getToken());
        } else if ("true".equals(checkToken)) {
            Response originalResponse = chain.proceed(original);
            JSONObject jsonObject = resultResponse(originalResponse);
            boolean data = jsonObject.optBoolean("data");
            if (!data) {
                LogOutDialog();
                OkGo.getInstance().cancelAll();
            }
        }
        Request request = builder
                .removeHeader("NeedToken")
                .method(original.method(), original.body())
                .build();


        return chain.proceed(request);
    }


    private void checkToken() {
        OkGo.<String>get(ApiUrl.CHECKTOKEN)
                .headers("Authorization", Cache.getToken())
                .headers("checkToken", "true")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String json = response.body();
                    }
                });
    }

    public JSONObject resultResponse(Response response) {
        try {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            if (contentLength != 0) {
                String result = buffer.clone().readString(UTF8);
                return new JSONObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void LogOutDialog() {
        Cache.saveLogin("");
        Intent intent = new Intent(ActivityUtils.getTopActivity(), LoginActivity.class);
        intent.putExtra("checkOut", "false");
        ActivityUtils.startActivity(intent);
        ActivityUtils.finishAllActivities();
    }

}