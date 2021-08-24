package com.yhzc.schooldormitorymobile.ui.mobileOffice;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 14:46
 * @描述:
 */
public class MobileOfficeAdapter extends BaseQuickAdapter<MobileModel.DataBean, BaseViewHolder> {

    private ItemAppClick mItemAppClick;

    public void setItemAppClick(ItemAppClick itemAppClick) {
        this.mItemAppClick = itemAppClick;
    }

    public MobileOfficeAdapter(int layoutResId, @Nullable List<MobileModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MobileModel.DataBean mobileOfficeModel) {
        baseViewHolder.setText(R.id.tv_title, mobileOfficeModel.getFullName());
        RecyclerView rvItem = baseViewHolder.getView(R.id.rv_item);
        rvItem.setLayoutManager(new GridLayoutManager(getContext(), 4));
        MobileAppAdapter appAdapter = new MobileAppAdapter(R.layout.item_home_app, mobileOfficeModel.getChildren());
        rvItem.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<MobileModel.DataBean.ChildrenBean> data = (List<MobileModel.DataBean.ChildrenBean>) adapter.getData();
            mItemAppClick.ItemAppClickListener(data.get(position).getEnCode(), position);
        });
    }


    interface ItemAppClick {
        /**
         * item点击事件
         *
         * @param title    点击标题
         * @param position 点击position
         */
        void ItemAppClickListener(String title, int position);
    }
}
