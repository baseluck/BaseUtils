package com.yhzc.schooldormitorymobile.ui.changePwd;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityChangePwdBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.login.LoginActivity;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/21 13:47
 * @描述:
 */
public class ChangePwdActivity extends BaseActivity<ChangePwdViewModel, ActivityChangePwdBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.tvSend.setOnClickListener(v -> changePwd());
    }

    private void changePwd() {

        if (StringUtils.isEmpty(mViewDataBind.etOldPwd.getText().toString().trim())) {
            ToastUtils.showShort("请输入原密码");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etNewPwdO.getText().toString().trim())) {
            ToastUtils.showShort("请输入新密码");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etNewPwdT.getText().toString().trim())) {
            ToastUtils.showShort("请再次输入新密码");
            return;
        }

        if (!StringUtils.equals(mViewDataBind.etNewPwdO.getText().toString().trim(), mViewDataBind.etNewPwdT.getText().toString().trim())) {
            ToastUtils.showShort("两次输入的新密码不一致，请重新输入");
            return;
        }

        String oldPwd = EncryptUtils.encryptMD5ToString(mViewDataBind.etOldPwd.getText().toString().trim());
        String newPwd = EncryptUtils.encryptMD5ToString(mViewDataBind.etNewPwdO.getText().toString().trim());

        Map<String, String> map = new HashMap<>(4);
        map.put("oldPassword", oldPwd);
        map.put("password", newPwd);

        OkGoUtils.post(ApiUrl.CHANGEPWD, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("修改成功");
                    Cache.savePassword("");
                    ActivityUtils.startActivity(LoginActivity.class);
                    ActivityUtils.finishAllActivities();
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });


    }

    @Override
    protected int initTitle() {
        return R.string.xgmm;
    }
}