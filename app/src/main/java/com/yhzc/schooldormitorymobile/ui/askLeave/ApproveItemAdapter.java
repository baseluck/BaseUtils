package com.yhzc.schooldormitorymobile.ui.askLeave;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 12:04
 * @描述:
 */
public class ApproveItemAdapter extends BaseQuickAdapter<LeaveApproveModel.DataBean.ApproveUserBean, BaseViewHolder> {
    public ApproveItemAdapter(int layoutResId, @Nullable List<LeaveApproveModel.DataBean.ApproveUserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveApproveModel.DataBean.ApproveUserBean approveUserBean) {
        baseViewHolder.setText(R.id.tv_name, approveUserBean.getName());
        Glide.with(getContext())
                .load(Cache.getHttp() + approveUserBean.getHeadPic())
                .placeholder(R.mipmap.ic_head)
                .into((ImageView) baseViewHolder.getView(R.id.iv_head));
    }
}
