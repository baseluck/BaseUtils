package com.yhzc.schooldormitorymobile.ui.InSchool;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityInSchoolListBinding;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 15:38
 * @描述:
 */
public class InSchoolListActivity extends BaseActivity<InSchoolViewModel, ActivityInSchoolListBinding> {

    private int currentPage = 1;
    private String time;
    private InSchoolListAdapter mInSchoolListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_school_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initInSchoolList(time, currentPage);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mInSchoolListAdapter = new InSchoolListAdapter(R.layout.item_in_school_list, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mInSchoolListAdapter);


        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initInSchoolList(time, currentPage);
        });

        Utils.initLoadMore(mInSchoolListAdapter, () -> {
            currentPage++;
            mViewModel.initInSchoolList(time, currentPage);
        });


        mViewModel.getInSchoolListModel().observe(this, inSchoolListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (inSchoolListModel != null) {
                mInSchoolListAdapter.setEmptyView(Utils.getEmptyView(this, 0, "无数据", null, null));
                if (inSchoolListModel.getData().getPagination().getCurrentPage() == 1) {
                    mInSchoolListAdapter.setList(inSchoolListModel.getData().getList());
                } else {
                    if (inSchoolListModel.getData().getList() != null && inSchoolListModel.getData().getList().size() != 0) {
                        mInSchoolListAdapter.addData(inSchoolListModel.getData().getList());
                        mInSchoolListAdapter.getLoadMoreModule().loadMoreComplete();
                    } else {
                        mInSchoolListAdapter.getLoadMoreModule().loadMoreEnd();
                    }
                }
            }
        });


        showRightTextAndOnClickListener("选择\n日期", v -> showTimePickerView());
    }

    @Override
    protected int initTitle() {
        return R.string.in_school_list;
    }

    private void showTimePickerView() {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"));
            currentPage = 1;
            mViewModel.initInSchoolList(time, currentPage);
        }).setType(new boolean[]{true, true, true, false, false, false})//分别对应年月日时分秒，默认全部显示
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(ColorUtils.getColor(R.color.color333333))//标题文字颜色
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color333333))//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();

        timePickerView.show();
    }
}
