package com.yhzc.schooldormitorymobile.ui.statistics;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 15:55
 * @描述:
 */
public class PersonCheckStatisticsAdapter extends BaseQuickAdapter<WeekStatisticsModel.DataBean, BaseViewHolder> {

    public PersonCheckStatisticsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WeekStatisticsModel.DataBean rankBean) {
        baseViewHolder.setText(R.id.tv_name, rankBean.getDay());
        baseViewHolder.setText(R.id.tv_rank, String.valueOf(rankBean.getNum()));
    }
}
