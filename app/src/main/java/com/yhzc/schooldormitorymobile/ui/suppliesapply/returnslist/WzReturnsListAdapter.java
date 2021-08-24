package com.yhzc.schooldormitorymobile.ui.suppliesapply.returnslist;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 15:47
 * @描述:
 */
public class WzReturnsListAdapter extends BaseQuickAdapter<ReturnsListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {
    public WzReturnsListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReturnsListModel.DataBean.ListBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, dataBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_hqsy, String.format(StringUtils.getString(R.string.wzsq_type),
                StringUtils.isEmpty(dataBean.getVcType()) ? "无" : dataBean.getVcType()));
        baseViewHolder.getView(R.id.tv_start_time).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.tv_end_time).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_time, dataBean.getDtRecordTime());
        TextView tvStatus = baseViewHolder.getView(R.id.tv_status);
        baseViewHolder.setVisible(R.id.tv_status, false);
    }
}
