package com.yhzc.schooldormitorymobile.ui.checkhealth.choosebedroom;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityChooseBedBinding;
import com.yhzc.schooldormitorymobile.ui.checkhealth.CheckHealthViewModel;
import com.yhzc.schooldormitorymobile.ui.checkhealth.details.CheckHealthDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom.BedroomListModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom.FloorAdapter;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 16:34
 * @描述: 选择宿舍
 */
public class ChooseHealthRoomActivity extends BaseActivity<CheckHealthViewModel, ActivityChooseBedBinding> {

    private FloorAdapter mFloorAdapter;
    private BedroomHealthAdapter mBedroomAdapter;
    private String id;
    private String lcid;
    private String status;
    private int selectFloorPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_bed;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(id)&&!StringUtils.isEmpty(lcid)) {
            mViewModel.initBedroomList2(id,lcid);
        }
    }
    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        id = getIntent().getStringExtra("id");
        lcid = getIntent().getStringExtra("lcid");
        status = getIntent().getStringExtra("status");
        mViewModel.initBedroomList2(id,lcid);
        mFloorAdapter = new FloorAdapter(R.layout.item_choose_bed_choose_floor);
        mBedroomAdapter = new BedroomHealthAdapter(R.layout.item_choose_bedroom);
        mViewDataBind.rvFloor.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvBedroom.setLayoutManager(new GridLayoutManager(this, 2));
        mViewDataBind.rvFloor.setAdapter(mFloorAdapter);
        mViewDataBind.rvBedroom.setAdapter(mBedroomAdapter);
        mViewModel.getBedroomListModel().observe(this, bedroomListModel -> {
            dismissLoading();
            if (bedroomListModel != null) {
                if (selectFloorPosition != 0) {
                    if (!bedroomListModel.getData().get(selectFloorPosition).isSelected()) {
                        for (int i = 0; i < bedroomListModel.getData().size(); i++) {
                            bedroomListModel.getData().get(i).setSelected(false);
                        }
                        bedroomListModel.getData().get(selectFloorPosition).setSelected(true);
                        mViewModel.setBedroomListModel(bedroomListModel);
                    }
                }
                mFloorAdapter.setList(bedroomListModel.getData());
                if (bedroomListModel.getData() != null && bedroomListModel.getData().size() != 0) {
                    for (BedroomListModel.DataBean dataBean : bedroomListModel.getData()) {
                        if (dataBean.isSelected()) {
                            mBedroomAdapter.setList(dataBean.getChildren());
                            return;
                        }
                    }
                }
            }
        });
        mFloorAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<BedroomListModel.DataBean> dataBeans = (List<BedroomListModel.DataBean>) adapter.getData();
            BedroomListModel bedroomListModel = mViewModel.getBedroomListModel().getValue();
            if (bedroomListModel != null) {
                if (!bedroomListModel.getData().get(position).isSelected()) {
                    for (int i = 0; i < dataBeans.size(); i++) {
                        bedroomListModel.getData().get(i).setSelected(false);
                    }
                    bedroomListModel.getData().get(position).setSelected(true);
                    selectFloorPosition = position;
                    mViewModel.setBedroomListModel(bedroomListModel);
                }
            }
        });
        mBedroomAdapter.setOnItemClickListener((adapter, view, position) -> {
            BedroomListModel.DataBean.ChildrenBean childrenBean = (BedroomListModel.DataBean.ChildrenBean) adapter.getData().get(position);
            Intent intent = new Intent(this, CheckHealthDetailsActivity.class);
            intent.putExtra("id", childrenBean.getVcId());
            intent.putExtra("title", childrenBean.getVcTitle() + "-" + childrenBean.getVcRoomName());
            intent.putExtra("status", status);
            ActivityUtils.startActivity(intent);
            mViewModel.initHealthDetails(childrenBean.getVcId());
        });
    }
    @Override
    protected int initTitle() {
        return R.string.check_bed_choose_bedroom;
    }
}