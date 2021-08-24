package com.yhzc.schooldormitorymobile.ui.checkhealth.details;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhzc.schooldormitorymobile.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/28 15:33
 * @描述:
 */
public class ThirdAdapter extends BaseQuickAdapter<HealthDetailsModel.DataBean.ListBean.ChildBean, BaseViewHolder> {

    private MyWatcher mMyWatcher;

    public void setMyWatcher(MyWatcher myWatcher) {
        mMyWatcher = myWatcher;
    }

    public ThirdAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HealthDetailsModel.DataBean.ListBean.ChildBean thirdTitle) {
        int itemPosition = getItemPosition(thirdTitle);
        baseViewHolder.setVisible(R.id.v_round, thirdTitle.getIntCheckStruts() == 2);
        baseViewHolder.setText(R.id.tv_js, thirdTitle.getIntSortCode() + "." + thirdTitle.getVcDetailsTitle());

        baseViewHolder.setText(R.id.et_num,String.valueOf(thirdTitle.getIntScoreDef()));

        String id = thirdTitle.getVcId();



    }




    public interface MyWatcher {

        void numChanged(CharSequence num, int position, String id);


    }

}
