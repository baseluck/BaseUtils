package com.yhzc.schooldormitorymobile.ui.message.list;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 16:16
 * @描述:
 */
public class MessageAdapter extends BaseQuickAdapter<MessageListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    public MessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageListModel.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_title, listBean.getTitle());
        baseViewHolder.setText(R.id.tv_user, listBean.getCreatorUser());

        boolean isToday = TimeUtils.isToday(listBean.getLastModifyTime());
        if (isToday) {
            baseViewHolder.setText(R.id.tv_time, TimeUtils.millis2String(listBean.getLastModifyTime(), TimeUtils.getSafeDateFormat("HH:mm")));
        } else {
            baseViewHolder.setText(R.id.tv_time, TimeUtils.millis2String(listBean.getLastModifyTime(), TimeUtils.getSafeDateFormat("yyyy-MM-dd")));

        }

        baseViewHolder.setVisible(R.id.v_unread, listBean.getIsRead() != 1);

    }
}
