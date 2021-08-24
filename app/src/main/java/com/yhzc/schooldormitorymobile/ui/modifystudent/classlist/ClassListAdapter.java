package com.yhzc.schooldormitorymobile.ui.modifystudent.classlist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/8/19 15:44
 * @描述:
 */
public class ClassListAdapter extends BaseQuickAdapter<ClassListModel.DataBean, BaseViewHolder> {

    private StudentClick mStudentClick;

    public void setStudentClick(StudentClick studentClick) {
        mStudentClick = studentClick;
    }

    public ClassListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ClassListModel.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_class_name, dataBean.getVcName());

        ClassListStudentAdapter studentAdapter = new ClassListStudentAdapter(R.layout.item_class_list_student, dataBean.getStuList());

        RecyclerView rv = baseViewHolder.getView(R.id.rv_student);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv.setAdapter(studentAdapter);

        studentAdapter.setOnItemClickListener((adapter, view, position) -> {
            ClassListModel.DataBean.StuListBean stuListBean = (ClassListModel.DataBean.StuListBean) adapter.getData().get(position);
            mStudentClick.ClickListener(stuListBean);
        });
    }

    interface StudentClick {
        void ClickListener(ClassListModel.DataBean.StuListBean stuListBean);
    }
}
