package com.yhzc.schooldormitorymobile.ui.performanceDetails;

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
public class SpThirdAdapter extends BaseQuickAdapter<DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean, BaseViewHolder> {


    public SpThirdAdapter(int layoutResId, @Nullable List<DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DetailsModel.DataBean.ContentBean.FirstListBean.SecondListBean thirdTitle) {
        int itemPosition = getItemPosition(thirdTitle);
        baseViewHolder.setVisible(R.id.v_round, !StringUtils.isEmpty(thirdTitle.getVcResultId()));
        baseViewHolder.setText(R.id.tv_js, thirdTitle.getSort() + "." +  thirdTitle.getKpxz());
        baseViewHolder.setText(R.id.et_score,thirdTitle.getDlItemScore()+"");
        baseViewHolder.setText(R.id.et_num,!StringUtils.isEmpty(thirdTitle.getIntSize()) ? thirdTitle.getIntSize() : thirdTitle.getIntItemIntSize());
        baseViewHolder.setText(R.id.et_zp,thirdTitle.getTextZp()+"");
        baseViewHolder.setText(R.id.et_score_jg,thirdTitle.getDlEndScore()+"");
        baseViewHolder.setText(R.id.et_num_jg,thirdTitle.getIntExamineSize()+"");
        baseViewHolder.setText(R.id.et_jg,thirdTitle.getTextExamineRemark()+"");




    }

}
