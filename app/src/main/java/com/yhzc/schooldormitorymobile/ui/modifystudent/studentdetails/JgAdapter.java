package com.yhzc.schooldormitorymobile.ui.modifystudent.studentdetails;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/20 10:22
 * @描述:
 */
public class JgAdapter extends BaseQuickAdapter<JgModel.DataBean.ListBean, BaseViewHolder> {

    public JgAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, JgModel.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_address, listBean.getFullName());
    }
}
