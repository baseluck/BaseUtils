package com.yhzc.schooldormitorymobile.ui.notetake.take;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 15:31
 * @描述:
 */
public class TimeAdapter extends BaseQuickAdapter<TimeModel, BaseViewHolder> {
    public TimeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TimeModel timeModel) {
        baseViewHolder.setText(R.id.tv_time, timeModel.getTime());

        if (timeModel.isSelected()) {
            baseViewHolder.setBackgroundResource(R.id.tv_time, R.drawable.note_take_time_selected);
            baseViewHolder.setTextColor(R.id.tv_time,0xffffffff);
        } else {
            baseViewHolder.setBackgroundResource(R.id.tv_time, R.drawable.note_take_time_unselected);
            baseViewHolder.setTextColor(R.id.tv_time,0xff333333);
        }
    }
}
