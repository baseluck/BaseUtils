package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/28 12:58
 * @描述:
 */
public class SelectImgAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    public SelectImgAdapter(int layoutResId, @Nullable List<File> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, File file) {
        Glide.with(getContext())
                .load(file)
                .error(R.mipmap.ic_img_error)
                .into((ImageView) baseViewHolder.getView(R.id.iv_img));
    }
}
