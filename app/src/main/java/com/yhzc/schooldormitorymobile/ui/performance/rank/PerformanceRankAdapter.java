package com.yhzc.schooldormitorymobile.ui.performance.rank;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 15:55
 * @描述:
 */
public class PerformanceRankAdapter extends BaseQuickAdapter<PerformanceRankModel.DataBean.RankBean, BaseViewHolder> {

    public PerformanceRankAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PerformanceRankModel.DataBean.RankBean rankBean) {
        baseViewHolder.setText(R.id.tv_name, rankBean.getVcUserId());
        baseViewHolder.setText(R.id.tv_score, rankBean.getScore());
        baseViewHolder.setText(R.id.tv_rank, rankBean.getRank());
    }
}
