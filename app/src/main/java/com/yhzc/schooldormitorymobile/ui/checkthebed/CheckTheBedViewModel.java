package com.yhzc.schooldormitorymobile.ui.checkthebed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.BedroomDetailsModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.choosebedroom.BedroomListModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.locationList.LocationListModel;
import com.yhzc.schooldormitorymobile.ui.checkthebed.tasklist.TaskListModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/9 9:46
 * @描述:
 */
public class CheckTheBedViewModel extends AndroidViewModel {

    private MutableLiveData<TaskListModel> mTaskListModel;
    private MutableLiveData<BedroomListModel> mBedroomListModel;
    private MutableLiveData<BedroomDetailsModel> mBedroomDetailsModel;
    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;
    private MutableLiveData<LocationListModel> mLocationListModel;

    public CheckTheBedViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<LocationListModel> getLocationListModel() {
        if (mLocationListModel == null) {
            mLocationListModel = new MutableLiveData<>();
        }
        return mLocationListModel;
    }

    public void setLocationListModel(LocationListModel data) {
        getLocationListModel().setValue(data);
    }

    public MutableLiveData<TaskListModel> getTaskListModel() {
        if (mTaskListModel == null) {
            mTaskListModel = new MutableLiveData<>();
        }
        return mTaskListModel;
    }

    public void setTaskListModel(TaskListModel taskListModel) {
        getTaskListModel().setValue(taskListModel);
    }

    public MutableLiveData<BedroomListModel> getBedroomListModel() {
        if (mBedroomListModel == null) {
            mBedroomListModel = new MutableLiveData<>();
        }
        return mBedroomListModel;
    }

    public void setBedroomListModel(BedroomListModel bedroomListModel) {
        getBedroomListModel().setValue(bedroomListModel);
    }

    public MutableLiveData<BedroomDetailsModel> getBedroomDetailsModel() {
        if (mBedroomDetailsModel == null) {
            mBedroomDetailsModel = new MutableLiveData<>();
        }
        return mBedroomDetailsModel;
    }

    public void setBedroomDetailsModel(BedroomDetailsModel bedroomDetailsModel) {
        getBedroomDetailsModel().setValue(bedroomDetailsModel);
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
        OkGoUtils.get(ApiUrl.CHECKBEDTASKLIST, map, new BaseCallback() {
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

    public void initBedroomList(String id, String dyid) {
        OkGoUtils.get(ApiUrl.CHECKBEDROOMLIST2 + id + "/" + dyid, new HashMap(), new BaseCallback() {
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

    public void initBedroomStudentList(String id) {
        OkGoUtils.get(ApiUrl.CHECKBEDROOMSTUDENTLIST + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BedroomDetailsModel bedroomDetailsModel = GsonUtils.fromJson(callback, BedroomDetailsModel.class);
                if (bedroomDetailsModel.getData().getList() != null && bedroomDetailsModel.getData().getList().size() != 0) {
                    for (int i = 0; i < bedroomDetailsModel.getData().getList().size(); i++) {
                        if (bedroomDetailsModel.getData().getList().get(i).getIntError() == 1) {
                            bedroomDetailsModel.getData().getList().get(i).setSelected(true);
                        }
                    }
                }
                setBedroomDetailsModel(bedroomDetailsModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setBedroomDetailsModel(null);
            }
        });
    }

    public void initLocationList(String id) {
        OkGoUtils.get(ApiUrl.LOCATIONLIST2 + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setLocationListModel(GsonUtils.fromJson(callback, LocationListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setLocationListModel(null);
            }
        });
    }
    public void initLocationListH(String id) {
        OkGoUtils.get(ApiUrl.LOCATIONLIST + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setLocationListModel(GsonUtils.fromJson(callback, LocationListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setLocationListModel(null);
            }
        });
    }
}
