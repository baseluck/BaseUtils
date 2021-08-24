package com.yhzc.schooldormitorymobile.ui.selectStudents;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivitySelectStudentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 16:43
 * @描述: 选择学生
 */
public class SelectStudentActivity extends BaseActivity<SelectStudentViewModel, ActivitySelectStudentBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_student;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        List<StudentClassModel> studentClassModels = new ArrayList<>();
        studentClassModels.add(new StudentClassModel("全部", true));
        studentClassModels.add(new StudentClassModel("高一371班", false));
        studentClassModels.add(new StudentClassModel("高一372班", false));
        studentClassModels.add(new StudentClassModel("高一373班", false));
        studentClassModels.add(new StudentClassModel("高一374班", false));
        studentClassModels.add(new StudentClassModel("高一375班", false));
        studentClassModels.add(new StudentClassModel("高一376班", false));
        studentClassModels.add(new StudentClassModel("高一377班", false));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        SelectStudentTopAdapter topAdapter = new SelectStudentTopAdapter(R.layout.item_select_student_top, studentClassModels);
        mViewDataBind.rvTop.setLayoutManager(llm);
        mViewDataBind.rvTop.setAdapter(topAdapter);

        topAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<StudentClassModel> data = (List<StudentClassModel>) adapter.getData();
            if (!data.get(position).isSelected()) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelected(false);
                }
                data.get(position).setSelected(true);
                topAdapter.setList(data);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.select_student;
    }
}