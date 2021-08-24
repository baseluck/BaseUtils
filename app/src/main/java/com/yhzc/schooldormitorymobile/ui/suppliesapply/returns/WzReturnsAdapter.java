package com.yhzc.schooldormitorymobile.ui.suppliesapply.returns;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/26 17:52
 * @描述:
 */
public class WzReturnsAdapter extends BaseQuickAdapter<WzReturnsModel, BaseViewHolder> {
    public WzReturnsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WzReturnsModel wzslModel) {
        baseViewHolder.setText(R.id.tv_name, wzslModel.getVcMaterialName());
        baseViewHolder.setText(R.id.tv_gg, wzslModel.getVcSpecification());
        baseViewHolder.setText(R.id.tv_sl, String.valueOf(wzslModel.getVcUnit()));
        baseViewHolder.setText(R.id.tv_num, String.valueOf(wzslModel.getIntNum()));
    }
}
