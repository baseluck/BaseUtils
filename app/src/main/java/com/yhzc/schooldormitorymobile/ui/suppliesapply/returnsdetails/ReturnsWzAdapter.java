package com.yhzc.schooldormitorymobile.ui.suppliesapply.returnsdetails;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/26 17:52
 * @描述:
 */
public class ReturnsWzAdapter extends BaseQuickAdapter<ReturnDetailsModel.DataBean.DetailsBean, BaseViewHolder> {
    public ReturnsWzAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReturnDetailsModel.DataBean.DetailsBean detailListBean) {
        baseViewHolder.setText(R.id.tv_name, detailListBean.getVcMaterialName());
        baseViewHolder.setText(R.id.tv_gg, detailListBean.getVcSpecification());
        baseViewHolder.setText(R.id.tv_sl, detailListBean.getVcUnit());
        baseViewHolder.setText(R.id.tv_num, String.valueOf(detailListBean.getIntNum()));
    }
}
