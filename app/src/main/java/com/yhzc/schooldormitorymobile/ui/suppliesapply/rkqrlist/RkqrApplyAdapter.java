package com.yhzc.schooldormitorymobile.ui.suppliesapply.rkqrlist;

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
public class RkqrApplyAdapter extends BaseQuickAdapter<QrListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {
    public RkqrApplyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QrListModel.DataBean.ListBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, dataBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_hqsy, String.format(StringUtils.getString(R.string.wzsq_type),
                StringUtils.isEmpty(dataBean.getVcType()) ? "无" : dataBean.getVcType()));
        baseViewHolder.setText(R.id.tv_start_time,String.format(StringUtils.getString(R.string.approve_fqr),dataBean.getVcUserId()));
        baseViewHolder.getView(R.id.tv_end_time).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_time, dataBean.getDtRecordTime());
        TextView tvStatus = baseViewHolder.getView(R.id.tv_status);
        baseViewHolder.setTextColor(R.id.tv_status, 0xff999999);
        baseViewHolder.setVisible(R.id.tv_status, false);
    }
}
