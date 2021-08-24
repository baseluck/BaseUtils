package com.yhzc.schooldormitorymobile.ui.mobileOffice.editor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeOfficeModel;
import com.yhzc.schooldormitorymobile.ui.mobileOffice.MobileModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/2 16:34
 * @描述:
 */
public class EditorViewModel extends AndroidViewModel {

    private MutableLiveData<List<EditorModel>> mNewModel;
    private MutableLiveData<List<EditorModel>> mAllModel;


    public EditorViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<List<EditorModel>> getNewModel() {
        if (mNewModel == null) {
            mNewModel = new MutableLiveData<>();
        }
        return mNewModel;
    }

    public void setNewModel(List<EditorModel> newModel) {
        getNewModel().setValue(newModel);
    }

    public MutableLiveData<List<EditorModel>> getAllModel() {
        if (mAllModel == null) {
            mAllModel = new MutableLiveData<>();
        }
        return mAllModel;
    }

    public void setAllModel(List<EditorModel> allModel) {
        getAllModel().setValue(allModel);
    }


    public void initHomeOffice() {
        OkGoUtils.get(ApiUrl.HOMEOFFICEITEM, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                HomeOfficeModel homeOfficeModel = GsonUtils.fromJson(callback, HomeOfficeModel.class);
                List<EditorModel> newModel = new ArrayList<>();
                for (HomeOfficeModel.DataBean dataBean : homeOfficeModel.getData()) {
                    EditorModel editorModel = new EditorModel();
                    editorModel.setId(dataBean.getId());
                    editorModel.setAppIcon(dataBean.getAppIcon());
                    editorModel.setEnCode(dataBean.getEnCode());
                    editorModel.setFullName(dataBean.getFullName());
                    editorModel.setNew(true);
                    newModel.add(editorModel);
                }
                setNewModel(newModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setNewModel(null);
            }
        });
    }

    public void initData() {
        OkGoUtils.get(ApiUrl.MOBILEOFFICEITEM, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                MobileModel mobileModel = GsonUtils.fromJson(callback, MobileModel.class);
                List<EditorModel> allModel = new ArrayList<>();
                for (MobileModel.DataBean dataBean : mobileModel.getData()) {
                    for (MobileModel.DataBean.ChildrenBean childrenBean : dataBean.getChildren()) {
                        EditorModel editorModel = new EditorModel();
                        editorModel.setId(childrenBean.getId());
                        editorModel.setAppIcon(childrenBean.getAppIcon());
                        editorModel.setEnCode(childrenBean.getEnCode());
                        editorModel.setFullName(childrenBean.getFullName());
                        editorModel.setNew(false);
                        allModel.add(editorModel);
                    }

                }

                setAllModel(allModel);

            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setAllModel(null);
            }
        });
    }
}
