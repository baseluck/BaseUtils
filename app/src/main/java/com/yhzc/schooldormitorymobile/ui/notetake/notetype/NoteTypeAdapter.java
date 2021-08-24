package com.yhzc.schooldormitorymobile.ui.notetake.notetype;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.notetake.list.NoteTypeListModel;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/23 15:27
 * @描述:
 */
public class NoteTypeAdapter extends BaseQuickAdapter<NoteTypeListModel.DataBean, BaseViewHolder> {

    public NoteTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteTypeListModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_name, dataBean.getVcName());

        Glide.with(getContext())
                .load(Cache.getHttp() + dataBean.getVcIcon())
                .into((ImageView) baseViewHolder.getView(R.id.iv_icon));

        if (dataBean.getIsSys() == 0) {
            baseViewHolder.setText(R.id.tv_type, "（自定义）");
            baseViewHolder.setVisible(R.id.iv_delete, true);
            baseViewHolder.setVisible(R.id.iv_edit, true);
        } else {
            baseViewHolder.setText(R.id.tv_type, "（系统）");
            baseViewHolder.setVisible(R.id.iv_delete, false);
            baseViewHolder.setVisible(R.id.iv_edit, false);
        }
    }
}
