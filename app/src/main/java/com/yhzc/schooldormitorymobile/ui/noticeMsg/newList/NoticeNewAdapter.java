package com.yhzc.schooldormitorymobile.ui.noticeMsg.newList;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/31 18:49
 * @描述:
 */
public class NoticeNewAdapter extends BaseQuickAdapter<NoticeNewModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {
    public NoticeNewAdapter(int layoutResId, @Nullable List<NoticeNewModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoticeNewModel.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_title, listBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_content,listBean.getDtSendTime());
    }
}
