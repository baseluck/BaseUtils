package com.yhzc.schooldormitorymobile.ui.checkthebed.locationList;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/17 14:47
 * @描述:
 */
public class LocationListAdapter extends BaseQuickAdapter<LocationListModel.DataBean, BaseViewHolder> {
    public LocationListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, LocationListModel.DataBean dataBean) {
        String location=dataBean.getXqmc() + dataBean.getLdmc() + dataBean.getDymc();
        baseViewHolder.setText(R.id.tv_floor, location);
    }
}
