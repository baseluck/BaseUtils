package com.yhzc.schooldormitorymobile.ui.backbedroom.details;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 18:25
 * @描述:
 */
public class BackBedroomDetailsAdapter extends BaseQuickAdapter<BackBedroomDetailsModel.DataBean.ContentBean, BaseViewHolder> {

    public BackBedroomDetailsAdapter(int layoutResId, @Nullable List<BackBedroomDetailsModel.DataBean.ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BackBedroomDetailsModel.DataBean.ContentBean contentBean) {
        baseViewHolder.setText(R.id.tv_title,contentBean.getType());
        baseViewHolder.setText(R.id.tv_content,contentBean.getDetail());
    }
}
