package com.yhzc.schooldormitorymobile.ui.meeting.initiatelist;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.meeting.list.MeetingListModel;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/13 10:00
 * @描述:
 */
public class MeetingInitiateListAdapter extends BaseQuickAdapter<MeetingListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    public MeetingInitiateListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MeetingListModel.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_title, listBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_user, String.format(StringUtils.getString(R.string.meeting_list_charge_user), listBean.getVcSignUser()));
        baseViewHolder.setText(R.id.tv_location, String.format(StringUtils.getString(R.string.meeting_list_time), listBean.getDtStartTime(), listBean.getDtEndTime()));
        baseViewHolder.setText(R.id.tv_date, String.format(StringUtils.getString(R.string.meeting_list_check_in_time), listBean.getDtSignInStartTime(), listBean.getDtSignInEndTime()));
        baseViewHolder.setText(R.id.tv_time, String.format(StringUtils.getString(R.string.meeting_list_check_out_time), listBean.getDtSignOutStartTime(), listBean.getDtSignOutEndTime()));
//        baseViewHolder.setText(R.id.tv_ongoing, "去签到");
//        baseViewHolder.setVisible(R.id.cl_operate, StringUtils.equals("0", listBean.getCheckType()));
        if (listBean.getBlSend()==0) {
            baseViewHolder.getView(R.id.cl_operate).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.getView(R.id.cl_operate).setVisibility(View.GONE);

        }

    }
}
