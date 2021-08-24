package com.yhzc.schooldormitorymobile.ui.InSchool;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 15:47
 * @描述:
 */
public class InSchoolListAdapter extends BaseQuickAdapter<InSchoolListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    public InSchoolListAdapter(int layoutResId, @Nullable List<InSchoolListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InSchoolListModel.DataBean.ListBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, String.format(StringUtils.getString(R.string.in_school_list_title), dataBean.getVcRealName()));
        baseViewHolder.setText(R.id.tv_hqsy, String.format(StringUtils.getString(R.string.in_school_list_class), dataBean.getVcGrade(), dataBean.getVcClass()));
        baseViewHolder.setText(R.id.tv_start_time, String.format(StringUtils.getString(R.string.in_school_list_id), dataBean.getVcNumber()));
        baseViewHolder.setText(R.id.tv_end_time, String.format(StringUtils.getString(R.string.in_school_list_time), dataBean.getDtBackTime()));
        baseViewHolder.setText(R.id.tv_bz, String.format(StringUtils.getString(R.string.in_school_list_bz), StringUtils.isEmpty(dataBean.getTextMark()) ? "无" : dataBean.getTextMark()));

        Glide.with(getContext())
                .load(Cache.getHttp() + dataBean.getVcIdPhoto())
                .into((ImageView) baseViewHolder.getView(R.id.iv_head));
    }
}
