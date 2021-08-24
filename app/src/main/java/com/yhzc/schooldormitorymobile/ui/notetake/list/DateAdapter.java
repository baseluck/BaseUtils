package com.yhzc.schooldormitorymobile.ui.notetake.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 16:36
 * @描述:
 */
public class DateAdapter extends BaseQuickAdapter<NoteListModel.DataBean.DateListBean, BaseViewHolder> {

    private ItemClick mItemClick;

    public void setItemClick(ItemClick itemClick) {
        mItemClick = itemClick;
    }

    public DateAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteListModel.DataBean.DateListBean dateListBean) {
        baseViewHolder.setText(R.id.tv_title, dateListBean.getRecordDay());
        baseViewHolder.setText(R.id.tv_num, String.format(StringUtils.getString(R.string.the_number_all), dateListBean.getTotalNum()));
        ContentAdapter contentAdapter = new ContentAdapter(R.layout.item_note_list_item, dateListBean.getRecordList());
        RecyclerView rv = baseViewHolder.getView(R.id.rv_item);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(contentAdapter);

        contentAdapter.setOnItemClickListener((adapter, view, position) -> {
            NoteListModel.DataBean.DateListBean.RecordListBean recordListBean = (NoteListModel.DataBean.DateListBean.RecordListBean) adapter.getData().get(position);
            mItemClick.itemClick(recordListBean);
        });
    }

    interface ItemClick {
        void itemClick(NoteListModel.DataBean.DateListBean.RecordListBean recordListBean);
    }
}
