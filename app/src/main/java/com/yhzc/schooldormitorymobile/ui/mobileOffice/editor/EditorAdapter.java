package com.yhzc.schooldormitorymobile.ui.mobileOffice.editor;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/2 16:29
 * @描述:
 */
public class EditorAdapter extends BaseQuickAdapter<EditorModel, BaseViewHolder> {

    public EditorAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, EditorModel editorModel) {
        baseViewHolder.setText(R.id.tv_name, editorModel.getFullName());
        Glide.with(getContext())
                .load(Cache.getHttp() + editorModel.getAppIcon())
                .into((ImageView) baseViewHolder.getView(R.id.iv_home_app));

        baseViewHolder.setText(R.id.tv_editor, "添加");
        baseViewHolder.setTextColor(R.id.tv_editor, 0xff1094f5);

    }
}
