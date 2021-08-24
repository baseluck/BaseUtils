package com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 17:29
 * @描述:
 */
public class BedroomAdapter extends BaseQuickAdapter<BedroomListModel.DataBean.ChildrenBean, BaseViewHolder> {


    public BedroomAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BedroomListModel.DataBean.ChildrenBean childrenBean) {
        baseViewHolder.setText(R.id.tv_room_no, childrenBean.getVcRoomName());
        if (childrenBean.getIntCheckStruts() == 1) {
            baseViewHolder.setText(R.id.tv_status, StringUtils.getString(R.string.await_examine));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_await);
        } else if (childrenBean.getIntCheckStruts() == 2) {
            baseViewHolder.setText(R.id.tv_status, StringUtils.getString(R.string.normal));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_normal);
        } else if (childrenBean.getIntCheckStruts() == 4) {
            baseViewHolder.setText(R.id.tv_status, StringUtils.getString(R.string.un_examine));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_await);
        } else {
            baseViewHolder.setText(R.id.tv_status, String.format(StringUtils.getString(R.string.abnormal_no), childrenBean.getIntErrorNum()));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_abnormal);
        }
    }
}
