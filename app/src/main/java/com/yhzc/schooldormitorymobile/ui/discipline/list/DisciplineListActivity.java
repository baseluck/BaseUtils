package com.yhzc.schooldormitorymobile.ui.discipline.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityDisciplineListBinding;
import com.yhzc.schooldormitorymobile.ui.discipline.DisciplineViewModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.DisciplineDetailsActivity;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 14:48
 * @描述: 违纪列表
 */
public class DisciplineListActivity extends BaseActivity<DisciplineViewModel, ActivityDisciplineListBinding> {

    private int currentPage = 1;
    private DisciplineAdapter mDisciplineAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discipline_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initList(currentPage);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        initRv();

        showRightTextAndOnClickListener("新增", v -> {
            Intent intent = new Intent(this, DisciplineDetailsActivity.class);
            intent.putExtra("wjType", "2");
            ActivityUtils.startActivity(intent);
        });
    }

    private void initRv() {
        mDisciplineAdapter = new DisciplineAdapter(R.layout.item_discipline_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mDisciplineAdapter);

        mDisciplineAdapter.setOnItemClickListener((adapter, view, position) -> {
            DisciplineListModel.DataBean.ListBean listBean = (DisciplineListModel.DataBean.ListBean) adapter.getData().get(position);
            Intent intent = new Intent(this, DisciplineDetailsActivity.class);
            intent.putExtra("wjType", "1");
            intent.putExtra("id", listBean.getVcId());
            ActivityUtils.startActivity(intent);
        });

        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initList(currentPage);
        });

        Utils.initLoadMore(mDisciplineAdapter, () -> {
            currentPage++;
            mViewModel.initList(currentPage);
        });

        mViewModel.getDisciplineListModel().observe(this, disciplineListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (disciplineListModel == null) {
                mDisciplineAdapter.setEmptyView(Utils.getEmptyView(this, R.mipmap.ic_error, "请求失败", null, null));
            } else {
                if (disciplineListModel.getData().getPagination().getCurrentPage() == 1) {
                    if (disciplineListModel.getData().getList().size() == 0) {
                        mDisciplineAdapter.setEmptyView(Utils.getEmptyView(this, 0, "当前数据列表为空", null, v -> {
                            currentPage = 1;
                            mViewModel.initList(currentPage);
                        }));
                    }
                    mDisciplineAdapter.setList(disciplineListModel.getData().getList());
                } else {
                    mDisciplineAdapter.addData(disciplineListModel.getData().getList());
                }
                if (disciplineListModel.getData().getList() != null && disciplineListModel.getData().getList().size() != 0) {
                    mDisciplineAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    mDisciplineAdapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.discipline_list;
    }
}