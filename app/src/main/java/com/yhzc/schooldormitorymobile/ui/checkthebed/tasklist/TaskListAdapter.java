package com.yhzc.schooldormitorymobile.ui.checkthebed.tasklist;

import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 15:32
 * @描述:
 */
public class TaskListAdapter extends BaseQuickAdapter<TaskListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    public static final int ONGOING = 2;
    public static final int OVER = 3;

    public TaskListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TaskListModel.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_title, listBean.getVcTitle());
        baseViewHolder.setText(R.id.tv_user, String.format(StringUtils.getString(R.string.check_bed_task_list_user), listBean.getVcManageName()));
        baseViewHolder.setText(R.id.tv_location, String.format(StringUtils.getString(R.string.check_bed_task_list_loaction), listBean.getVcAreaName(), listBean.getVcBuildingName(), listBean.getVcUnitName(),listBean.getVcStoreyName()));
        baseViewHolder.getView(R.id.tv_location).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_date, String.format(StringUtils.getString(R.string.check_bed_task_list_date), listBean.getDtCheckDate()));
        baseViewHolder.setText(R.id.tv_time, String.format(StringUtils.getString(R.string.check_bed_task_list_time), listBean.getDtCheckStartTime(), listBean.getDtCheckEndTime()));

        if (listBean.getIntStruts() == ONGOING) {
            baseViewHolder.setVisible(R.id.tv_ongoing, true);
            baseViewHolder.setVisible(R.id.tv_over, false);
        } else {
            baseViewHolder.setVisible(R.id.tv_ongoing, false);
            baseViewHolder.setVisible(R.id.tv_over, true);

        }
    }
}
