package com.yhzc.schooldormitorymobile.ui.noticeMsg;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.details.NoticeDetailsModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.list.NoticeListModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newList.NoticeNewModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectUserModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UserModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 15:17
 * @描述:
 */
public class NoticeViewModel extends AndroidViewModel {

    private MutableLiveData<NoticeListModel> mNoticeList;
    private MutableLiveData<NoticeDetailsModel> mNoticeDetailsModel;
    private MutableLiveData<SelectUserModel> mSelectUserModel;
    private MutableLiveData<List<UserModel>> mUserModels;
    private MutableLiveData<List<File>> mImages;
    private MutableLiveData<String> mVideoPath;

    private MutableLiveData<NoticeNewModel> mNoticeNewModel;


    public NoticeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<NoticeListModel> getNoticeList() {
        if (mNoticeList == null) {
            mNoticeList = new MutableLiveData<>();
        }
        return mNoticeList;
    }

    public void setNoticeList(NoticeListModel noticeList) {
        getNoticeList().setValue(noticeList);
    }


    public MutableLiveData<NoticeNewModel> getNoticeNewModel() {
        if (mNoticeNewModel == null) {
            mNoticeNewModel = new MutableLiveData<>();
        }
        return mNoticeNewModel;
    }

    public void setNoticeNewModel(NoticeNewModel NoticeNewModel) {
        getNoticeNewModel().setValue(NoticeNewModel);
    }

    public MutableLiveData<NoticeDetailsModel> getNoticeDetailsModel() {
        if (mNoticeDetailsModel == null) {
            mNoticeDetailsModel = new MutableLiveData<>();
        }
        return mNoticeDetailsModel;
    }


    public void setNoticeDetailsModel(NoticeDetailsModel NoticeDetailsModel) {
        getNoticeDetailsModel().setValue(NoticeDetailsModel);
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

    public MutableLiveData<List<UserModel>> getUserModels() {
        if (mUserModels == null) {
            mUserModels = new MutableLiveData<>();
        }
        return mUserModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        getUserModels().setValue(userModels);
    }

    public MutableLiveData<List<File>> getImages() {
        if (mImages == null) {
            mImages = new MutableLiveData<>();
        }
        return mImages;
    }

    public void setImages(List<File> images) {
        getImages().setValue(images);
    }

    public MutableLiveData<String> getVideoPath() {
        if (mVideoPath == null) {
            mVideoPath = new MutableLiveData<>();
        }
        return mVideoPath;
    }

    public void setVideoPath(String videoPath) {
        getVideoPath().setValue(videoPath);
    }

    public void initNoticeList(Context context) {
        OkGoUtils.get(ApiUrl.NOTICEMSGLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NoticeListModel noticeModel = GsonUtils.fromJson(callback, NoticeListModel.class);
                if (noticeModel.getCode() == ApiUrl.SUCCESS) {
                    setNoticeList(noticeModel);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    public void initMyNoticeList(Context context, int currentPage) {

        Map<String, String> map = new HashMap<>();
        map.put("currentPage", currentPage + "");
        OkGoUtils.get(ApiUrl.MYNOTICELIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NoticeNewModel noticeModel = GsonUtils.fromJson(callback, NoticeNewModel.class);
                if (noticeModel.getCode() == ApiUrl.SUCCESS) {
                    setNoticeNewModel(noticeModel);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    public void initDetails(Context context, String id) {

        Map<String, String> map = new HashMap<>();
        map.put("noticeId", id);
        OkGoUtils.get(ApiUrl.NOTICEINFO, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NoticeDetailsModel noticeDetailsModel = GsonUtils.fromJson(callback, NoticeDetailsModel.class);
                if (noticeDetailsModel.getCode() == ApiUrl.SUCCESS) {
                    setNoticeDetailsModel(noticeDetailsModel);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setNoticeDetailsModel(null);
            }
        });

    }

    public void initSelectUser(Context context) {
        OkGoUtils.get(ApiUrl.SELECTUSER, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                SelectUserModel selectUserModel = GsonUtils.fromJson(callback, SelectUserModel.class);
                setSelectUserModel(selectUserModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

}
