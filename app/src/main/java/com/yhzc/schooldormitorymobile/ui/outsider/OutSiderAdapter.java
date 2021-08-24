package com.yhzc.schooldormitorymobile.ui.outsider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.luck.basemodule.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/7 16:29
 * @描述:
 */
public class OutSiderAdapter extends BaseQuickAdapter<OutsiderListModel.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {


    public OutSiderAdapter(int layoutResId, @Nullable List<OutsiderListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OutsiderListModel.DataBean.ListBean dataBean) {
        baseViewHolder.setText(R.id.tv_title, String.format(StringUtils.getString(R.string.outsider_list_title), dataBean.getVcFkUser()));
        baseViewHolder.setText(R.id.tv_hqsy, String.format(StringUtils.getString(R.string.outsider_list_phone), dataBean.getVcPhone()));
        baseViewHolder.setText(R.id.tv_start_time, String.format(StringUtils.getString(R.string.outsider_list_start_time), dataBean.getDtTime()));
        baseViewHolder.setText(R.id.tv_end_time, String.format(StringUtils.getString(R.string.outsider_list_out_time), dataBean.getDtLeaveTime()));
        baseViewHolder.setText(R.id.tv_user, String.format(StringUtils.getString(R.string.outsider_list_user), StringUtils.isEmpty(dataBean.getVcContactUser()) ? "无" : dataBean.getVcContactUser()));
        baseViewHolder.setText(R.id.tv_car, String.format(StringUtils.getString(R.string.outsider_list_car), StringUtils.isEmpty(dataBean.getVcCarNo()) ? "无" : dataBean.getVcCarNo()));
        baseViewHolder.setText(R.id.tv_reason, String.format(StringUtils.getString(R.string.outsider_list_reason), dataBean.getVcReason()));
        OutsiderImgAdapter adapter = new OutsiderImgAdapter(R.layout.item_hf_img, dataBean.getVcImg());
        RecyclerView rv = baseViewHolder.getView(R.id.rv_img);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<String> data = (List<String>) adapter1.getData();
            Utils.showBigImg(data, position);

        });
    }
}
