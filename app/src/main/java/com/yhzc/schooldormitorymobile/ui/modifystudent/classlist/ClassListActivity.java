package com.yhzc.schooldormitorymobile.ui.modifystudent.classlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityClassListBinding;
import com.yhzc.schooldormitorymobile.ui.modifystudent.ModifyStudentViewModel;
import com.yhzc.schooldormitorymobile.ui.modifystudent.studentdetails.ModifyStudentActivity;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 15:03
 * @描述:
 */
public class ClassListActivity extends BaseActivity<ModifyStudentViewModel, ActivityClassListBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initClassList();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        ClassListAdapter adapter = new ClassListAdapter(R.layout.item_class_list_class);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(adapter);
        mViewDataBind.refresh.setOnRefreshListener(() -> mViewModel.initClassList());
        mViewModel.getClassListModel().observe(this, classListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (classListModel != null) {
                adapter.setList(classListModel.getData());
            }
        });

        adapter.setStudentClick(stuListBean -> {
            Intent intent = new Intent(this, ModifyStudentActivity.class);
            intent.putExtra("id", stuListBean.getVcId());
            ActivityUtils.startActivity(intent);
        });

    }

    @Override
    protected int initTitle() {
        return R.string.bjlb;
    }
}