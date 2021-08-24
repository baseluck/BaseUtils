package com.yhzc.schooldormitorymobile.ui.noticeMsg.list;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 16:17
 * @描述:
 */
public class NoticeListAdapter extends BaseQuickAdapter<NoticeListModel.DataBean, BaseViewHolder> implements LoadMoreModule {

    public NoticeListAdapter(int layoutResId, @Nullable List<NoticeListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoticeListModel.DataBean noticeListModel) {
        baseViewHolder.setText(R.id.tv_title, noticeListModel.getVcTitle());
        baseViewHolder.setText(R.id.tv_content,noticeListModel.getCreateTime());
        baseViewHolder.setVisible(R.id.v_new_msg, StringUtils.equals(noticeListModel.getStatus(), "未读" ));
    }
}
