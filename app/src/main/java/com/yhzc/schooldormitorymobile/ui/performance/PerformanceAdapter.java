package com.yhzc.schooldormitorymobile.ui.performance;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/7 16:46
 * @描述:
 */
public class PerformanceAdapter extends BaseQuickAdapter<PerforManceModel.DataBean, BaseViewHolder> {

    private ItemClick mItemClick;

    public void setItemClick(ItemClick itemClick) {
        mItemClick = itemClick;
    }

    public PerformanceAdapter(int layoutResId, @Nullable List<PerforManceModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PerforManceModel.DataBean perforManceModel) {
        baseViewHolder.setText(R.id.tv_title, perforManceModel.getSchoolYear());
        PerformanceItemAdapter performanceItemAdapter = new PerformanceItemAdapter(R.layout.item_performance_item, perforManceModel.getAssessmentList());
        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(performanceItemAdapter);
        performanceItemAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<PerforManceModel.DataBean.AssessmentListBean> data = (List<PerforManceModel.DataBean.AssessmentListBean>) adapter.getData();
            mItemClick.onItemClick(data, position);
        });
    }

    interface ItemClick {
        /**
         * 点击事件
         *
         * @param data
         * @param postion
         */
        void onItemClick(List<PerforManceModel.DataBean.AssessmentListBean> data, int postion);
    }
}
