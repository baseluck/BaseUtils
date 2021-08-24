package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:03
 * @描述:
 */
public class PerformanceDetailsViewModel extends AndroidViewModel {
    private MutableLiveData<PerformanceDetailsModel> mPerformanceDetailsModel;
    private MutableLiveData<DetailsModel> mDetailsModel;
    private MutableLiveData<UpdateBean> mUpdateBean;

    public PerformanceDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<PerformanceDetailsModel> getPerformanceDetailsModel() {
        if (mPerformanceDetailsModel == null) {
            mPerformanceDetailsModel = new MutableLiveData<>();
            mPerformanceDetailsModel.setValue(getData());
        }
        return mPerformanceDetailsModel;
    }

    public void setPerformanceDetailsModel(PerformanceDetailsModel PerformanceDetailsModel) {
        getPerformanceDetailsModel().setValue(PerformanceDetailsModel);
    }

    public MutableLiveData<DetailsModel> getDetailsModel() {
        if (mDetailsModel == null) {
            mDetailsModel = new MutableLiveData<>();
        }
        return mDetailsModel;
    }


    public void setDetailsModel(DetailsModel DetailsModel) {
        getDetailsModel().setValue(DetailsModel);
    }


    public MutableLiveData<UpdateBean> getUpdateBean() {
        if (mUpdateBean == null) {
            mUpdateBean = new MutableLiveData<>();
        }
        return mUpdateBean;
    }

    public void setUpdateBean(UpdateBean UpdateBean) {
        getUpdateBean().setValue(UpdateBean);
    }

    public void initData(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("planId", id);
        OkGoUtils.get(ApiUrl.MYPLANDETAILS, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                DetailsModel detailsModel = GsonUtils.fromJson(callback, DetailsModel.class);
                if (detailsModel.getData().getContent() != null && detailsModel.getData().getContent().size() != 0) {
                    detailsModel.getData().getContent().get(0).setSelect(true);
                    if (detailsModel.getData().getContent().get(0).getFirstList() != null && detailsModel.getData().getContent().get(0).getFirstList().size() != 0) {
                        detailsModel.getData().getContent().get(0).getFirstList().get(0).setSelect(true);
                    }
                }
                setDetailsModel(detailsModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setDetailsModel(null);
            }
        });
    }

    public void update(String content) {
        OkGoUtils.postJson(ApiUrl.UPDATEPLAN, content, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                ActivityUtils.getTopActivity().finish();
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    private PerformanceDetailsModel getData() {
        PerformanceDetailsModel performanceDetailsModel = new PerformanceDetailsModel();
        performanceDetailsModel.setTitle("A1教师教学任务");
        List<PerformanceDetailsModel.FirstTitle> firstTitles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            PerformanceDetailsModel.FirstTitle firstTitle = new PerformanceDetailsModel.FirstTitle();
            firstTitle.setSelect(i == 0);
            firstTitle.setTitle("A" + (i + 1));
            firstTitle.setResult(false);
            Random random = new Random();
            int second = random.nextInt(3) + 4;
            List<PerformanceDetailsModel.FirstTitle.SecondTitle> secondTitles = new ArrayList<>();
            for (int j = 0; j < second; j++) {
                PerformanceDetailsModel.FirstTitle.SecondTitle secondTitle = new PerformanceDetailsModel.FirstTitle.SecondTitle();
                secondTitle.setSelect(j == 0);
                secondTitle.setTitle("B" + (j + 1));
                secondTitle.setResult(false);
                List<PerformanceDetailsModel.FirstTitle.SecondTitle.ThirdTitle> thirdTitles = new ArrayList<>();
                int third = random.nextInt(3) + 1;
                for (int k = 0; k < third; k++) {
                    PerformanceDetailsModel.FirstTitle.SecondTitle.ThirdTitle thirdTitle = new PerformanceDetailsModel.FirstTitle.SecondTitle.ThirdTitle();
                    thirdTitle.setContent((k + 1) + ".  周工作量达到和超过一级工作量\n  查岗时不在学校未向教导处请假");
                    thirdTitle.setScore("");
                    thirdTitle.setZp("");
                    thirdTitles.add(thirdTitle);
                }
                secondTitle.setThirdTitles(thirdTitles);
                secondTitles.add(secondTitle);
            }
            firstTitle.setSecondTitles(secondTitles);
            firstTitles.add(firstTitle);
        }
        performanceDetailsModel.setFirstTitles(firstTitles);

        return performanceDetailsModel;
    }

}
