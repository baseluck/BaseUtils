package com.yhzc.schooldormitorymobile.ui.suppliesapply.confirm;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/17 14:41
 * @描述:
 */
public class WzImageAdapter extends BaseQuickAdapter<SelectImageModel, BaseViewHolder> {

    public WzImageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SelectImageModel selectImageModel) {
        if (StringUtils.equals("0", selectImageModel.getImgType())) {
            Glide.with(getContext())
                    .load(selectImageModel.getImgUrl())
                    .error(R.mipmap.ic_img_error)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_img));
            baseViewHolder.setVisible(R.id.iv_delete, true);
        } else {
            Glide.with(getContext())
                    .load(Cache.getHttp() + selectImageModel.getImgUrl())
                    .error(R.mipmap.ic_img_error)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_img));
            baseViewHolder.setVisible(R.id.iv_delete, false);
        }

    }
}
