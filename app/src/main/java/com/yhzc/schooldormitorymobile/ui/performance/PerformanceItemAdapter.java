package com.yhzc.schooldormitorymobile.ui.performance;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/7 16:47
 * @描述:
 */
public class PerformanceItemAdapter extends BaseQuickAdapter<PerforManceModel.DataBean.AssessmentListBean, BaseViewHolder> {
    public PerformanceItemAdapter(int layoutResId, @Nullable List<PerforManceModel.DataBean.AssessmentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PerforManceModel.DataBean.AssessmentListBean performanceList) {
        baseViewHolder.setText(R.id.tv_title, performanceList.getTitle());
        baseViewHolder.setText(R.id.tv_time, String.format(StringUtils.getString(R.string.kh_date), performanceList.getExamineTime()));
        baseViewHolder.setText(R.id.tv_time_tb, String.format(StringUtils.getString(R.string.kh_time), performanceList.getFillTime()));
        if (StringUtils.isEmpty(performanceList.getProgress())) {
            baseViewHolder.setVisible(R.id.tv_result, false);
        }
        baseViewHolder.setText(R.id.tv_result, performanceList.getProgress());
        int itemPosition = getItemPosition(performanceList);
        baseViewHolder.setVisible(R.id.line, itemPosition != 0);
        if (StringUtils.isEmpty(performanceList.getStatus())) {
            baseViewHolder.setVisible(R.id.tv_kh, false);
        }
        baseViewHolder.setText(R.id.tv_kh, performanceList.getStatus());
    }
}
