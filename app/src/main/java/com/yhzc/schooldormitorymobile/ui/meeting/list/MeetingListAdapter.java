package com.yhzc.schooldormitorymobile.ui.meeting.list;

import android.view.View;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/13 10:00
 * @描述:
 */
public class MeetingListAdapter extends BaseQuickAdapter<MeetingListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    public MeetingListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MeetingListModel.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_title, listBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_user, String.format(StringUtils.getString(R.string.meeting_list_charge_user), listBean.getVcSignUser()));
        baseViewHolder.setText(R.id.tv_location, String.format(StringUtils.getString(R.string.meeting_list_time), listBean.getDtStartTime(), listBean.getDtEndTime()));
        baseViewHolder.setText(R.id.tv_date, String.format(StringUtils.getString(R.string.meeting_list_check_in_time), listBean.getDtSignInStartTime(), listBean.getDtSignInEndTime()));
        baseViewHolder.setText(R.id.tv_time, String.format(StringUtils.getString(R.string.meeting_list_check_out_time), listBean.getDtSignOutStartTime(), listBean.getDtSignOutEndTime()));
        baseViewHolder.setText(R.id.tv_ongoing, "去签到");
        baseViewHolder.setVisible(R.id.tv_ongoing, StringUtils.equals("1", listBean.getCheckType()));

        if (!StringUtils.equals("1", listBean.getCheckType())){
            baseViewHolder.getView(R.id.cl_sign).setVisibility(View.VISIBLE);
            if (StringUtils.equals("0", listBean.getIsSignIn())){
                baseViewHolder.setText(R.id.tv_sign_in, "未签到");
                baseViewHolder.setTextColor(R.id.tv_sign_in, ColorUtils.getColor(R.color.colore7563f));
            }else {
                baseViewHolder.setText(R.id.tv_sign_in, "已签到");
                baseViewHolder.setTextColor(R.id.tv_sign_in, ColorUtils.getColor(R.color.color1094f5));
            }
            if (StringUtils.equals("0", listBean.getIsSignOut())){
                baseViewHolder.setText(R.id.tv_sign_out, "未签退");
                baseViewHolder.setTextColor(R.id.tv_sign_out, ColorUtils.getColor(R.color.colore7563f));
            }else {
                baseViewHolder.setText(R.id.tv_sign_out, "已签退");
                baseViewHolder.setTextColor(R.id.tv_sign_out, ColorUtils.getColor(R.color.color1094f5));
            }
        }else {
            baseViewHolder.getView(R.id.cl_sign).setVisibility(View.GONE);
        }

    }
}
