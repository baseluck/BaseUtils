package com.yhzc.schooldormitorymobile.ui.suppliesapply;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.checkthebed.checkdetails.SelectImageModel;
import com.yhzc.schooldormitorymobile.ui.leaveList.LeaveListModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.WzListModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.apply.WzslModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returns.WzReturnsModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returnsdetails.ReturnDetailsModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.returnslist.ReturnsListModel;
import com.yhzc.schooldormitorymobile.ui.suppliesapply.rkqrlist.QrListModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/19 15:48
 * @描述:
 */
public class SuppliesApplyViewModel extends AndroidViewModel {

    private MutableLiveData<LeaveListModel> mLeaveListModel;
    private MutableLiveData<WzListModel> mWzListModel;
    private MutableLiveData<List<WzslModel>> mWzslModel;
    private MutableLiveData<List<WzReturnsModel>> mWzReturnsModel;
    private MutableLiveData<ReturnsListModel> mReturnsListModel;
    private MutableLiveData<List<SelectImageModel>> mSelectImageModel;
    private MutableLiveData<QrListModel> mQrListModel;
    private MutableLiveData<ReturnDetailsModel> mReturnDetailsModel;
    private MutableLiveData<SelectUserModel> mSelectUserModel;

    public MutableLiveData<ReturnDetailsModel> getReturnDetailsModel() {
        if (mReturnDetailsModel == null) {
            mReturnDetailsModel = new MutableLiveData<>();
        }
        return mReturnDetailsModel;
    }

    public void setReturnDetailsModel(ReturnDetailsModel data) {
        getReturnDetailsModel().setValue(data);
    }

    public MutableLiveData<QrListModel> getQrListModel() {
        if (mQrListModel == null) {
            mQrListModel = new MutableLiveData<>();
        }
        return mQrListModel;
    }

    public void setQrListModel(QrListModel data) {
        getQrListModel().setValue(data);
    }


    public SuppliesApplyViewModel(@NonNull @NotNull Application application) {
        super(application);
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

    public MutableLiveData<LeaveListModel> getLeaveListModel() {
        if (mLeaveListModel == null) {
            mLeaveListModel = new MutableLiveData<>();
        }
        return mLeaveListModel;
    }

    public void setLeaveListModel(LeaveListModel LeaveListModel) {
        getLeaveListModel().setValue(LeaveListModel);
    }

    public MutableLiveData<WzListModel> getWzListModel() {
        if (mWzListModel == null) {
            mWzListModel = new MutableLiveData<>();
        }
        return mWzListModel;
    }

    public void setWzListModel(WzListModel wzListModel) {
        getWzListModel().setValue(wzListModel);
    }

    public MutableLiveData<List<WzslModel>> getWzslModel() {
        if (mWzslModel == null) {
            mWzslModel = new MutableLiveData<>();
        }
        return mWzslModel;
    }

    public void setWzslModel(List<WzslModel> data) {
        getWzslModel().setValue(data);
    }

    public MutableLiveData<SelectUserModel> getSelectUserModel() {
        if (mSelectUserModel == null) {
            mSelectUserModel = new MutableLiveData<>();
        }
        return mSelectUserModel;
    }

    public void setSelectUserModel(SelectUserModel SelectUserModel) {
        getSelectUserModel().setValue(SelectUserModel);
    }

    public MutableLiveData<List<WzReturnsModel>> getWzReturnsModel() {
        if (mWzReturnsModel == null) {
            mWzReturnsModel = new MutableLiveData<>();
        }
        return mWzReturnsModel;
    }

    public void setWzReturnsModel(List<WzReturnsModel> wzReturnsModel) {
        getWzReturnsModel().setValue(wzReturnsModel);
    }

    public MutableLiveData<ReturnsListModel> getReturnsListModel() {
        if (mReturnsListModel == null) {
            mReturnsListModel = new MutableLiveData<>();
        }
        return mReturnsListModel;
    }

    public void setReturnsListModel(ReturnsListModel data) {
        getReturnsListModel().setValue(data);
    }

    public void initLeaveList(String status, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("intStaus", status);

        OkGoUtils.get(ApiUrl.LEAVEMYLIST + type, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                LeaveListModel leaveListModel = GsonUtils.fromJson(callback, LeaveListModel.class);
                setLeaveListModel(leaveListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    public void initSelectUser() {
        OkGoUtils.get(ApiUrl.SELECTUSER, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SelectUserModel selectUserModel = GsonUtils.fromJson(callback, SelectUserModel.class);
                setSelectUserModel(selectUserModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setSelectUserModel(null);
            }
        });

    }

    public void initWzKc() {
        OkGoUtils.get(ApiUrl.WZKCLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setWzListModel(GsonUtils.fromJson(callback, WzListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setWzListModel(null);
            }
        });
    }

    public void initReturnsList(int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", currentPage + "");

        OkGoUtils.get(ApiUrl.WZKCTHLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setReturnsListModel(GsonUtils.fromJson(callback, ReturnsListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setReturnsListModel(null);
            }
        });
    }

    public void initReturnsDetails(String id) {
        OkGoUtils.get(ApiUrl.WZKCTHDETAILS + id, new HashMap<>(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setReturnDetailsModel(GsonUtils.fromJson(callback, ReturnDetailsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setReturnDetailsModel(null);
            }
        });
    }

    public void initRkqrList(int currentPage, String blQr) {
        Map<String, String> map = new HashMap<>();
        map.put("intType", "1");
        map.put("blQr", blQr);
        map.put("currentPage", currentPage + "");

        OkGoUtils.get(ApiUrl.KCCZLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setQrListModel(GsonUtils.fromJson(callback, QrListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setQrListModel(null);
            }
        });
    }

    public void initCkqrList(int currentPage, String blQr) {
        Map<String, String> map = new HashMap<>();
        map.put("intType", "2");
        map.put("blQr", blQr);
        map.put("currentPage", currentPage + "");

        OkGoUtils.get(ApiUrl.KCCZLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setQrListModel(GsonUtils.fromJson(callback, QrListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setQrListModel(null);
            }
        });
    }
}
