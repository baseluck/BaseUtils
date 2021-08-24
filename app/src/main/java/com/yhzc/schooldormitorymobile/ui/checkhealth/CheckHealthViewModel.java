package com.yhzc.schooldormitorymobile.ui.checkhealth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkhealth.details.HealthDetailsModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom.BedroomListModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.tasklist.TaskListModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/16 9:51
 * @描述:
 */
public class CheckHealthViewModel extends AndroidViewModel {
    private MutableLiveData<TaskListModel> mTaskListModel;
    private MutableLiveData<BedroomListModel> mBedroomListModel;
    private MutableLiveData<HealthDetailsModel> mHealthDetailsModel;
    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;

    public CheckHealthViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<TaskListModel> getTaskListModel() {
        if (mTaskListModel == null) {
            mTaskListModel = new MutableLiveData<>();
        }
        return mTaskListModel;
    }

    public void setTaskListModel(TaskListModel TaskListModel) {
        getTaskListModel().setValue(TaskListModel);
    }

    public MutableLiveData<BedroomListModel> getBedroomListModel() {
        if (mBedroomListModel == null) {
            mBedroomListModel = new MutableLiveData<>();
        }
        return mBedroomListModel;
    }

    public void setBedroomListModel(BedroomListModel BedroomListModel) {
        getBedroomListModel().setValue(BedroomListModel);
    }

    public MutableLiveData<HealthDetailsModel> getHealthDetailsModel() {
        if (mHealthDetailsModel == null) {
            mHealthDetailsModel = new MutableLiveData<>();
        }
        return mHealthDetailsModel;
    }

    public void setHealthDetailsModel(HealthDetailsModel HealthDetailsModel) {
        getHealthDetailsModel().setValue(HealthDetailsModel);
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

    public void initTaskList(int currentPage, String status, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", currentPage + "");
        map.put("intStruts", status);
        map.put("intType", type);
        OkGoUtils.get(ApiUrl.CHECKHEALTHTASKLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                TaskListModel taskListModel = GsonUtils.fromJson(callback, TaskListModel.class);
                setTaskListModel(taskListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setTaskListModel(null);
            }
        });
    }

    public void initBedroomList(String id) {
        OkGoUtils.get(ApiUrl.CHECKHEALTHROOMLIST + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BedroomListModel bedroomListModel = GsonUtils.fromJson(callback, BedroomListModel.class);
                if (bedroomListModel.getData() != null && bedroomListModel.getData().size() != 0) {
                    bedroomListModel.getData().get(0).setSelected(true);
                }
                setBedroomListModel(bedroomListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setBedroomListModel(null);
            }
        });
    }

    public void initBedroomList2(String id, String lcid) {
        OkGoUtils.get(ApiUrl.CHECKHEALTHROOMLIST2 + id + "/" + lcid, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BedroomListModel bedroomListModel = GsonUtils.fromJson(callback, BedroomListModel.class);
                if (bedroomListModel.getData() != null && bedroomListModel.getData().size() != 0) {
                    bedroomListModel.getData().get(0).setSelected(true);
                }
                setBedroomListModel(bedroomListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setBedroomListModel(null);
            }
        });
    }

    public void initHealthDetails(String id) {
        OkGoUtils.get(ApiUrl.CHECKHEALTHROOMDETAILS + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                HealthDetailsModel healthDetailsModel = GsonUtils.fromJson(callback, HealthDetailsModel.class);
                if (healthDetailsModel.getData().getList() != null && healthDetailsModel.getData().getList().size() != 0) {
                    healthDetailsModel.getData().getList().get(0).setSelect(true);
                }
                setHealthDetailsModel(healthDetailsModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setHealthDetailsModel(null);
            }
        });
    }


}
