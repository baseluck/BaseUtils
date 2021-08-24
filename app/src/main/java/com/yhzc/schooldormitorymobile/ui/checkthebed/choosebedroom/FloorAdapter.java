package com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 17:28
 * @描述:
 */
public class FloorAdapter extends BaseQuickAdapter<BedroomListModel.DataBean, BaseViewHolder> {

    private static final int ZERO = 0;

    public FloorAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BedroomListModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_floor, dataBean.getVcStoreyName());
        if (dataBean.isSelected()) {
            baseViewHolder.setTextColor(R.id.tv_floor, ColorUtils.getColor(R.color.white));
            if (getItemPosition(dataBean) == ZERO) {
                baseViewHolder.setBackgroundResource(R.id.tv_floor, R.drawable.check_bed_selected_floor);
            } else {
                baseViewHolder.setBackgroundColor(R.id.tv_floor, 0xff1094F5);
            }

        } else {
            baseViewHolder.setTextColor(R.id.tv_floor, 0xff666666);
            baseViewHolder.setBackgroundColor(R.id.tv_floor, 0x00000000);
        }
    }
}
