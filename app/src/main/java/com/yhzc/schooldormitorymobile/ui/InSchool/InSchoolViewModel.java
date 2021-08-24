package com.yhzc.schooldormitorymobile.ui.InSchool;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/28 10:20
 * @描述:
 */
public class InSchoolViewModel extends AndroidViewModel {

    private MutableLiveData<StudentModel> mStudentModel;
    private MutableLiveData<InSchoolListModel> mInSchoolListModel;


    public InSchoolViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<StudentModel> getStudentModel() {
        if (mStudentModel == null) {
            mStudentModel = new MutableLiveData<>();
        }
        return mStudentModel;
    }

    public void setStudentModel(StudentModel StudentModel) {
        getStudentModel().setValue(StudentModel);
    }

    public MutableLiveData<InSchoolListModel> getInSchoolListModel() {
        if (mInSchoolListModel == null) {
            mInSchoolListModel = new MutableLiveData<>();
        }
        return mInSchoolListModel;
    }

    public void setInSchoolListModel(InSchoolListModel InSchoolListModel) {
        getInSchoolListModel().setValue(InSchoolListModel);
    }

    public void initInSchoolList(String date, int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", String.valueOf(currentPage));
        if (!StringUtils.isEmpty(date)) {
            map.put("date", date);
        }
        OkGoUtils.get(ApiUrl.STUDENTINRECORD, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                InSchoolListModel inSchoolListModel = GsonUtils.fromJson(callback, InSchoolListModel.class);
                setInSchoolListModel(inSchoolListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });

    }

    public void initStudent(String card) {
        Map<String, String> map = new HashMap<>();
        map.put("idCardNo", card);
        OkGoUtils.get(ApiUrl.STUDENTINFO, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                StudentModel studentModel = GsonUtils.fromJson(callback, StudentModel.class);
                setStudentModel(studentModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }
}
