package com.yhzc.schooldormitorymobile.ui.noticeMsg.newList;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNewsListBinding;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.NoticeViewModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.details.NoticeDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SendNoticeActivity;
import com.luck.basemodule.utils.Utils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 15:18
 * @描述:
 */
public class NoticeNewListActivity extends BaseActivity<NoticeViewModel, ActivityNewsListBinding> {
    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initMyNoticeList(this, currentPage);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        NoticeNewAdapter adapter = new NoticeNewAdapter(R.layout.item_notice_list, null);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(adapter);
        Utils.initLoadMore(adapter, () -> {
            currentPage++;
            mViewModel.initMyNoticeList(this, currentPage);
        });
        mViewModel.getNoticeNewModel().observe(this, noticeNewModel -> {
            if (noticeNewModel != null) {
                if (noticeNewModel.getData().getPagination().getCurrentPage() == 1) {
                    adapter.setList(noticeNewModel.getData().getList());
                } else {
                    adapter.addData(noticeNewModel.getData().getList());
                }

                if (noticeNewModel.getData().getList() != null && noticeNewModel.getData().getList().size() != 0) {
                    adapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    adapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initMyNoticeList(this, currentPage);
        });
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<NoticeNewModel.DataBean.ListBean> data = (List<NoticeNewModel.DataBean.ListBean>) adapter1.getData();
            Intent intent = new Intent(this, NoticeDetailsActivity.class);
            intent.putExtra("id", data.get(position).getVcId());
            ActivityUtils.startActivity(intent);
        });
        showRightTextAndOnClickListener("发布", v -> ActivityUtils.startActivity(SendNoticeActivity.class));
    }

    @Override
    protected int initTitle() {
        return R.string.yfbtzlb;
    }
}
