package com.yhzc.schooldormitorymobile.ui.mobileOffice.editor;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityEditorOfficeBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/2 16:22
 * @描述: 移动办公编辑
 */
public class EditorOfficeActivity extends BaseActivity<EditorViewModel, ActivityEditorOfficeBinding> {

    private EditorNewAdapter mNewAdapter;
    private EditorAdapter mAllAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_office;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewModel.initData();
        mViewModel.initHomeOffice();

        initNew();
        initAll();

        showRightTextAndOnClickListener("保存", v -> update());
    }

    private void update() {
        List<EditorModel> editorModels = mViewModel.getNewModel().getValue();
        if (editorModels == null) {
            editorModels = new ArrayList<>();
        }

        List<String> ids = new ArrayList<>();
        for (EditorModel editorModel : editorModels) {
            ids.add(editorModel.getId());
        }


        Map<String, Object> map = new HashMap<>();
        map.put("usuallyModules", ids);

        OkGoUtils.post(ApiUrl.UPDATEOFFICEITEM, map, new BaseCallback() {
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

    private void initAll() {
        mAllAdapter = new EditorAdapter(R.layout.item_editor_office);
        mViewDataBind.rvAll.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvAll.setAdapter(mAllAdapter);
        mViewModel.getAllModel().observe(this, editorModels -> {
            if (editorModels != null) {
                mAllAdapter.setList(editorModels);
            }
        });

        mAllAdapter.setOnItemClickListener((adapter, view, position) -> {
            EditorModel editorModel = (EditorModel) adapter.getData().get(position);
            List<EditorModel> editorModels = mViewModel.getNewModel().getValue();
            if (editorModels == null) {
                editorModels = new ArrayList<>();
                editorModels.add(editorModel);
            } else {
                if (editorModels.size() < 4) {
                    boolean has = false;
                    for (EditorModel editor : editorModels) {
                        if (StringUtils.equals(editor.getId(), editorModel.getId())) {
                            has = true;
                        }
                    }

                    if (!has) {
                        editorModels.add(editorModel);
                    }
                } else {
                    ToastUtils.showShort("最多只能选择四个");
                }
            }
            mViewModel.setNewModel(editorModels);
        });

    }

    private void initNew() {
        mNewAdapter = new EditorNewAdapter(R.layout.item_editor_office);
        mViewDataBind.rvCy.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvCy.setAdapter(mNewAdapter);
        mViewModel.getNewModel().observe(this, editorModels -> {
            if (editorModels != null) {
                mNewAdapter.setList(editorModels);
            }
        });

        mNewAdapter.setOnItemClickListener((adapter, view, position) -> {
            EditorModel editorModel = (EditorModel) adapter.getData().get(position);
            List<EditorModel> editorModels = mViewModel.getNewModel().getValue();
            if (editorModels != null) {
                editorModels.remove(editorModel);
                mViewModel.setNewModel(editorModels);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.office_editor;
    }
}