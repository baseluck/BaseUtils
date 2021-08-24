package com.yhzc.schooldormitorymobile.ui.askLeaveDetails;

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
public class LeaveDetailsItemAdapter extends BaseQuickAdapter<LeaveDetailsModel.DataBean.ContentListBean, BaseViewHolder> {

    public LeaveDetailsItemAdapter(int layoutResId, @Nullable List<LeaveDetailsModel.DataBean.ContentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveDetailsModel.DataBean.ContentListBean leaveItemModel) {
        TextView tvContent = baseViewHolder.getView(R.id.tv_leave_content);
        tvContent.setText(leaveItemModel.getContent());
        baseViewHolder.setText(R.id.tv_leave_title, leaveItemModel.getTitle());

    }
}
