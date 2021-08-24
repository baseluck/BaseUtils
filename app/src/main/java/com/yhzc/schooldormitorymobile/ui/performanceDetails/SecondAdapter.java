package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:31
 * @描述:
 */
public class SecondAdapter extends BaseQuickAdapter<DetailsModel.DataBean.ContentBean.FirstListBean, BaseViewHolder> {
    public SecondAdapter(int layoutResId, @Nullable List<DetailsModel.DataBean.ContentBean.FirstListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DetailsModel.DataBean.ContentBean.FirstListBean secondTitle) {
        baseViewHolder.setText(R.id.tv_left, secondTitle.getSort()+"");
        if (secondTitle.isSelect()) {
            baseViewHolder.setTextColor(R.id.tv_left, 0xff333333);
            baseViewHolder.setBackgroundColor(R.id.tv_left, 0xffACDCFF);
        } else {
            baseViewHolder.setTextColor(R.id.tv_left, 0xff999999);
            baseViewHolder.setBackgroundColor(R.id.tv_left, 0xffF5FAFD);
        }
    }
}
