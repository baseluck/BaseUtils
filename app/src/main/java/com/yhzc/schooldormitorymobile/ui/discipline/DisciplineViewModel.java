package com.yhzc.schooldormitorymobile.ui.discipline;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.discipline.details.DisciplineDetailsModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.DisciplineLevelModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.ImageModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.SelectStudentModel;
import com.yhzc.schooldormitorymobile.ui.discipline.details.StudentListModel;
import com.yhzc.schooldormitorymobile.ui.discipline.list.DisciplineListModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/30 11:24
 * @描述:
 */
public class DisciplineViewModel extends AndroidViewModel {

    private MutableLiveData<DisciplineListModel> mDisciplineListModel;
    private MutableLiveData<StudentListModel> mStudentListModel;
    private MutableLiveData<DisciplineDetailsModel> mDisciplineDetailsModel;

    private MutableLiveData<List<SelectStudentModel>> mSelectStudents;
    private MutableLiveData<List<ImageModel>> mImages;

    private MutableLiveData<DisciplineLevelModel> mDisciplineLevelModel;

    public MutableLiveData<DisciplineLevelModel> getDisciplineLevelModel() {
        if (mDisciplineLevelModel == null) {
            mDisciplineLevelModel = new MutableLiveData<>();
        }
        return mDisciplineLevelModel;
    }

    public void setDisciplineLevelModel(DisciplineLevelModel data) {
        getDisciplineLevelModel().setValue(data);
    }

    public DisciplineViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<DisciplineListModel> getDisciplineListModel() {
        if (mDisciplineListModel == null) {
            mDisciplineListModel = new MutableLiveData<>();
        }
        return mDisciplineListModel;
    }

    public void setDisciplineListModel(DisciplineListModel data) {
        getDisciplineListModel().setValue(data);
    }

    public MutableLiveData<List<SelectStudentModel>> getSelectStudents() {
        if (mSelectStudents == null) {
            mSelectStudents = new MutableLiveData<>();
        }
        return mSelectStudents;
    }

    public void setSelectStudents(List<SelectStudentModel> selectStudents) {
        getSelectStudents().setValue(selectStudents);
    }

    public MutableLiveData<List<ImageModel>> getImages() {
        if (mImages == null) {
            mImages = new MutableLiveData<>();
        }
        return mImages;
    }

    public void setImages(List<ImageModel> images) {
        getImages().setValue(images);
    }

    public MutableLiveData<StudentListModel> getStudentListModel() {
        if (mStudentListModel == null) {
            mStudentListModel = new MutableLiveData<>();
        }
        return mStudentListModel;
    }

    public void setStudentListModel(StudentListModel data) {
        getStudentListModel().setValue(data);
    }

    public MutableLiveData<DisciplineDetailsModel> getDisciplineDetailsModel() {
        if (mDisciplineDetailsModel == null) {
            mDisciplineDetailsModel = new MutableLiveData<>();
        }
        return mDisciplineDetailsModel;
    }

    public void setDisciplineDetailsModel(DisciplineDetailsModel data) {
        getDisciplineDetailsModel().setValue(data);
    }

    public void initList(int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("currentPage", String.valueOf(currentPage));

        OkGoUtils.get(ApiUrl.WJLIST, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setDisciplineListModel(GsonUtils.fromJson(callback, DisciplineListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setDisciplineListModel(null);
            }
        });
    }

    public void initCkList(int currentPage, String type) {
        String url = StringUtils.equals("1", type) ? ApiUrl.BZRLIST : ApiUrl.XLDLIST;

        Map<String, String> map = new HashMap<>();
        map.put("currentPage", String.valueOf(currentPage));

        OkGoUtils.get(url, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setDisciplineListModel(GsonUtils.fromJson(callback, DisciplineListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setDisciplineListModel(null);
            }
        });
    }


    public void initDetails(String id) {
        OkGoUtils.get(ApiUrl.WJDETAILS + id, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setDisciplineDetailsModel(GsonUtils.fromJson(callback, DisciplineDetailsModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setDisciplineDetailsModel(null);
            }
        });
    }

    public void initLevel() {
        OkGoUtils.get(ApiUrl.WJLEVEL, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setDisciplineLevelModel(GsonUtils.fromJson(callback, DisciplineLevelModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setDisciplineLevelModel(null);
            }
        });
    }

    public void initStudent() {
        OkGoUtils.get(ApiUrl.STUDENTLIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setStudentListModel(GsonUtils.fromJson(callback, StudentListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setStudentListModel(null);
            }
        });
    }
}
