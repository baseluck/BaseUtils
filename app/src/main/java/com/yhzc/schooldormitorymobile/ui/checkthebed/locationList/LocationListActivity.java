package com.yhzc.schooldormitorymobile.ui.checkthebed.locationList;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityDisciplineListBinding;
import com.yhzc.schooldormitorymobile.ui.checkthebed.CheckTheBedViewModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom.ChooseBedActivity;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/17 14:42
 * @描述:
 */
public class LocationListActivity extends BaseActivity<CheckTheBedViewModel, ActivityDisciplineListBinding> {

    private LocationListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discipline_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mAdapter = new LocationListAdapter(R.layout.item_location_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mAdapter);
        String id = getIntent().getStringExtra("id");
        String status = getIntent().getStringExtra("status");
        mViewModel.initLocationList(id);
        mViewModel.getLocationListModel().observe(this, locationListModel -> {
            mViewDataBind.refresh.setRefreshing(false);
            if (locationListModel != null) {
                mAdapter.setList(locationListModel.getData());
            }
        });

        mViewDataBind.refresh.setOnRefreshListener(() -> mViewModel.initLocationList(id));


        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            List<LocationListModel.DataBean> data = (List<LocationListModel.DataBean>) adapter.getData();
            Intent intent = new Intent(this, ChooseBedActivity.class);
            intent.putExtra("dyid", data.get(position).getDyid());
            intent.putExtra("id", id);
            intent.putExtra("status", status);
            ActivityUtils.startActivity(intent);
        });

    }

    @Override
    protected int initTitle() {
        return R.string.jianchaweizhi;
    }
}
