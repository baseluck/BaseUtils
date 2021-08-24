package com.yhzc.schooldormitorymobile.ui.notetake.take;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityNoteTakeBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.notetake.NoteTakeViewModel;
import com.yhzc.schooldormitorymobile.ui.notetake.list.NoteTypeListModel;
import com.yhzc.schooldormitorymobile.ui.notetake.notetype.NoteTypeActivity;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/22 11:30
 * @描述:
 */
public class NoteTakeActivity extends BaseActivity<NoteTakeViewModel, ActivityNoteTakeBinding> {
    private NoteChooseAdapter mNoteChooseAdapter;
    private TimeAdapter mTimeAdapter;
    private String id;
    private String time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_note_take;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initNoteList();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewModel.initTimeModels();
        initRv();
        initRvTime();
        mViewDataBind.tvSend.setOnClickListener(v -> send());
        mViewDataBind.clDate.setOnClickListener(v -> showTimePickerView());
        showRightTextAndOnClickListener("设置", v -> ActivityUtils.startActivity(NoteTypeActivity.class));
    }

    private void initRvTime() {
        mTimeAdapter = new TimeAdapter(R.layout.item_note_take_time);
        mViewDataBind.rvTime.setLayoutManager(new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mViewDataBind.rvTime.setAdapter(mTimeAdapter);
        mViewModel.getTimeModels().observe(this, timeModels -> mTimeAdapter.setList(timeModels));
        mTimeAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<TimeModel> timeModels = mViewModel.getTimeModels().getValue();
            if (timeModels != null) {
                timeModels.get(position).setSelected(!timeModels.get(position).isSelected());
                mViewModel.setTimeModels(timeModels);
            }
        });
    }

    private void initRv() {
        mNoteChooseAdapter = new NoteChooseAdapter(R.layout.item_note_take_type);
        mViewDataBind.rvType.setLayoutManager(new GridLayoutManager(this, 4));
        mViewDataBind.rvType.setAdapter(mNoteChooseAdapter);
        mViewModel.getNoteListTypeModel().observe(this, noteListModel -> {
            if (noteListModel != null) {
                mNoteChooseAdapter.setList(noteListModel.getData());
            }
        });
        mNoteChooseAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<NoteTypeListModel.DataBean> dataBeans = (List<NoteTypeListModel.DataBean>) adapter.getData();
            if (!dataBeans.get(position).isSelected()) {
                NoteTypeListModel noteListModel = mViewModel.getNoteListTypeModel().getValue();
                if (noteListModel != null) {
                    for (int i = 0; i < noteListModel.getData().size(); i++) {
                        noteListModel.getData().get(i).setSelected(false);
                    }
                    noteListModel.getData().get(position).setSelected(true);
                    if (mViewDataBind.clContent.getVisibility() == View.GONE) {
                        mViewDataBind.clContent.setVisibility(View.VISIBLE);
                    }
                    mViewModel.setNoteListTypeModel(noteListModel);
                    id = noteListModel.getData().get(position).getVcId();
                }
            }
        });
    }

    private void showTimePickerView() {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            String time = TimeUtils.date2String(date, TimeUtils.getSafeDateFormat("yyyy/MM/dd"));
            mViewDataBind.tvMonth.setText(time);
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setTitleText("选择日期")
                .setOutSideCancelable(true)
                .setTitleColor(ColorUtils.getColor(R.color.color333333))
                .setSubmitColor(ColorUtils.getColor(R.color.color549BFF))
                .setCancelColor(ColorUtils.getColor(R.color.color333333))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .build();
        timePickerView.show();
    }

    private void send() {
        if (StringUtils.isEmpty(id)) {
            ToastUtils.showShort("请选择类别");
            return;
        }
        if (StringUtils.isEmpty(mViewDataBind.etNum.getText().toString().trim())) {
            ToastUtils.showShort("数量不能为空");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("vcMemoCateId", id);
        map.put("intNum", mViewDataBind.etNum.getText().toString().trim());
        map.put("textMark", mViewDataBind.etBz.getText().toString().trim());
        if (StringUtils.equals("今天", mViewDataBind.tvMonth.getText().toString().trim())) {
            map.put("dtRecordDay", TimeUtils.string2Millis(TimeUtils.getNowString()) + "");
        } else {
            map.put("dtRecordDay", TimeUtils.string2Millis(mViewDataBind.tvMonth.getText().toString().trim(), "yyyy/MM/dd") + "");
        }
        List<TimeModel> timeModels = mViewModel.getTimeModels().getValue();
        if (timeModels != null && timeModels.size() != 0) {
            List<String> times = new ArrayList<>();
            for (TimeModel timeModel : timeModels) {
                if (timeModel.isSelected()) {
                    times.add(timeModel.getTime());
                }
            }
            if (times.size() != 0) {
                map.put("jsonStr", times);
            }
        }

        OkGoUtils.post(ApiUrl.NOTECREATE, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                finish();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    @Override
    protected int initTitle() {
        return R.string.note_take;
    }
}