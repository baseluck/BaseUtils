package com.yhzc.schooldormitorymobile.ui.suppliesapply.applylist;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 15:47
 * @描述:
 */
public class WzApplyAdapter extends BaseQuickAdapter<LeaveListModel.DataBean, BaseViewHolder> {
    public WzApplyAdapter(int layoutResId, @Nullable List<LeaveListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveListModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, dataBean.getLeaveTitle());
        baseViewHolder.setText(R.id.tv_hqsy,String.format(StringUtils.getString(R.string.wzsq_reason),
                        StringUtils.isEmpty(dataBean.getLeaveReason()) ? "无" : dataBean.getLeaveReason()));
        baseViewHolder.setText(R.id.tv_start_time, String.format(StringUtils.getString(R.string.start_time), dataBean.getStartTime()));
        baseViewHolder.getView(R.id.tv_start_time).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.tv_end_time).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_end_time, String.format(StringUtils.getString(R.string.end_time), dataBean.getEndTime()));
        baseViewHolder.setText(R.id.tv_time, dataBean.getSendTime());
        TextView tvStatus = baseViewHolder.getView(R.id.tv_status);
        baseViewHolder.setTextColor(R.id.tv_status, 0xff999999);
        //主单状态  0-待提交 1-已提交 2-审批通过 3-审批驳回  4-审核撤回 5-审核终止

        if (dataBean.getLeaveStatus() == 1 || dataBean.getLeaveStatus() == 0) {
            baseViewHolder.setText(R.id.tv_status, "待审批");
        } else if (dataBean.getLeaveStatus() == 2) {
            baseViewHolder.setText(R.id.tv_status, "已通过");
        } else if (dataBean.getLeaveStatus() == 3) {
            baseViewHolder.setText(R.id.tv_status, "已驳回");
        } else if (dataBean.getLeaveStatus() == 4) {
            baseViewHolder.setText(R.id.tv_status, "审核撤回");
        } else if (dataBean.getLeaveStatus() == 5) {
            baseViewHolder.setText(R.id.tv_status, "审核终止");
        }
    }
}
