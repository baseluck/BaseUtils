package com.yhzc.schooldormitorymobile.ui.noticeMsg.details;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 15:58
 * @描述:
 */
public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        Glide.with(getContext())
                .load(Cache.getHttp() + s)
                .error(R.mipmap.ic_img_error)
                .into((ImageView) baseViewHolder.getView(R.id.iv_notice));
    }
}
