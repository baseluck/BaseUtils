package com.yhzc.schooldormitorymobile.ui.notetake.details;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityNoteDetailsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.notetake.NoteTakeViewModel;
import com.yhzc.schooldormitorymobile.ui.notetake.take.TimeAdapter;
import com.yhzc.schooldormitorymobile.ui.notetake.take.TimeModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/23 10:02
 * @描述: 记录详情
 */
public class NoteDetailsActivity extends BaseActivity<NoteTakeViewModel, ActivityNoteDetailsBinding> {

    private String id;
    private String mCateName;
    private TimeAdapter mTimeAdapter;

    private String cateId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_note_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        mViewModel.initTimeModels();
        id = getIntent().getStringExtra("id");
        mCateName = getIntent().getStringExtra("mCateName");
        initRvTime();
        initData(id);
        mViewDataBind.tvEditor.setOnClickListener(v -> mViewDataBind.clContent.setVisibility(View.VISIBLE));
        mViewDataBind.ivDismiss.setOnClickListener(v -> mViewDataBind.clContent.setVisibility(View.GONE));
        mViewDataBind.tvSend.setOnClickListener(v -> send());
        mViewDataBind.tvDelete.setOnClickListener(v -> showDeleteDialog());
        mViewDataBind.clDate.setOnClickListener(v -> showTimePickerView());
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

    protected void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(StringUtils.getString(R.string.qrsc));
        builder.setTitle("提示");
        builder.setPositiveButton("确认", (dialog, which) -> {
            delete();
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
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

    private void initData(String id) {
        OkGoUtils.get(ApiUrl.NOTEDETAILS + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                NoteDetailsModel noteDetailsModel = GsonUtils.fromJson(callback, NoteDetailsModel.class);
                setData(noteDetailsModel);
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });
    }

    private void setData(NoteDetailsModel noteDetailsModel) {
        cateId = noteDetailsModel.getData().getVcMemoCateId();
        mViewDataBind.tvLb.setText(mCateName);
        mViewDataBind.tvSl.setText(String.valueOf(noteDetailsModel.getData().getIntNum()));
        mViewDataBind.tvJlsq.setText(TimeUtils.millis2String(noteDetailsModel.getData().getDtRecordDay(), TimeUtils.getSafeDateFormat("yyyy-MM-dd")));
        mViewDataBind.tvCjrq.setText(TimeUtils.millis2String(noteDetailsModel.getData().getDtCreateTime()));
        List<String> time = noteDetailsModel.getData().getJsonStr();
        if (time.size() != 0) {
            StringBuilder str2 = new StringBuilder();
            for (Iterator<String> iterator = time.iterator(); iterator.hasNext();) {
                String string = iterator.next();
                str2.append(string);
                if (iterator.hasNext()) {
                    str2.append(",");
                }
            }
            String string = str2.toString();
            mViewDataBind.tvCjsj.setText(string);
        }
        mViewDataBind.tvMyBz.setText(noteDetailsModel.getData().getTextMark());
        mViewDataBind.etNum.setText(String.valueOf(noteDetailsModel.getData().getIntNum()));
        mViewDataBind.etBz.setText(noteDetailsModel.getData().getTextMark());
        mViewDataBind.tvMonth.setText(TimeUtils.millis2String(noteDetailsModel.getData().getDtRecordDay(), TimeUtils.getSafeDateFormat("yyyy-MM-dd")));
        List<TimeModel> timeModels = mViewModel.getTimeModels().getValue();
        if (timeModels != null && timeModels.size() != 0) {
            for (int i = 0; i < timeModels.size(); i++) {
                for (String myTime : noteDetailsModel.getData().getJsonStr()) {
                    if (StringUtils.equals(myTime, timeModels.get(i).getTime())) {
                        timeModels.get(i).setSelected(true);
                    }
                }
            }
            mTimeAdapter.setList(timeModels);
        }

    }

    private void send() {
        Map<String, Object> map = new HashMap<>();
        map.put("vcMemoCateId", cateId);
        map.put("intNum", mViewDataBind.etNum.getText().toString().trim());
        map.put("textMark", mViewDataBind.etBz.getText().toString().trim());
        map.put("dtRecordDay", TimeUtils.string2Millis(mViewDataBind.tvMonth.getText().toString().trim(), "yyyy-MM-dd") + "");
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
        OkGoUtils.put(ApiUrl.NOTEDETAILS + id, map, new BaseCallback() {
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

    private void delete() {
        OkGoUtils.delete(ApiUrl.NOTEDETAILS + id, new HashMap(), new BaseCallback() {
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
        return R.string.note_details;
    }
}