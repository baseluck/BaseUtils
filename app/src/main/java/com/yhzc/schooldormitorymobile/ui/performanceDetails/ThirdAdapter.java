package com.yhzc.schooldormitorymobile.ui.performanceDetails;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:33
 * @描述:
 */
public class ThirdAdapter extends BaseQuickAdapter<DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean, BaseViewHolder> {

    private MyWatcher mMyWatcher;

    public void setMyWatcher(MyWatcher myWatcher) {
        mMyWatcher = myWatcher;
    }

    public ThirdAdapter(int layoutResId, @Nullable List<DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean thirdTitle) {

        int itemPosition = getItemPosition(thirdTitle);

        baseViewHolder.setVisible(R.id.v_round, !StringUtils.isEmpty(thirdTitle.getVcResultId()));

        baseViewHolder.setText(R.id.tv_js, thirdTitle.getSort() + "." +  thirdTitle.getKpxz());
        baseViewHolder.setText(R.id.et_score,thirdTitle.getDlItemScore()+"");


        EditText etZp = baseViewHolder.getView(R.id.et_zp);
        EditText etNum = baseViewHolder.getView(R.id.et_num);

        if (etZp.getTag() != null && etZp.getTag() instanceof TextWatcher) {
            etZp.removeTextChangedListener((TextWatcher) etZp.getTag());
        }
        if (etNum.getTag() != null && etNum.getTag() instanceof TextWatcher) {
            etNum.removeTextChangedListener((TextWatcher) etNum.getTag());
        }
        etZp.setText(thirdTitle.getTextZp());
        etNum.setText(!StringUtils.isEmpty(thirdTitle.getIntSize()) ? thirdTitle.getIntSize() : thirdTitle.getIntItemIntSize());

        String id = thirdTitle.getVcXzId();
        String score = String.valueOf(thirdTitle.getDlItemScore());

        baseViewHolder.getView(R.id.iv_add).setOnClickListener(v -> {
            int num = Integer.parseInt(!StringUtils.isEmpty(thirdTitle.getIntSize()) ? thirdTitle.getIntSize() : thirdTitle.getIntItemIntSize());
            int max = Integer.parseInt(thirdTitle.getIntItemMaxSize());
            if (num < max) {
                num++;
                thirdTitle.setIntSize(num + "");
                etNum.setText(String.valueOf(num));
                mMyWatcher.numChanged(String.valueOf(num), itemPosition, id, score);
            }
        });
        baseViewHolder.getView(R.id.iv_minus).setOnClickListener(v -> {
            int num = Integer.parseInt(!StringUtils.isEmpty(thirdTitle.getIntSize()) ? thirdTitle.getIntSize() :thirdTitle.getIntItemIntSize());
            int min = Integer.parseInt(thirdTitle.getIntItemIntSize());
            if (num > min) {
                num--;
                thirdTitle.setIntSize(num + "");
                etNum.setText(String.valueOf(num));
                mMyWatcher.numChanged(String.valueOf(num), itemPosition, id, score);
            }
        });

        TextWatcher zpWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMyWatcher.zpChanged(s, itemPosition, id, score);
                thirdTitle.setTextZp(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        TextWatcher NumWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMyWatcher.numChanged(s, itemPosition, id, score);
                thirdTitle.setIntSize(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etZp.addTextChangedListener(zpWatcher);
        etNum.addTextChangedListener(NumWatcher);

        etZp.setTag(zpWatcher);
        etNum.setTag(NumWatcher);

    }

    public interface MyWatcher {

        void zpChanged(CharSequence zp, int position, String id, String score);

        void numChanged(CharSequence num, int position, String id, String score);


    }

}
