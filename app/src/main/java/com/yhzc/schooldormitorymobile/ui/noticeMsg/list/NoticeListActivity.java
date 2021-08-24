package com.yhzc.schooldormitorymobile.ui.noticeMsg.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNewsListBinding;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.NoticeViewModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.details.NoticeDetailsActivity;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 15:18
 * @描述:
 */
public class NoticeListActivity extends BaseActivity<NoticeViewModel, ActivityNewsListBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initNoticeList(this);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        NoticeListAdapter adapter = new NoticeListAdapter(R.layout.item_notice_list, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(adapter);
        mViewModel.getNoticeList().observe(this, noticeListModel -> {
            if (noticeListModel != null) {
                adapter.setList(noticeListModel.getData());
            }
        });
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            mViewModel.initNoticeList(this);
        });
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<NoticeListModel.DataBean> data = (List<NoticeListModel.DataBean>) adapter1.getData();
            Intent intent=new Intent(this, NoticeDetailsActivity.class);
            intent.putExtra("id",data.get(position).getVcId());
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected int initTitle() {
        return R.string.tzlb;
    }
}
