package com.yhzc.schooldormitorymobile.ui.discipline.details;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/17 14:41
 * @描述:
 */
public class ImageWjAdapter extends BaseQuickAdapter<ImageModel, BaseViewHolder> {

    public ImageWjAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ImageModel imageModel) {
        if (imageModel.getType() == 0) {
            Glide.with(getContext())
                    .load(imageModel.getPath())
                    .error(R.mipmap.ic_img_error)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_img));
        } else {
            Glide.with(getContext())
                    .load(Cache.getHttp() + imageModel.getPath())
                    .error(R.mipmap.ic_img_error)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_img));
        }

        baseViewHolder.setVisible(R.id.iv_delete, imageModel.isCanChange());

    }
}
