package com.yhzc.schooldormitorymobile.ui.news.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNewsListBinding;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.ContentList;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeNewsAdapter;
import com.yhzc.schooldormitorymobile.ui.news.NewsViewModel;
import com.yhzc.schooldormitorymobile.ui.news.details.NewsDetailsActivity;
import com.luck.basemodule.utils.Utils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 18:02
 * @描述: 新闻列表
 */
public class NewsListActivity extends BaseActivity<NewsViewModel, ActivityNewsListBinding> {

    private HomeNewsAdapter mHomeNewsAdapter;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initContentList(this, currentPage);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRv();
        Utils.initLoadMore(mHomeNewsAdapter, () -> {
            currentPage++;
            mViewModel.initContentList(this, currentPage);
        });
        mViewModel.getContentList().observe(this, contentList -> {
            if (contentList.getData().getPagination().getCurrentPage() == 1) {
                mHomeNewsAdapter.setList(contentList.getData().getList());
            } else {
                mHomeNewsAdapter.addData(contentList.getData().getList());
            }

            if (contentList.getData().getList() != null && contentList.getData().getList().size() != 0) {
                mHomeNewsAdapter.getLoadMoreModule().loadMoreComplete();
            } else {
                mHomeNewsAdapter.getLoadMoreModule().loadMoreEnd();
            }
        });
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initContentList(this, currentPage);
        });
        mHomeNewsAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<ContentList.DataBean.ListBean> data = (List<ContentList.DataBean.ListBean>) adapter.getData();
            Intent intent = new Intent(this, NewsDetailsActivity.class);
            intent.putExtra("id", data.get(position).getVcId());
            ActivityUtils.startActivity(intent);
        });
    }

    private void initRv() {
        mHomeNewsAdapter = new HomeNewsAdapter(R.layout.item_home_news, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mHomeNewsAdapter);
    }

    @Override
    protected int initTitle() {
        return R.string.school_news;
    }
}