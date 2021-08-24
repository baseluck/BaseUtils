package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 9:38
 * @描述:
 */
public class HomeNewsAdapter extends BaseQuickAdapter<ContentList.DataBean.ListBean, BaseViewHolder> implements LoadMoreModule {
    public HomeNewsAdapter(int layoutResId, @Nullable List<ContentList.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ContentList.DataBean.ListBean homeNewsModel) {
        baseViewHolder.setText(R.id.tv_home_title, homeNewsModel.getVcTitle());
        baseViewHolder.setText(R.id.tv_home_content, homeNewsModel.getTextMark());
        baseViewHolder.setText(R.id.tv_home_time, homeNewsModel.getDtSendTime());
        ImageView iv = baseViewHolder.getView(R.id.iv_home_news);

        Glide.with(iv)
                .load(Cache.getHttp() + homeNewsModel.getVcImportPic())
                .into(iv);
    }
}
