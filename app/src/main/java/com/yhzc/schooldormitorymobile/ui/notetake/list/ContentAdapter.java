package com.yhzc.schooldormitorymobile.ui.notetake.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 16:36
 * @描述:
 */
public class ContentAdapter extends BaseQuickAdapter<NoteListModel.DataBean.DateListBean.RecordListBean, BaseViewHolder> {


    public ContentAdapter(int layoutResId, @Nullable List<NoteListModel.DataBean.DateListBean.RecordListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteListModel.DataBean.DateListBean.RecordListBean recordListBean) {
        baseViewHolder.setText(R.id.tv_time, recordListBean.getDtRecordDay());
        baseViewHolder.setText(R.id.tv_num, recordListBean.getIntNum());
        baseViewHolder.setText(R.id.tv_content, recordListBean.getTextMark());

        int itemPosition = getItemPosition(recordListBean);
        int itemCount = getItemCount();


        baseViewHolder.setVisible(R.id.line, (itemCount - 1) != itemPosition);

    }
}
