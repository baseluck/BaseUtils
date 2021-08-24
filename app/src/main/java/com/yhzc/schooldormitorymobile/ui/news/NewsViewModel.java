package com.yhzc.schooldormitorymobile.ui.news;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.ContentList;
import com.yhzc.schooldormitorymobile.ui.news.details.NewsDetailsModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/19 17:58
 * @描述:
 */
public class NewsViewModel extends AndroidViewModel {

    private MutableLiveData<ContentList> mContentList;
    private MutableLiveData<NewsDetailsModel> mNewsDetailsModel;


    public NewsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<ContentList> getContentList() {
        if (mContentList == null) {
            mContentList = new MutableLiveData<>();
        }
        return mContentList;
    }

    public void setContentList(ContentList ContentList) {
        getContentList().setValue(ContentList);
    }

    public MutableLiveData<NewsDetailsModel> getNewsDetailsModel() {
        if (mNewsDetailsModel == null) {
            mNewsDetailsModel = new MutableLiveData<>();
        }
        return mNewsDetailsModel;
    }

    public void setNewsDetailsModel(NewsDetailsModel NewsDetailsModel) {
        getNewsDetailsModel().setValue(NewsDetailsModel);
    }

    public void initContentList(Context context, int currentPage) {

        Map<String, String> map = new HashMap<>();
        map.put("currentPage", currentPage + "");

        OkGoUtils.get(ApiUrl.CONTENTLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                ContentList contentList = GsonUtils.fromJson(callback, ContentList.class);
                if (contentList.getCode() == ApiUrl.SUCCESS) {
                    setContentList(contentList);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    public void initNewsDetails(Context context, String id) {

        OkGoUtils.get(ApiUrl.CONTENT + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NewsDetailsModel newsDetailsModel = GsonUtils.fromJson(callback, NewsDetailsModel.class);
                setNewsDetailsModel(newsDetailsModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }
}
