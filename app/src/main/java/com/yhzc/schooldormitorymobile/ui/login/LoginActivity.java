package com.yhzc.schooldormitorymobile.ui.login;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityLoginBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.yhzc.schooldormitorymobile.ui.home.HomeActivity;
import com.yhzc.schooldormitorymobile.utils.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/13 20:03
 * @描述: 登录页面
 */
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        BarUtils.setStatusBarColor(this, 0x00000000);

        String checkOut = getIntent().getStringExtra("checkOut");
        if (checkOut != null && StringUtils.equals("false", checkOut)) {
            LogOutDialog((ViewGroup) this.getWindow().getDecorView());
        }

        mViewDataBind.etAccount.setText(Cache.getAccount());
        mViewDataBind.etPassword.setText(Cache.getPassword());
        mViewDataBind.etHttp.setText(Cache.getBaseHttp());
        mViewDataBind.etHttp.setVisibility(View.GONE);

        mViewDataBind.tvLogin.setOnClickListener(v -> {
            Cache.saveToken("");
            login();
        });
    }

    private void testLogin() {
        Cache.saveToken("token");
        ActivityUtils.startActivity(HomeActivity.class);
        finish();
    }

    private void login() {

        if (StringUtils.isEmpty(mViewDataBind.etAccount.getText().toString().trim())) {
            ToastUtils.showShort("账号不能为空");
            return;
        }

        if (StringUtils.isEmpty(mViewDataBind.etPassword.getText().toString().trim())) {
            ToastUtils.showShort("密码不能为空");
            return;
        }

        showLoading("正在登录");
        String account = mViewDataBind.etAccount.getText().toString().trim();
        String password = EncryptUtils.encryptMD5ToString(mViewDataBind.etPassword.getText().toString().trim());

        Map<String, String> map = new HashMap<>(4);
        map.put("account", account);
        map.put("password", password);


        Cache.saveBaseHttp("");
        Cache.saveBaseHttp("http://39.99.169.115:7771");
//        Cache.saveBaseHttp("http://124.71.235.115");
//        String url = "http://124.71.235.115";

//        Cache.saveBaseHttp("");
//        Cache.saveBaseHttp(mViewDataBind.etHttp.getText().toString().trim());
//        String url;
//        if (mViewDataBind.etHttp.getText().toString().trim().contains("http://")) {
//            url = mViewDataBind.etHttp.getText().toString().trim();
//        } else {
//            url = "http://" + mViewDataBind.etHttp.getText().toString().trim();
//        }


        OkGo.<String>post(ApiUrl.LOGIN)
                .headers("NeedToken", "false")
                .upJson(GsonUtils.toJson(map))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissLoading();
                        LoginModel loginModel = GsonUtils.fromJson(response.body(), LoginModel.class);
                        if (loginModel.getCode() == ApiUrl.SUCCESS) {
                            Cache.saveAccount(account);
                            Cache.savePassword(mViewDataBind.etPassword.getText().toString().trim());
                            Cache.saveLogin(response.body());
                            Cache.saveToken(loginModel.getData().getToken());
                            initBaiduToken(loginModel.getData().getToken());
                        } else {
                            dismissLoading();
                            ToastUtils.showShort(loginModel.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissLoading();
                        ToastUtils.showShort("请求失败");
                    }

                });


    }

    private void initBaiduToken(String token) {
        OkGo.<String>post(ApiUrl.BAIDUTOKEN)
                .headers("Authorization", token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = GsonUtils.fromJson(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 200) {
                            Cache.saveBaiduToken(baseBean.getMsg());
                            ActivityUtils.startActivity(HomeActivity.class);
                            ActivityUtils.finishAllActivities();
                        } else {
                            ToastUtils.showShort(baseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissLoading();
                        ToastUtils.showShort("人脸识别token获取失败");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissLoading();
                    }
                });
    }

    private static final int TIME_EXIT = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            super.onBackPressed();
            ActivityUtils.finishAllActivities();
            System.exit(0);
        } else {
            ToastUtils.showShort("再点击一次返回退出程序");
            mBackPressed = System.currentTimeMillis();
        }

    }

    private void LogOutDialog(ViewGroup viewGroup) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_logout, viewGroup, false);
        TextView tvMsg = dialogView.findViewById(R.id.tv_msg);
        TextView tvOut = dialogView.findViewById(R.id.tv_out);
        Dialog dialog = new Dialog(this, R.style.ShareDialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        tvMsg.setText("登录超时，请重新登录");
        dialog.show();
        tvOut.setOnClickListener(v -> {
            dialog.dismiss();
            Cache.saveLogin("");
        });
    }

    @Override
    protected int initTitle() {
        return R.string.login;
    }
}