package com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
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
public class ImageAdapter extends BaseQuickAdapter<SelectImageModel, BaseViewHolder> {

    public ImageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SelectImageModel selectImageModel) {
        if (StringUtils.equals("0", selectImageModel.getImgType())) {
            Glide.with(getContext())
                    .load(selectImageModel.imgUrl)
                    .error(R.mipmap.ic_img_error)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_img));
        } else {
            Glide.with(getContext())
                    .load(Cache.getHttp() + selectImageModel.imgUrl)
                    .error(R.mipmap.ic_img_error)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_img));
        }

    }
}
