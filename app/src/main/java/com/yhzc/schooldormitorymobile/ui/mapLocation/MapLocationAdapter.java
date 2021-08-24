package com.yhzc.schooldormitorymobile.ui.mapLocation;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @描述:
 * @创建日期: 2021/4/14 17:20
 * @author: ProcyonLotor
 */
public class MapLocationAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> implements LoadMoreModule {

    public MapLocationAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PoiItem poiItem) {
        baseViewHolder.setText(R.id.tv_name, poiItem.getTitle());
        baseViewHolder.setText(R.id.tv_address, poiItem.getSnippet());
    }
}
