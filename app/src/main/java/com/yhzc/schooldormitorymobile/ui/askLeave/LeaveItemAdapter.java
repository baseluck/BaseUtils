package com.yhzc.schooldormitorymobile.ui.askLeave;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @描述:
 * @创建日期: 2021/4/16 14:08
 * @author: ProcyonLotor
 */
public class LeaveItemAdapter extends BaseQuickAdapter<LeaveItemModel, BaseViewHolder> {

    public LeaveItemAdapter(int layoutResId, @Nullable List<LeaveItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveItemModel leaveItemModel) {
        TextView tvContent = baseViewHolder.getView(R.id.tv_leave_content);
        tvContent.setHint(leaveItemModel.getHintContent());
        tvContent.setText(leaveItemModel.getContent());
        baseViewHolder.setText(R.id.tv_leave_title, leaveItemModel.getTitle());

    }
}
