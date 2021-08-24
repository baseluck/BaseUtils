package com.yhzc.schooldormitorymobile.ui.checkhealth.choosebedroom;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom.BedroomListModel;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 17:29
 * @描述:
 */
public class BedroomHealthAdapter extends BaseQuickAdapter<BedroomListModel.DataBean.ChildrenBean, BaseViewHolder> {


    public BedroomHealthAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BedroomListModel.DataBean.ChildrenBean childrenBean) {
        baseViewHolder.setText(R.id.tv_room_no, childrenBean.getVcRoomName());
        if (childrenBean.getIntCheckStruts() == 1) {
            baseViewHolder.setText(R.id.tv_status, StringUtils.getString(R.string.await_examine));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_await);
        } else if (childrenBean.getIntCheckStruts() == 2) {
            baseViewHolder.setText(R.id.tv_status, StringUtils.getString(R.string.examine));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_normal);
        } else if (childrenBean.getIntCheckStruts() == 3) {
            baseViewHolder.setText(R.id.tv_status, StringUtils.getString(R.string.un_examine));
            baseViewHolder.setBackgroundResource(R.id.tv_status, R.drawable.check_bed_choose_bed_await);
        }
    }
}
