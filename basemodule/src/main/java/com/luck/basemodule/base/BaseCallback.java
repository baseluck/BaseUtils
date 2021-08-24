package com.luck.basemodule.base;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 17:25
 * @描述:
 */
public abstract class BaseCallback extends StringCallback {

    public static final int SUCCESS = 200;

    @Override
    public void onSuccess(Response<String> response) {
        String body = response.body();
        try {
            JSONObject jsonObject = new JSONObject(body);
            int code = jsonObject.getInt("code");
            if (code == SUCCESS) {
                onSuccess(response.body());
            } else {
                String msg = jsonObject.optString("msg");
                onError(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        if (response.getException() instanceof SocketTimeoutException) {
            onError("请求超时");
        } else if (response.getException() instanceof SocketException) {
            onError("服务器异常");
        } else {
            onError("请求失败");
        }
    }

    /**
     * 请求成功
     *
     * @param callback
     */
    protected abstract void onSuccess(String callback);

    /**
     * 请求失败
     *
     * @param error
     */
    protected abstract void onError(String error);

}
