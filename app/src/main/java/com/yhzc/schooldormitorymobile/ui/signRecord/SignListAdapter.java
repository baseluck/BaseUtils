package com.yhzc.schooldormitorymobile.ui.signRecord;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/21 19:27
 * @描述:
 */
public class SignListAdapter extends BaseQuickAdapter<SignListBean.DataBean, BaseViewHolder> {

    public SignListAdapter(int layoutResId, @Nullable List<SignListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SignListBean.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_status, dataBean.getSignStatus());
        baseViewHolder.setText(R.id.tv_time, dataBean.getSignTime());
        baseViewHolder.setText(R.id.tv_content, dataBean.getSignType());
    }
}
