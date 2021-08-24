package com.yhzc.schooldormitorymobile.ui.backbedroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.backbedroom.checkin.BackroomDetailsModel;
import com.yhzc.schooldormitorymobile.ui.backbedroom.details.BackBedroomDetailsModel;
import com.yhzc.schooldormitorymobile.ui.backbedroom.list.BackBedroomModel;
import com.yhzc.schooldormitorymobile.ui.backbedroom.send.StudentModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.ImageModel;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 15:43
 * @描述:
 */
public class BackBedroomViewModel extends AndroidViewModel {

    private MutableLiveData<List<ContentItemModel>> mContentItemModels;
    private MutableLiveData<BackBedroomModel> mBackBedroomModel;
    private MutableLiveData<BackBedroomDetailsModel> mBackBedroomDetailsModel;
    private MutableLiveData<BackroomDetailsModel> mBackroomDetailsModel;
    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;
    private MutableLiveData<StudentModel> mStudentModel;

    public MutableLiveData<StudentModel> getStudentModel() {
        if (mStudentModel == null) {
            mStudentModel = new MutableLiveData<>();
        }
        return mStudentModel;
    }

    public void setStudentModel(StudentModel data) {
        getStudentModel().setValue(data);
    }


    private MutableLiveData<List<ImageModel>> mImages;

    public BackBedroomViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ContentItemModel>> getContentItemModels() {
        if (mContentItemModels == null) {
            mContentItemModels = new MutableLiveData<>();
        }
        return mContentItemModels;
    }

    public void setContentItemModels(List<ContentItemModel> contentItemModels) {
        getContentItemModels().setValue(contentItemModels);
    }


    public MutableLiveData<BackBedroomModel> getBackBedroomModel() {
        if (mBackBedroomModel == null) {
            mBackBedroomModel = new MutableLiveData<>();
        }
        return mBackBedroomModel;
    }

    public void setBackBedroomModel(BackBedroomModel BackBedroomModel) {
        getBackBedroomModel().setValue(BackBedroomModel);
    }

    public MutableLiveData<BackBedroomDetailsModel> getBackBedroomDetailsModel() {
        if (mBackBedroomDetailsModel == null) {
            mBackBedroomDetailsModel = new MutableLiveData<>();
        }
        return mBackBedroomDetailsModel;
    }

    public void setBackBedroomDetailsModel(BackBedroomDetailsModel BackBedroomDetailsModel) {
        getBackBedroomDetailsModel().setValue(BackBedroomDetailsModel);
    }

    public MutableLiveData<List<ImageModel>> getImages() {
        if (mImages == null) {
            mImages = new MutableLiveData<>();
        }
        return mImages;
    }

    public MutableLiveData<BackroomDetailsModel> getBackroomDetailsModel() {
        if (mBackroomDetailsModel == null) {
            mBackroomDetailsModel = new MutableLiveData<>();
        }
        return mBackroomDetailsModel;
    }

    public void setBackroomDetailsModel(BackroomDetailsModel data) {
        getBackroomDetailsModel().setValue(data);
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

    public void setImages(List<ImageModel> images) {
        getImages().setValue(images);
    }

    public void initList(int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", String.valueOf(currentPage));
        OkGoUtils.get(ApiUrl.BACKBEDROOMLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setBackBedroomModel(GsonUtils.fromJson(callback, BackBedroomModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setBackBedroomModel(null);
            }
        });
    }

    public void initBackData(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("idCardNo", id);
        OkGoUtils.get(ApiUrl.BACKBEDROOMLAST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setBackroomDetailsModel(GsonUtils.fromJson(callback, BackroomDetailsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setBackroomDetailsModel(null);
            }
        });
    }

    public void initModel() {
        String json = Utils.getJson(R.raw.back_bedroom);
        BackBedroomModel backBedroomModel = GsonUtils.fromJson(json, BackBedroomModel.class);
        setBackBedroomModel(backBedroomModel);
    }

    public void initDetails() {
        BackBedroomDetailsModel backBedroomDetailsModel = GsonUtils.fromJson(Utils.getJson(R.raw.back_room_details), BackBedroomDetailsModel.class);
        setBackBedroomDetailsModel(backBedroomDetailsModel);
    }

    public void getStudent(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("idCardNo", id);

        OkGoUtils.get(ApiUrl.STUDENTINFO2, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setStudentModel(GsonUtils.fromJson(callback, StudentModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setStudentModel(null);
            }
        });
    }
}
