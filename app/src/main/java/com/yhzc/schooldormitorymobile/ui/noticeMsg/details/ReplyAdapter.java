package com.yhzc.schooldormitorymobile.ui.noticeMsg.details;

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
 * @创建日期: 2021/5/22 21:58
 * @描述:
 */
public class ReplyAdapter extends BaseQuickAdapter<NoticeDetailsModel.DataBean.ReplyListBean, BaseViewHolder> {

    private ImageClick mImageClick;

    public void setImageClick(ImageClick imageClick) {
        mImageClick = imageClick;
    }

    public ReplyAdapter(int layoutResId, @Nullable List<NoticeDetailsModel.DataBean.ReplyListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoticeDetailsModel.DataBean.ReplyListBean replyListBean) {
        baseViewHolder.setText(R.id.tv_name, replyListBean.getUserName());
        baseViewHolder.setText(R.id.tv_time, replyListBean.getCreateTime());
        baseViewHolder.setText(R.id.tv_content, replyListBean.getContent());
        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_hf_img);
        ImgHfAdapter mImgShowAdapter = new ImgHfAdapter(R.layout.item_hf_img, replyListBean.getJsonPic());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(mImgShowAdapter);
        mImgShowAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<String> data = (List<String>) adapter.getData();
            mImageClick.imageClick(data, position);
        });

    }

    interface ImageClick {
        void imageClick(List<String> urls, int position);
    }

}
