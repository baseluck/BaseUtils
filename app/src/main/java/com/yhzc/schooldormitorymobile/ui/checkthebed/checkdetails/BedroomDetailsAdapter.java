package com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/12 15:25
 * @描述:
 */
public class BedroomDetailsAdapter extends BaseQuickAdapter<BedroomDetailsModel.DataBean.ListBean, BaseViewHolder> {

    public BedroomDetailsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BedroomDetailsModel.DataBean.ListBean dataBean) {
        baseViewHolder.setText(R.id.tv_num, dataBean.getVcBedName());
        baseViewHolder.setText(R.id.tv_name, dataBean.getVcStudentName());
        if (dataBean.isSelected()) {
            baseViewHolder.setTextColor(R.id.tv_num, ColorUtils.getColor(R.color.white));
            baseViewHolder.setTextColor(R.id.tv_name, ColorUtils.getColor(R.color.white));
            baseViewHolder.setBackgroundResource(R.id.tv_num, R.drawable.check_bedroom_details_selcted_left);
            baseViewHolder.setBackgroundResource(R.id.tv_name, R.drawable.check_bedroom_details_selcted_right);
        } else {
            baseViewHolder.setTextColor(R.id.tv_num, ColorUtils.getColor(R.color.color333333));
            baseViewHolder.setTextColor(R.id.tv_name, ColorUtils.getColor(R.color.color333333));
            baseViewHolder.setBackgroundResource(R.id.tv_num, R.drawable.check_bedroom_details_unselcted_left);
            baseViewHolder.setBackgroundResource(R.id.tv_name, R.drawable.check_bedroom_details_unselcted_right);
        }

        baseViewHolder.setVisible(R.id.tv_leave, dataBean.getBlInSchool() == 0 && dataBean.getIntCheckin() == 1);

    }
}
