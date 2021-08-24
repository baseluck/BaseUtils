package com.yhzc.schooldormitorymobile.ui.home;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeOfficeModel;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 17:15
 * @描述: 首页应用适配器
 */
public class HomeAppAdapter extends BaseQuickAdapter<HomeOfficeModel.DataBean, BaseViewHolder> {

    public HomeAppAdapter(int layoutResId, @Nullable List<HomeOfficeModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeOfficeModel.DataBean homeItemApp) {
        baseViewHolder.setText(R.id.tv_home_app, homeItemApp.getFullName());
        Glide.with(getContext())
                .load(Cache.getHttp() + homeItemApp.getAppIcon())
                .into((ImageView) baseViewHolder.getView(R.id.iv_home_app));

        baseViewHolder.setVisible(R.id.v_new_msg, homeItemApp.isBlMessage());
    }
}
