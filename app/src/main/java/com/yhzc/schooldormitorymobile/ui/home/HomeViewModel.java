package com.yhzc.schooldormitorymobile.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.BannerModel;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.ContentList;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeModel;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeOfficeModel;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.NoticeModel;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.UnReadMsgModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;

/**
 * @author: procyonlotor
 * @创建日期: 2021/4/22 14:55
 * @描述:
 */
public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<HomeModel> mHomeModel;
    private MutableLiveData<ContentList> mContentList;
    private MutableLiveData<BannerModel> mBannerModel;
    private MutableLiveData<NoticeModel> mNoticeModel;
    private MutableLiveData<Boolean> mNewNotice;
    private MutableLiveData<HomeOfficeModel> mHomeOfficeModel;
    private MutableLiveData<UnReadMsgModel> mUnReadMsgModel;

    public MutableLiveData<UnReadMsgModel> getUnReadMsgModel() {
        if (mUnReadMsgModel == null) {
            mUnReadMsgModel = new MutableLiveData<>();
        }
        return mUnReadMsgModel;
    }

    public void setUnReadMsgModel(UnReadMsgModel data) {
        getUnReadMsgModel().setValue(data);
    }


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<HomeModel> getHomeModel() {
        if (mHomeModel == null) {
            mHomeModel = new MutableLiveData<>();
        }
        return mHomeModel;
    }

    public void setHomeModel(HomeModel HomeModel) {
        getHomeModel().setValue(HomeModel);
    }

    public MutableLiveData<ContentList> getContentList() {
        if (mContentList == null) {
            mContentList = new MutableLiveData<>();
        }
        return mContentList;
    }

    public void setContentList(ContentList contentList) {
        getContentList().setValue(contentList);
    }

    public MutableLiveData<BannerModel> getBannerModel() {
        if (mBannerModel == null) {
            mBannerModel = new MutableLiveData<>();
        }
        return mBannerModel;
    }

    public void setBannerModel(BannerModel bannerModel) {
        getBannerModel().setValue(bannerModel);
    }

    public MutableLiveData<NoticeModel> getNoticeModel() {
        if (mNoticeModel == null) {
            mNoticeModel = new MutableLiveData<>();
        }
        return mNoticeModel;
    }

    public void setNoticeModel(NoticeModel NoticeModel) {
        getNoticeModel().setValue(NoticeModel);
    }

    public MutableLiveData<Boolean> getNewNotice() {
        if (mNewNotice == null) {
            mNewNotice = new MutableLiveData<>();
        }
        return mNewNotice;
    }

    public void setNewNotice(Boolean newNotice) {
        getNewNotice().setValue(newNotice);
    }

    public MutableLiveData<HomeOfficeModel> getHomeOfficeModel() {
        if (mHomeOfficeModel == null) {
            mHomeOfficeModel = new MutableLiveData<>();
        }
        return mHomeOfficeModel;
    }

    public void setHomeOfficeModel(HomeOfficeModel data) {
        getHomeOfficeModel().setValue(data);
    }

    public void initNewNotice(Context context) {
        OkGoUtils.get(ApiUrl.NEWNOTICE, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NewNoticeModel newNoticeModel = GsonUtils.fromJson(callback, NewNoticeModel.class);
                if (newNoticeModel.getCode() == ApiUrl.SUCCESS) {
                    Cache.saveHasNewNotice(newNoticeModel.isData());
                    setNewNotice(newNoticeModel.isData());
                }

                initContentList(context);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                initContentList(context);
            }
        });

    }

    public void initHomeOffice() {
        OkGoUtils.get(ApiUrl.HOMEOFFICEITEM, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setHomeOfficeModel(GsonUtils.fromJson(callback, HomeOfficeModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setHomeOfficeModel(null);
            }
        });
    }

    public void initUnReadMsg() {
        OkGoUtils.get(ApiUrl.MESSAGEUNREAD, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setUnReadMsgModel(GsonUtils.fromJson(callback, UnReadMsgModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setUnReadMsgModel(null);
            }
        });
    }


    public void initContentList(Context context) {

        OkGoUtils.get(ApiUrl.CONTENTLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                ContentList contentList = GsonUtils.fromJson(callback, ContentList.class);
                if (contentList.getCode() == ApiUrl.SUCCESS) {
                    setContentList(contentList);
                }
                initBanner(context);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                initBanner(context);
            }
        });

    }

    public void initBanner(Context context) {
        OkGoUtils.get(ApiUrl.ADLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BannerModel bannerModel = GsonUtils.fromJson(callback, BannerModel.class);
                if (bannerModel.getCode() == ApiUrl.SUCCESS) {
                    setBannerModel(bannerModel);
                }

                initNoticeList(context);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                initNoticeList(context);
            }
        });

    }

    public void initNoticeList(Context context) {
        OkGoUtils.get(ApiUrl.NOTICELIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NoticeModel noticeModel = GsonUtils.fromJson(callback, NoticeModel.class);
                if (noticeModel.getCode() == ApiUrl.SUCCESS) {
                    setNoticeModel(noticeModel);
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }
}
