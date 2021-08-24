package com.yhzc.schooldormitorymobile.ui.outsider;

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
 * @创建日期: 2021/7/17 14:41
 * @描述:
 */
public class OutsiderImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OutsiderImgAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String selectImageModel) {
        Glide.with(getContext())
                .load(Cache.getHttp() + selectImageModel)
                .error(R.mipmap.ic_img_error)
                .into((ImageView) baseViewHolder.getView(R.id.iv_img));
        baseViewHolder.setVisible(R.id.iv_delete, false);

    }
}
