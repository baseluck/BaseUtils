package com.yhzc.schooldormitorymobile.ui.selectStudents;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 16:51
 * @描述:
 */
public class SelectStudentTopAdapter extends BaseQuickAdapter<StudentClassModel, BaseViewHolder> {
    public SelectStudentTopAdapter(int layoutResId, @Nullable List<StudentClassModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StudentClassModel studentClassModel) {
        baseViewHolder.setText(R.id.tv_class,studentClassModel.getClassName());
        if (studentClassModel.isSelected()){
            baseViewHolder.setBackgroundResource(R.id.tv_class,R.drawable.frame_1094f5_radius_8dp);
            baseViewHolder.setTextColor(R.id.tv_class, Color.WHITE);
        }else {
            baseViewHolder.setBackgroundResource(R.id.tv_class,R.drawable.frame_line_999999_radius_5dp);
            baseViewHolder.setTextColor(R.id.tv_class,0xff333333);
        }
    }
}
