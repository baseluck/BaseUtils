package com.yhzc.schooldormitorymobile.ui.message;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.message.details.MessageDetailsModel;
import com.yhzc.schooldormitorymobile.ui.message.list.MessageListModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/29 16:21
 * @描述:
 */
public class MessageViewModel extends AndroidViewModel {

    private MutableLiveData<MessageListModel> mMessageListModel;
    private MutableLiveData<MessageDetailsModel> mMessageDetailsModel;


    public MessageViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<MessageListModel> getMessageListModel() {
        if (mMessageListModel == null) {
            mMessageListModel = new MutableLiveData<>();
        }
        return mMessageListModel;
    }

    public void setMessageListModel(MessageListModel data) {
        getMessageListModel().setValue(data);
    }

    public MutableLiveData<MessageDetailsModel> getMessageDetailsModel() {
        if (mMessageDetailsModel == null) {
            mMessageDetailsModel = new MutableLiveData<>();
        }
        return mMessageDetailsModel;
    }

    public void setMessageDetailsModel(MessageDetailsModel data) {
        getMessageDetailsModel().setValue(data);
    }

    public void initMessageList(int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", String.valueOf(currentPage));

        OkGoUtils.get(ApiUrl.MESSAGELIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setMessageListModel(GsonUtils.fromJson(callback, MessageListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setMessageListModel(null);
            }
        });
    }

    public void initMessageDetails(String id) {

        OkGoUtils.get(ApiUrl.MESSAGEDETAILS + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setMessageDetailsModel(GsonUtils.fromJson(callback, MessageDetailsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setMessageListModel(null);
            }
        });
    }
}
