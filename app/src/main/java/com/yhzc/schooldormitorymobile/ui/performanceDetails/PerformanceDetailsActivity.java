package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityPerformanceDetailsBinding;
import com.yhzc.schooldormitorymobile.databinding.PerformanceDetailsContentHeaderBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 11:58
 * @描述: 绩效打分详情
 */
public class PerformanceDetailsActivity extends BaseActivity<PerformanceDetailsViewModel, ActivityPerformanceDetailsBinding> {

    private FirstAdapter mFirstAdapter;
    private SecondAdapter mSecondAdapter;
    private ThirdAdapter mThirdAdapter;
    private String plan;
    private PerformanceDetailsContentHeaderBinding mHeaderBinding;
    private int firstPosition = 0;
    private int secondPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_performance_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        showRightTextAndOnClickListener("提交", v -> dialog());
        View headerView = LayoutInflater.from(this).inflate(R.layout.performance_details_content_header, null);
        mHeaderBinding = DataBindingUtil.bind(headerView);
        plan = getIntent().getStringExtra("id");
        mViewModel.initData(plan);
        mFirstAdapter = new FirstAdapter(R.layout.item_khxq_top, null);
        mSecondAdapter = new SecondAdapter(R.layout.item_khxq_left, null);
        mThirdAdapter = new ThirdAdapter(R.layout.item_khxq_content, null);
        mThirdAdapter.addHeaderView(mHeaderBinding.getRoot());
        mViewDataBind.rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mViewDataBind.rvLeft.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvContent.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvTop.setAdapter(mFirstAdapter);
        mViewDataBind.rvLeft.setAdapter(mSecondAdapter);
        mViewDataBind.rvContent.setAdapter(mThirdAdapter);
        mViewModel.getDetailsModel().observe(this, performanceDetailsModel -> {
            dismissLoading();
            if (performanceDetailsModel != null) {
                mViewDataBind.tvTitle.setText(performanceDetailsModel.getData().getTitle());
                mFirstAdapter.setList(performanceDetailsModel.getData().getContent());
                if (performanceDetailsModel.getData().getContent() != null && performanceDetailsModel.getData().getContent().size() != 0) {
                    for (DetailsModel.DataBean.ContentBean firstTitle : performanceDetailsModel.getData().getContent()) {
                        if (firstTitle.isSelect()) {
                            mSecondAdapter.setList(firstTitle.getFirstList());
                            for (DetailsModel.DataBean.ContentBean.FirstListBean secondTitle : firstTitle.getFirstList()) {
                                if (secondTitle.isSelect()) {
                                    mThirdAdapter.setList(secondTitle.getSecondList());
                                    mHeaderBinding.tvHeader.setText(secondTitle.getSecondTitle());
                                }
                            }
                        }
                    }
                }
            }
        });
        mThirdAdapter.setMyWatcher(new ThirdAdapter.MyWatcher() {
            @Override
            public void zpChanged(CharSequence zp, int position, String id, String score) {
                DetailsModel detailsModel = mViewModel.getDetailsModel().getValue();
                if (detailsModel != null) {
                    detailsModel.getData().getContent().get(firstPosition).getFirstList().get(secondPosition).getSecondList().get(position).setTextZp(zp.toString().trim());
                }
            }

            @Override
            public void numChanged(CharSequence num, int position, String id, String score) {
                DetailsModel detailsModel = mViewModel.getDetailsModel().getValue();
                if (detailsModel != null) {
                    detailsModel.getData().getContent().get(firstPosition).getFirstList().get(secondPosition).getSecondList().get(position).setIntSize(num.toString().trim());
                }
            }
        });
        mViewDataBind.tvSend.setOnClickListener(v -> send());
        mFirstAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<DetailsModel.DataBean.ContentBean> data = (List<DetailsModel.DataBean.ContentBean>) adapter.getData();
            if (!data.get(position).isSelect()) {
                DetailsModel detailsModel = mViewModel.getDetailsModel().getValue();
                if (detailsModel != null) {
                    for (int i = 0; i < detailsModel.getData().getContent().size(); i++) {
                        detailsModel.getData().getContent().get(i).setSelect(false);
                    }
                    detailsModel.getData().getContent().get(position).setSelect(true);
                    if (detailsModel.getData().getContent().get(position).getFirstList() != null && detailsModel.getData().getContent().get(position).getFirstList().size() != 0) {
                        boolean isSelect = true;
                        for (DetailsModel.DataBean.ContentBean.FirstListBean firstListBean : detailsModel.getData().getContent().get(position).getFirstList()) {
                            if (firstListBean.isSelect()) {
                                isSelect = false;
                                break;
                            }
                        }
                        if (isSelect) {
                            detailsModel.getData().getContent().get(position).getFirstList().get(0).setSelect(true);
                        }
                    }
                }
                firstPosition = position;
                secondPosition = 0;
                mViewModel.setDetailsModel(detailsModel);
            }
        });
        mSecondAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<DetailsModel.DataBean.ContentBean.FirstListBean> data = (List<DetailsModel.DataBean.ContentBean.FirstListBean>) adapter.getData();
            if (!data.get(position).isSelect()) {
                DetailsModel detailsModel = mViewModel.getDetailsModel().getValue();
                if (detailsModel != null) {
                    for (int i = 0; i < detailsModel.getData().getContent().size(); i++) {
                        if (detailsModel.getData().getContent().get(i).isSelect()) {
                            for (int j = 0; j < detailsModel.getData().getContent().get(i).getFirstList().size(); j++) {
                                detailsModel.getData().getContent().get(i).getFirstList().get(j).setSelect(false);
                            }
                            detailsModel.getData().getContent().get(i).getFirstList().get(position).setSelect(true);
                        }
                    }
                }
                secondPosition = position;
                mViewModel.setDetailsModel(detailsModel);
            }
        });
    }

    private void update() {
        DetailsModel detailsModel = mViewModel.getDetailsModel().getValue();
        if (detailsModel != null) {
            UpdateBean updateBean = new UpdateBean();
            updateBean.setPlanId(plan);
            updateBean.setIsSubmit("1");
            List<UpdateBean.ContentBean> contentBeans = new ArrayList<>();
            for (DetailsModel.DataBean.ContentBean contentBean : detailsModel.getData().getContent()) {
                for (DetailsModel.DataBean.ContentBean.FirstListBean firstListBean : contentBean.getFirstList()) {
                    for (DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean secondListBean : firstListBean.getSecondList()) {
                        UpdateBean.ContentBean content = new UpdateBean.ContentBean();
                        content.setVcXzId(secondListBean.getVcXzId());
                        content.setDlZpScore(secondListBean.getDlItemScore() + "");
                        content.setTextZp(secondListBean.getTextZp());
                        content.setIntSize(secondListBean.getIntSize());
                        contentBeans.add(content);
                    }
                }
            }
            updateBean.setContent(contentBeans);
            mViewModel.update(GsonUtils.toJson(updateBean));
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(StringUtils.getString(R.string.tjhsjwfxg));
        builder.setTitle("提示");
        builder.setPositiveButton("确认", (dialog, which) -> {
            update();
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void send() {
        DetailsModel detailsModel = mViewModel.getDetailsModel().getValue();
        if (detailsModel != null) {
            UpdateBean updateBean = new UpdateBean();
            updateBean.setPlanId(plan);
            updateBean.setIsSubmit("0");
            List<UpdateBean.ContentBean> contentBeans = new ArrayList<>();
            for (DetailsModel.DataBean.ContentBean contentBean : detailsModel.getData().getContent()) {
                for (DetailsModel.DataBean.ContentBean.FirstListBean firstListBean : contentBean.getFirstList()) {
                    for (DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean secondListBean : firstListBean.getSecondList()) {
                        UpdateBean.ContentBean content = new UpdateBean.ContentBean();
                        content.setVcXzId(secondListBean.getVcXzId());
                        content.setDlZpScore(secondListBean.getDlItemScore() + "");
                        content.setTextZp(secondListBean.getTextZp());
                        content.setIntSize(secondListBean.getIntSize());
                        contentBeans.add(content);
                    }
                }
            }
            updateBean.setContent(contentBeans);
            mViewModel.update(GsonUtils.toJson(updateBean));
        }
    }


    @Override
    protected int initTitle() {
        return R.string.performance;
    }
}