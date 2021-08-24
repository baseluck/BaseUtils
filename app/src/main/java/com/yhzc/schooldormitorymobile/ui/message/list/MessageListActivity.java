package com.yhzc.schooldormitorymobile.ui.message.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityMessageListBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.message.MessageViewModel;
import com.yhzc.schooldormitorymobile.ui.message.details.MessageDetailsActivity;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.Utils;

import java.util.HashMap;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 16:20
 * @描述: 消息中心
 */
public class MessageListActivity extends BaseActivity<MessageViewModel, ActivityMessageListBinding> {

    private MessageAdapter mMessageAdapter;
    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        mViewModel.initMessageList(currentPage);

    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        mMessageAdapter = new MessageAdapter(R.layout.item_message_list);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mMessageAdapter);

        mViewDataBind.refresh.setOnRefreshListener(() -> {
            currentPage = 1;
            mViewModel.initMessageList(currentPage);
        });

        Utils.initLoadMore(mMessageAdapter, () -> {
            currentPage++;
            mViewModel.initMessageList(currentPage);
        });

        mViewModel.getMessageListModel().observe(this, messageListModel -> {
            dismissLoading();
            mViewDataBind.refresh.setRefreshing(false);
            if (messageListModel.getData().getPagination().getCurrentPage() == 1) {
                mMessageAdapter.setList(messageListModel.getData().getList());
            } else {
                mMessageAdapter.addData(messageListModel.getData().getList());
            }

            if (messageListModel.getData().getList() != null && messageListModel.getData().getList().size() != 0) {
                mMessageAdapter.getLoadMoreModule().loadMoreComplete();
            } else {
                mMessageAdapter.getLoadMoreModule().loadMoreEnd();
            }
        });

        mMessageAdapter.setOnItemClickListener((adapter, view, position) -> {
            MessageListModel.DataBean.ListBean listBean = (MessageListModel.DataBean.ListBean) adapter.getData().get(position);
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("id", listBean.getId());
            ActivityUtils.startActivity(intent);
        });

        showRightTextAndOnClickListener("全部已读", v -> readAll());


    }

    private void readAll() {
        OkGoUtils.post(ApiUrl.MESSAGEREADALL, new HashMap<>(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                currentPage = 1;
                mViewModel.initMessageList(currentPage);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.message;
    }
}