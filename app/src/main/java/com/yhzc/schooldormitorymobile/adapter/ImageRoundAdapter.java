package com.yhzc.schooldormitorymobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.BannerModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 11:23
 * @描述: 轮播图
 */
public class ImageRoundAdapter extends BannerAdapter<BannerModel.DataBean.ListBean, ImageRoundAdapter.BannerViewHolder> {

    public ImageRoundAdapter(List<BannerModel.DataBean.ListBean> mDatas) {
        super(mDatas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerModel.DataBean.ListBean data, int position, int size) {
        Glide.with(holder.imageView)
                .load(Cache.getHttp() + data.getVcPicPath())
                .into(holder.imageView);
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public BannerViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_banner);
        }
    }

}
