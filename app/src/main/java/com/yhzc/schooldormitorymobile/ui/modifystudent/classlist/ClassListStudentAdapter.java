package com.yhzc.schooldormitorymobile.ui.modifystudent.classlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 15:45
 * @描述:
 */
public class ClassListStudentAdapter extends BaseQuickAdapter<ClassListModel.DataBean.StuListBean, BaseViewHolder> {

    public ClassListStudentAdapter(int layoutResId, @Nullable List<ClassListModel.DataBean.StuListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ClassListModel.DataBean.StuListBean stuListBean) {
        baseViewHolder.setText(R.id.tv_name, stuListBean.getVcName());
    }
}
