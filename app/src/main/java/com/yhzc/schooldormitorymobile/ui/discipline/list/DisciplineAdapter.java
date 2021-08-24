package com.yhzc.schooldormitorymobile.ui.discipline.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 14:57
 * @描述:
 */
public class DisciplineAdapter extends BaseQuickAdapter<DisciplineListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    public DisciplineAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DisciplineListModel.DataBean.ListBean listBean) {

        baseViewHolder.setText(R.id.tv_title, listBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_hqsy, listBean.getTextContent());
        baseViewHolder.setText(R.id.tv_time, listBean.getDtTime());
    }
}
