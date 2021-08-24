package com.yhzc.schooldormitorymobile.ui.askLeave;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 12:03
 * @描述:
 */
public class ApproveAdapter extends BaseQuickAdapter<LeaveApproveModel.DataBean, BaseViewHolder> {
    public ApproveAdapter(int layoutResId, @Nullable List<LeaveApproveModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveApproveModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, dataBean.getApproveType());
        ApproveItemAdapter approveItemAdapter = new ApproveItemAdapter(R.layout.item_leave_approve_item, dataBean.getApproveUser());
        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_sp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(approveItemAdapter);
    }
}
