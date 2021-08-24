package com.yhzc.schooldormitorymobile.ui.checkhealth.details;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:28
 * @描述:
 */
public class FirstAdapter extends BaseQuickAdapter<HealthDetailsModel.DataBean.ListBean, BaseViewHolder> {
    public FirstAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HealthDetailsModel.DataBean.ListBean firstTitle) {
        baseViewHolder.setText(R.id.tv_title, firstTitle.getName());
        if (firstTitle.isSelect()) {
            baseViewHolder.setTextColor(R.id.tv_title, 0xffffffff);
            baseViewHolder.setBackgroundResource(R.id.tv_title, R.mipmap.ic_kh_select);
        } else {
            baseViewHolder.setTextColor(R.id.tv_title, 0xffBADBF1);
            baseViewHolder.setBackgroundResource(R.id.tv_title, R.mipmap.ic_kh_unselect);
        }

        baseViewHolder.setVisible(R.id.iv_wc, false);
    }
}
