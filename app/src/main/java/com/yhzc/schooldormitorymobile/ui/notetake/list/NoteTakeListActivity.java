package com.yhzc.schooldormitorymobile.ui.notetake.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNoteTakeListBinding;
import com.yhzc.schooldormitorymobile.ui.notetake.NoteTakeViewModel;
import com.yhzc.schooldormitorymobile.ui.notetake.details.NoteDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.notetake.take.NoteTakeActivity;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.Utils;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/21 16:58
 * @描述: 随手记
 */
public class NoteTakeListActivity extends BaseActivity<NoteTakeViewModel, ActivityNoteTakeListBinding> {

    private TopAdapter mTopAdapter;
    private DateAdapter mDateAdapter;
    private String mCateName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_note_take_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initNoteListHome();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRvDate();
        initRvTop();
        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy/MM/dd"));
        mViewDataBind.tvMonth.setText(nowString);
        Glide.with(this)
                .load(Cache.getHttp() + Cache.getLogin().getData().getVcHeadPic())
                .placeholder(R.mipmap.ic_head)
                .into(mViewDataBind.ivHead);
        mViewDataBind.tvName.setText(Cache.getLogin().getData().getVcRealName());
        mViewDataBind.tvBm.setText(Cache.getLogin().getData().getVcPostion());
        showRightTextAndOnClickListener("添加", v -> ActivityUtils.startActivity(NoteTakeActivity.class));
    }

    private void initRvDate() {
        mDateAdapter = new DateAdapter(R.layout.item_note_list_date);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mDateAdapter);
        mDateAdapter.setEmptyView(Utils.getEmptyView(this, 0, "无数据", null, null));
        mDateAdapter.setItemClick(recordListBean -> {
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra("id", recordListBean.getVcId());
            intent.putExtra("mCateName", mCateName);
            ActivityUtils.startActivity(intent);
        });
    }

    private void initRvTop() {
        mTopAdapter = new TopAdapter(R.layout.item_note_take_type_h);
        mViewDataBind.rvItem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mViewDataBind.rvItem.setAdapter(mTopAdapter);
        mViewModel.getNoteListModel().observe(this, noteListModel -> {
            if (noteListModel != null) {
                mTopAdapter.setList(noteListModel.getData());
                for (NoteListModel.DataBean dateBean : noteListModel.getData()) {
                    if (dateBean.isSelected()) {
                        mCateName = dateBean.getVcCateName();
                        mDateAdapter.setList(dateBean.getDateList());
                    }
                }
            }
        });
        mTopAdapter.setOnItemClickListener((adapter, view, position) -> {
            NoteListModel noteListModel = mViewModel.getNoteListModel().getValue();
            if (noteListModel != null && !noteListModel.getData().get(position).isSelected()) {
                for (int i = 0; i < noteListModel.getData().size(); i++) {
                    noteListModel.getData().get(i).setSelected(false);
                }
                noteListModel.getData().get(position).setSelected(true);
                mViewModel.setNoteListModel(noteListModel);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.note_list;
    }
}