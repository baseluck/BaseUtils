package com.yhzc.schooldormitorymobile.ui.notetake;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.notetake.list.NoteListModel;
import com.yhzc.schooldormitorymobile.ui.notetake.list.NoteTypeListModel;
import com.yhzc.schooldormitorymobile.ui.notetake.take.TimeModel;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/21 16:56
 * @描述:
 */
public class NoteTakeViewModel extends AndroidViewModel {

    private MutableLiveData<NoteTypeListModel> mNoteTypeListModel;
    private MutableLiveData<List<TimeModel>> mTimeModels;

    private MutableLiveData<NoteListModel> mNoteListModel;


    public NoteTakeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<NoteTypeListModel> getNoteListTypeModel() {
        if (mNoteTypeListModel == null) {
            mNoteTypeListModel = new MutableLiveData<>();
        }
        return mNoteTypeListModel;
    }

    public void setNoteListTypeModel(NoteTypeListModel noteListModel) {
        getNoteListTypeModel().setValue(noteListModel);
    }

    public MutableLiveData<List<TimeModel>> getTimeModels() {
        if (mTimeModels == null) {
            mTimeModels = new MutableLiveData<>();
        }
        return mTimeModels;
    }

    public void setTimeModels(List<TimeModel> timeModels) {
        getTimeModels().setValue(timeModels);
    }

    public MutableLiveData<NoteListModel> getNoteListModel() {
        if (mNoteListModel == null) {
            mNoteListModel = new MutableLiveData<>();
        }
        return mNoteListModel;
    }

    public void setNoteListModel(NoteListModel NoteListModel) {
        getNoteListModel().setValue(NoteListModel);
    }

    public void initNoteList() {
        OkGoUtils.get(ApiUrl.NOTECATELIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                setNoteListTypeModel(GsonUtils.fromJson(callback, NoteTypeListModel.class));
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setNoteListTypeModel(null);
            }
        });
    }

    public void initNoteListHome() {
        OkGoUtils.get(ApiUrl.NOTELIST, new HashMap(), new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                NoteListModel noteListModel = GsonUtils.fromJson(callback, NoteListModel.class);
                if (noteListModel != null && noteListModel.getData() != null && noteListModel.getData().size() != 0) {
                    noteListModel.getData().get(0).setSelected(true);
                }

                setNoteListModel(noteListModel);
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                setNoteListModel(null);
            }
        });
    }

    public void initTimeModels() {
        TimeModel timeModel1 = new TimeModel("00:00", false);
        TimeModel timeModel2 = new TimeModel("02:00", false);
        TimeModel timeModel3 = new TimeModel("04:00", false);
        TimeModel timeModel4 = new TimeModel("06:00", false);
        TimeModel timeModel5 = new TimeModel("08:00", false);
        TimeModel timeModel6 = new TimeModel("10:00", false);
        TimeModel timeModel7 = new TimeModel("12:00", false);
        TimeModel timeModel8 = new TimeModel("14:00", false);
        TimeModel timeModel9 = new TimeModel("16:00", false);
        TimeModel timeModel10 = new TimeModel("18:00", false);
        TimeModel timeModel11 = new TimeModel("20:00", false);
        TimeModel timeModel12 = new TimeModel("22:00", false);

        List<TimeModel> list = new ArrayList<>();
        list.add(timeModel1);
        list.add(timeModel2);
        list.add(timeModel3);
        list.add(timeModel4);
        list.add(timeModel5);
        list.add(timeModel6);
        list.add(timeModel7);
        list.add(timeModel8);
        list.add(timeModel9);
        list.add(timeModel10);
        list.add(timeModel11);
        list.add(timeModel12);

        setTimeModels(list);
    }
}
