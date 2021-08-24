package com.yhzc.schooldormitorymobile.ui.askLeaveDetails;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/26 17:52
 * @描述:
 */
public class ApproveWzAdapter extends BaseQuickAdapter<LeaveDetailsModel.DataBean.DetailListBean, BaseViewHolder> {
    public ApproveWzAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveDetailsModel.DataBean.DetailListBean detailListBean) {
        baseViewHolder.setText(R.id.tv_name, detailListBean.getVcMaterialName());
        baseViewHolder.setText(R.id.tv_gg, detailListBean.getVcSpecification());
        baseViewHolder.setText(R.id.tv_sl, detailListBean.getVcUnit());
        baseViewHolder.setText(R.id.tv_num, detailListBean.getIntNum());
    }
}
