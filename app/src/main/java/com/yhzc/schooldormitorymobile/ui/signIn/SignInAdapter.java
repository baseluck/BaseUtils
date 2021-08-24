package com.yhzc.schooldormitorymobile.ui.signIn;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @描述:
 * @创建日期: 2021/4/14 10:22
 * @author: ProcyonLotor
 */
public class SignInAdapter extends BaseQuickAdapter<SignInModel.DataBean.SignListBean, BaseViewHolder> {

    public SignInAdapter(int layoutResId, @Nullable List<SignInModel.DataBean.SignListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SignInModel.DataBean.SignListBean signInModel) {
        baseViewHolder.setText(R.id.tv_need_time, signInModel.getSignTime()+" "+signInModel.getSignType());
        baseViewHolder.setText(R.id.tv_dk_time, signInModel.getSignStatus());
    }
}
