package com.yhzc.schooldormitorymobile.ui.notetake.notetype;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityNoteTypeBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.notetake.NoteTakeViewModel;
import com.yhzc.schooldormitorymobile.ui.notetake.list.NoteTypeListModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/23 14:27
 * @描述: 随手记分类设置
 */
public class NoteTypeActivity extends BaseActivity<NoteTakeViewModel, ActivityNoteTypeBinding> {

    private NoteTypeAdapter mNoteTypeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_note_type;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewModel.initNoteList();
        initRv();
        showRightTextAndOnClickListener("新增", v -> showCreateDialog());
    }

    private void initRv() {
        mNoteTypeAdapter = new NoteTypeAdapter(R.layout.item_note_type);
        mViewDataBind.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rv.setAdapter(mNoteTypeAdapter);
        mViewModel.getNoteListTypeModel().observe(this, noteTypeListModel -> {
            if (noteTypeListModel != null) {
                mNoteTypeAdapter.setList(noteTypeListModel.getData());
            }
        });
        mNoteTypeAdapter.addChildClickViewIds(R.id.iv_delete, R.id.iv_edit);
        mNoteTypeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            NoteTypeListModel.DataBean dataBean = (NoteTypeListModel.DataBean) adapter.getData().get(position);
            if (view.getId() == R.id.iv_delete) {
                delete(dataBean.getVcId());
            } else if (view.getId() == R.id.iv_edit) {
                changeCreateDialog(dataBean.getVcName(), dataBean.getVcId());
            }
        });
    }

    private void delete(String id) {
        OkGoUtils.delete(ApiUrl.NOTECATEEDIT + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                mViewModel.initNoteList();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }


    private void showCreateDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_note_type, null);
        EditText etName = dialogView.findViewById(R.id.et_name);
        TextView tvSend = dialogView.findViewById(R.id.tv_send);
        Dialog dialog = new Dialog(this, R.style.ShareDialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tvSend.setOnClickListener(v -> {
            if (StringUtils.isEmpty(etName.getText().toString().trim())) {
                ToastUtils.showShort("名字不能为空");
                return;
            }
            create(etName.getText().toString().trim(), dialog);
        });
    }

    private void changeCreateDialog(String name, String id) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_note_type, null);
        EditText etName = dialogView.findViewById(R.id.et_name);
        etName.setText(name);
        TextView tvSend = dialogView.findViewById(R.id.tv_send);
        Dialog dialog = new Dialog(this, R.style.ShareDialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tvSend.setOnClickListener(v -> {
            if (StringUtils.isEmpty(etName.getText().toString().trim())) {
                ToastUtils.showShort("名字不能为空");
                return;
            }
            change(etName.getText().toString().trim(), id, dialog);
        });
    }

    private void change(String name, String id, Dialog dialog) {
        Map<String, String> map = new HashMap<>();
        map.put("vcName", name);
        OkGoUtils.put(ApiUrl.NOTECATEEDIT + id, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                dialog.dismiss();
                mViewModel.initNoteList();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    private void create(String name, Dialog dialog) {
        Map<String, String> map = new HashMap<>();
        map.put("vcName", name);
        OkGoUtils.post(ApiUrl.NOTECATECREATE, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                dialog.dismiss();
                mViewModel.initNoteList();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.note_type;
    }
}