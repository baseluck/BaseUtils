package com.yhzc.schooldormitorymobile.ui.discipline.details;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/28 12:31
 * @描述:
 */
public class SelectStudentAdapter extends BaseQuickAdapter<SelectStudentModel, BaseViewHolder> {
    public SelectStudentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SelectStudentModel userModel) {
        baseViewHolder.setText(R.id.tv_notice_user, userModel.getName());
        baseViewHolder.setVisible(R.id.tv_delete, userModel.isCanChange());
    }
}
