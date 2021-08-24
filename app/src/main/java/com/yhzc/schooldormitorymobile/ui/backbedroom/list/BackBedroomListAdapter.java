package com.yhzc.schooldormitorymobile.ui.backbedroom.list;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 17:25
 * @描述:
 */
public class BackBedroomListAdapter extends BaseQuickAdapter<BackBedroomModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {
    public BackBedroomListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BackBedroomModel.DataBean.ListBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, String.format(StringUtils.getString(R.string.back_bedroom_title), dataBean.getVcStudentId()));
        baseViewHolder.setText(R.id.tv_start_time, String.format(StringUtils.getString(R.string.back_bedroom_reason), dataBean.getTextContent()));
        baseViewHolder.setText(R.id.tv_hqsy, String.format(StringUtils.getString(R.string.back_bedroom_teacher), dataBean.getVcTeacherId()));
        baseViewHolder.getView(R.id.tv_end_time).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_time, dataBean.getDtCreateTime());
        baseViewHolder.getView(R.id.tv_status).setVisibility(View.GONE);
    }
}
