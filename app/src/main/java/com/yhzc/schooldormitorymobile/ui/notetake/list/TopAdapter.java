package com.yhzc.schooldormitorymobile.ui.notetake.list;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 11:01
 * @描述:
 */
public class TopAdapter extends BaseQuickAdapter<NoteListModel.DataBean, BaseViewHolder> {

    public TopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteListModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_name, dataBean.getVcCateName());

        baseViewHolder.setText(R.id.tv_status, dataBean.getIntTotalNum() + "");

        Glide.with(getContext())
                .load(Cache.getHttp() + dataBean.getVcIcon())
                .into((ImageView) baseViewHolder.getView(R.id.iv_icon));

        if (dataBean.isSelected()) {
            baseViewHolder.setBackgroundResource(R.id.cl_image, R.drawable.frame_1094f5_radius_8dp);
            baseViewHolder.setTextColor(R.id.tv_name,0xffffffff);
        } else {
            baseViewHolder.setBackgroundResource(R.id.cl_image, R.drawable.frame_border_1094f5_radius_8dp);
            baseViewHolder.setTextColor(R.id.tv_name,0xff333333);
        }
    }
}
