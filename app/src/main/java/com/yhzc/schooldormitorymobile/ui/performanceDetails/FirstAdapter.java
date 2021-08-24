package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:28
 * @描述:
 */
public class FirstAdapter extends BaseQuickAdapter<DetailsModel.DataBean.ContentBean, BaseViewHolder> {
    public FirstAdapter(int layoutResId, @Nullable List<DetailsModel.DataBean.ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DetailsModel.DataBean.ContentBean firstTitle) {
        baseViewHolder.setText(R.id.tv_title, firstTitle.getFirstTitle());
        if (firstTitle.isSelect()) {
            baseViewHolder.setTextColor(R.id.tv_title, 0xffffffff);
            baseViewHolder.setBackgroundResource(R.id.tv_title, R.mipmap.ic_kh_select);
        } else {
            baseViewHolder.setTextColor(R.id.tv_title, 0xffBADBF1);
            baseViewHolder.setBackgroundResource(R.id.tv_title, R.mipmap.ic_kh_unselect);
        }

        baseViewHolder.setVisible(R.id.iv_wc, firstTitle.isResult());
    }
}
