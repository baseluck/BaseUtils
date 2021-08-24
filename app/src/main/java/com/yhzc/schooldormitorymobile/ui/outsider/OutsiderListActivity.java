package com.yhzc.schooldormitorymobile.ui.outsider;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityOutsiderListBinding;
import com.luck.basemodule.utils.UrlImageLoader;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 15:39
 * @描述:
 */
public class OutsiderListActivity extends BaseActivity<OutsiderViewModel, ActivityOutsiderListBinding> {

    private int currentPage = 1;
    private String time;
    private String vcFkUser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outsider_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initOutsiderList(time, currentPage, vcFkUser);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        ZoomMediaLoader.getInstance().init(new UrlImageLoader(Cache.getHttp()));
        showLoading("正在加载");
        OutSiderAdapter outSiderAdapter = new OutSiderAdapter(R.layout.item_outsider_list, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(outSiderAdapter);
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initOutsiderList(time, currentPage, vcFkUser);
        });
        Utils.initLoadMore(outSiderAdapter, () -> {
            currentPage++;
            mViewModel.initOutsiderList(time, currentPage, vcFkUser);
        });
        mViewDataBind.tvSearch.setOnClickListener(v -> {
            vcFkUser = mViewDataBind.etSearch.getText().toString();
            currentPage = 1;
            mViewModel.initOutsiderList(time, currentPage, vcFkUser);
        });
        mViewModel.getOutsiderListModel().observe(this, outsiderListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (outsiderListModel != null) {
                outSiderAdapter.setEmptyView(Utils.getEmptyView(this, 0, "无数据", null, null));
                if (outsiderListModel.getData().getPagination().getCurrentPage() == 1) {
                    outSiderAdapter.setList(outsiderListModel.getData().getList());
                } else {
                    if (outsiderListModel.getData().getList() != null && outsiderListModel.getData().getList().size() != 0) {
                        outSiderAdapter.addData(outsiderListModel.getData().getList());
                        outSiderAdapter.getLoadMoreModule().loadMoreComplete();
                    } else {
                        outSiderAdapter.getLoadMoreModule().loadMoreEnd();
                    }
                }
            }
        });
        showRightTextAndOnClickListener("选择\n日期", v -> showTimePickerView());
    }

    @Override
    protected int initTitle() {
        return R.string.outsider_list;
    }

    private void showTimePickerView() {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy-MM-dd"));
            currentPage = 1;
            mViewModel.initOutsiderList(time, currentPage, vcFkUser);
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
