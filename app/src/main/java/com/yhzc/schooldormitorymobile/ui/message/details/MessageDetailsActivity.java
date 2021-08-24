package com.yhzc.schooldormitorymobile.ui.message.details;

import android.os.Bundle;

import com.blankj.utilcode.util.TimeUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityMessageDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.message.MessageViewModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 10:33
 * @描述: 消息详情
 */
public class MessageDetailsActivity extends BaseActivity<MessageViewModel, ActivityMessageDetailsBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        showLoading("正在加载");
        String id = getIntent().getStringExtra("id");
        mViewModel.initMessageDetails(id);

        mViewModel.getMessageDetailsModel().observe(this, messageDetailsModel -> {
            dismissLoading();
            if (messageDetailsModel != null) {
                mViewDataBind.tvTitle.setText(messageDetailsModel.getData().getTitle());
                String string = TimeUtils.millis2String(messageDetailsModel.getData().getLastModifyTime()) + "\t\t" + messageDetailsModel.getData().getCreatorUser();
                mViewDataBind.tvUser.setText(string);
                mViewDataBind.wvMsgDetails.setVerticalScrollBarEnabled(false);
                mViewDataBind.wvMsgDetails.setHorizontalScrollBarEnabled(false);

                mViewDataBind.wvMsgDetails.clearCache(true);
                mViewDataBind.wvMsgDetails.loadData(messageDetailsModel.getData().getBodyText(), "text/html;charset=utf-8", null);
            }
        });

    }

    @Override
    protected int initTitle() {
        return R.string.message_details;
    }
}