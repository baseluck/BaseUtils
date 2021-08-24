package com.yhzc.schooldormitorymobile.ui.outsider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/28 10:33
 * @描述:
 */
public class OutsiderViewModel extends AndroidViewModel {

    private MutableLiveData<File> mVisitorPic;
    private MutableLiveData<OutsiderListModel> mOutsiderListModel;
    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;

    public OutsiderViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<File> getVisitorPic() {
        if (mVisitorPic == null) {
            mVisitorPic = new MutableLiveData<>();
        }
        return mVisitorPic;
    }

    public void setVisitorPic(File visitorPic) {
        getVisitorPic().setValue(visitorPic);
    }

    public MutableLiveData<OutsiderListModel> getOutsiderListModel() {
        if (mOutsiderListModel == null) {
            mOutsiderListModel = new MutableLiveData<>();
        }
        return mOutsiderListModel;
    }

    public void setOutsiderListModel(OutsiderListModel OutsiderListModel) {
        getOutsiderListModel().setValue(OutsiderListModel);
    }

    public MutableLiveData<List<SelectImageModel>> getSelectImageModel() {
        if (mSelectImageModel == null) {
            mSelectImageModel = new MutableLiveData<>();
        }
        return mSelectImageModel;
    }

    public void setSelectImageModel(List<SelectImageModel> selectImageModels) {
        getSelectImageModel().setValue(selectImageModels);
    }

    public void initOutsiderList(String date,int currentPage,String vcFkUser) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", String.valueOf(currentPage));

        if (!StringUtils.isEmpty(date)) {
            map.put("date", date);
        }
        if (!StringUtils.isEmpty(vcFkUser)) {
            map.put("vcFkUser", vcFkUser);
        }

        OkGoUtils.get(ApiUrl.OUTSIDERRECORD, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                OutsiderListModel outsiderListModel = GsonUtils.fromJson(callback, OutsiderListModel.class);
                setOutsiderListModel(outsiderListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setOutsiderListModel(null);
            }
        });
    }
}
