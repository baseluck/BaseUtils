package com.yhzc.schooldormitorymobile.ui.mobileOffice;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityMobileOfficeBinding;
import com.yhzc.schooldormitorymobile.ui.home.HomeAppAdapter;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeOfficeModel;
import com.yhzc.schooldormitorymobile.ui.mobileOffice.editor.EditorOfficeActivity;
import com.yhzc.schooldormitorymobile.utils.StartActivityUtils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 14:43
 * @描述: 移动办公
 */
public class MobileOfficeActivity extends BaseActivity<MobileOfficeViewModel, ActivityMobileOfficeBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mobile_office;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initNewNotice(this);
        mViewModel.initData();
        mViewModel.initHomeOffice();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRv();
        initApp();
        showRightTextAndOnClickListener("编辑", v -> ActivityUtils.startActivity(EditorOfficeActivity.class));
    }

    private void initApp() {
        mViewDataBind.rvItem.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        HomeAppAdapter appAdapter = new HomeAppAdapter(R.layout.item_home_app, null);
        mViewDataBind.rvItem.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<HomeOfficeModel.DataBean> data = (List<HomeOfficeModel.DataBean>) adapter.getData();

            StartActivityUtils.startActivity(this, data.get(position).getEnCode());
        });

        mViewModel.getHomeOfficeModel().observe(this, homeModel -> {
            if (homeModel != null) {
                appAdapter.setList(homeModel.getData());
            }
        });

    }

    private void initRv() {

        MobileOfficeAdapter adapter = new MobileOfficeAdapter(R.layout.item_mobile_office, null);
        mViewDataBind.refresh.setOnRefreshListener(() -> mViewDataBind.refresh.setRefreshing(false));
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(adapter);

        mViewModel.getMobileModel().observe(this, mobileModel -> {
            if (mobileModel != null) {
                adapter.setList(mobileModel.getData());
            }
        });

        mViewModel.getNewNotice().observe(this, aBoolean -> mViewModel.getData(aBoolean));

        adapter.setItemAppClick((title, position) -> {

            StartActivityUtils.startActivity(this, title);
        });

    }

    @Override
    protected int initTitle() {
        return R.string.mobile_office;
    }


}