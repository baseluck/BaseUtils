package com.yhzc.schooldormitorymobile.ui.notetake.take;

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
 * @创建日期: 2021/7/22 14:53
 * @描述:
 */
public class NoteChooseAdapter extends BaseQuickAdapter<NoteTypeListModel.DataBean, BaseViewHolder> {

    public NoteChooseAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoteTypeListModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_name, dataBean.getVcName());
        Glide.with(getContext())
                .load(Cache.getHttp() + dataBean.getVcIcon())
                .into((ImageView) baseViewHolder.getView(R.id.iv_icon));

        if (dataBean.isSelected()) {
            baseViewHolder.setBackgroundResource(R.id.v_bg, R.drawable.note_selected_round);
        } else {
            baseViewHolder.setBackgroundResource(R.id.v_bg, R.drawable.note_un_selected_round);
        }
    }
}
