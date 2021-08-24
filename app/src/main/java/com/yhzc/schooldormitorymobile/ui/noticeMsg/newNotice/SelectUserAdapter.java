package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/28 12:31
 * @描述:
 */
public class SelectUserAdapter extends BaseQuickAdapter<UserModel, BaseViewHolder> {
    public SelectUserAdapter(int layoutResId, @Nullable List<UserModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UserModel userModel) {
        baseViewHolder.setText(R.id.tv_notice_user, userModel.getUserName());
    }
}
