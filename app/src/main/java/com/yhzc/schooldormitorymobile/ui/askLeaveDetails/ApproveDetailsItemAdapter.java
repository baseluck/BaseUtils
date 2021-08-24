package com.yhzc.schooldormitorymobile.ui.askLeaveDetails;

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
public class ApproveDetailsItemAdapter extends BaseQuickAdapter<LeaveDetailsModel.DataBean.ApproveListBean.ApproveUserBean, BaseViewHolder> {

    public ApproveDetailsItemAdapter(int layoutResId, @Nullable List<LeaveDetailsModel.DataBean.ApproveListBean.ApproveUserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveDetailsModel.DataBean.ApproveListBean.ApproveUserBean approveUserBean) {
        baseViewHolder.setText(R.id.tv_name, approveUserBean.getName());
        baseViewHolder.setVisible(R.id.tv_content, true);
        baseViewHolder.setText(R.id.tv_content, approveUserBean.getOpinion());
        Glide.with(getContext())
                .load(Cache.getHttp() + approveUserBean.getHeadPic())
                .placeholder(R.mipmap.ic_head)
                .into((ImageView) baseViewHolder.getView(R.id.iv_head));
    }
}
