package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yhzc.schooldormitorymobile.R;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/29 10:46
 * @描述: 通知adapter
 */
public class HomeNoticeAdapter extends BannerAdapter<NoticeModel.DataBean, HomeNoticeAdapter.NoticeHolder> {

    public HomeNoticeAdapter(List<NoticeModel.DataBean> datas) {
        super(datas);
    }

    @Override
    public NoticeHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new NoticeHolder(BannerUtils.getView(parent, R.layout.item_home_notice));
    }

    @Override
    public void onBindView(NoticeHolder holder, NoticeModel.DataBean data, int position, int size) {
        holder.message.setText(data.getTitle());
    }

    static class NoticeHolder extends RecyclerView.ViewHolder {
        public TextView message;

        public NoticeHolder(@NonNull View view) {
            super(view);
            message = view.findViewById(R.id.tv_msg);
        }
    }
}
