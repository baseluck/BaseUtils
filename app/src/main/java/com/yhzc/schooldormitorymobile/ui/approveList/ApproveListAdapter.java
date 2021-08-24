package com.yhzc.schooldormitorymobile.ui.approveList;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 17:56
 * @描述:
 */
public class ApproveListAdapter extends BaseQuickAdapter<ApproveListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {
    public ApproveListAdapter(int layoutResId, @Nullable List<ApproveListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ApproveListModel.DataBean.ListBean approveListModel) {
        baseViewHolder.setText(R.id.tv_title, approveListModel.getVcTitle());
        baseViewHolder.setText(R.id.tv_hqsy, String.format(StringUtils.getString(R.string.approve_rwlx), approveListModel.getProcessInstanceName()));
        baseViewHolder.setText(R.id.tv_start_time, String.format(StringUtils.getString(R.string.approve_fqr), approveListModel.getInitiator()));
        baseViewHolder.setText(R.id.tv_end_time, String.format(StringUtils.getString(R.string.approve_spjd), approveListModel.getName()));
        baseViewHolder.setText(R.id.tv_time, approveListModel.getCreateTime());
        baseViewHolder.setText(R.id.tv_status, "待审批");
    }
}
