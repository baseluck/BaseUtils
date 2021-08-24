package com.yhzc.schooldormitorymobile.ui.backbedroom.send;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.backbedroom.ContentItemModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/27 16:06
 * @描述:
 */
public class BackBedroomAdapter extends BaseQuickAdapter<ContentItemModel, BaseViewHolder> {

    private EditTextChangeListener mEditTextChangeListener;

    public void setEditTextChangeListener(EditTextChangeListener editTextChangeListener) {
        this.mEditTextChangeListener = editTextChangeListener;
    }

    public BackBedroomAdapter(int layoutResId, @Nullable List<ContentItemModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ContentItemModel contentItemModel) {
        baseViewHolder.setText(R.id.tv_back_bedroom, contentItemModel.getTitle());
        EditText editText = baseViewHolder.getView(R.id.et_back_bedroom);
        editText.setHint(contentItemModel.getHint());

       

        editText.setTag(contentItemModel);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditTextChangeListener.OnTextChageListener(s, getData(), getItemPosition(contentItemModel));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        if (ObjectUtils.equals(editText.getTag(),contentItemModel)){
            editText.setFocusable(contentItemModel.isCanEnter());
        }

        editText.addTextChangedListener(textWatcher);

    }

    interface EditTextChangeListener {
        /**
         * 输入框监听
         *
         * @param s
         * @param data
         * @param position
         */
        void OnTextChageListener(CharSequence s, List<ContentItemModel> data, int position);
    }
}
