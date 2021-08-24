package com.yhzc.schooldormitorymobile.ui.modifystudent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.backbedroom.send.StudentModel;
import com.yhzc.schooldormitorymobile.ui.modifystudent.classlist.ClassListModel;
import com.yhzc.schooldormitorymobile.ui.modifystudent.studentdetails.JgModel;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 15:01
 * @描述:
 */
public class ModifyStudentViewModel extends AndroidViewModel {

    private MutableLiveData<ClassListModel> mClassListModel;
    private MutableLiveData<StudentModel> mStudentModel;
    private MutableLiveData<JgModel> mJgModel;

    public MutableLiveData<JgModel> getJgModel() {
        if (mJgModel == null) {
            mJgModel = new MutableLiveData<>();
        }
        return mJgModel;
    }

    public void setJgModel(JgModel data) {
        getJgModel().setValue(data);
    }

    public MutableLiveData<StudentModel> getStudentModel() {
        if (mStudentModel == null) {
            mStudentModel = new MutableLiveData<>();
        }
        return mStudentModel;
    }

    public void setStudentModel(StudentModel data) {
        getStudentModel().setValue(data);
    }

    public MutableLiveData<ClassListModel> getClassListModel() {
        if (mClassListModel == null) {
            mClassListModel = new MutableLiveData<>();
        }
        return mClassListModel;
    }

    public void setClassListModel(ClassListModel data) {
        getClassListModel().setValue(data);
    }

    public ModifyStudentViewModel(@NonNull Application application) {
        super(application);
    }

    public void initClassList() {
        OkGoUtils.get(ApiUrl.CLASSSTU, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setClassListModel(GsonUtils.fromJson(callback, ClassListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setClassListModel(null);
            }
        });
    }

    public void getStudnet(String studentId) {
        OkGoUtils.get(ApiUrl.STUDENTINFO2 + studentId, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                StudentModel studentModel = GsonUtils.fromJson(callback, StudentModel.class);
                setStudentModel(studentModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setStudentModel(null);
            }
        });
    }

    public void getJg(String id) {
        OkGoUtils.get(ApiUrl.GETDQ + id + ApiUrl.SELECTOR, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setJgModel(GsonUtils.fromJson(callback, JgModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setJgModel(null);
            }
        });
    }
}
