package com.yhzc.schooldormitorymobile.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.cy.tablayoutniubility.FragPageAdapterVp2NoScroll;
import com.cy.tablayoutniubility.TabAdapterNoScroll;
import com.cy.tablayoutniubility.TabMediatorVp2NoScroll;
import com.cy.tablayoutniubility.TabNoScrollViewHolder;
import com.cy.tablayoutniubility.TabSelectBean;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityHomeBinding;
import com.yhzc.schooldormitorymobile.service.MessageService;
import com.yhzc.schooldormitorymobile.ui.home.fragment.home.HomeFragment;
import com.yhzc.schooldormitorymobile.ui.home.fragment.mine.MineFragment;
import com.yhzc.schooldormitorymobile.ui.mobileOffice.MobileOfficeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/22 16:57
 * @描述: 首页 Activity
 */
public class HomeActivity extends BaseActivity<HomeViewModel, ActivityHomeBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        BarUtils.setStatusBarColor(this, 0x00000000);
        MessageService.getConnet(this);

        View childAt = mViewDataBind.vp.getChildAt(0);
        childAt.setOverScrollMode(View.OVER_SCROLL_NEVER);

        FragPageAdapterVp2NoScroll<TabSelectBean> fragmentPageAdapter = new FragPageAdapterVp2NoScroll<TabSelectBean>(this) {

            @Override
            public Fragment createFragment(TabSelectBean bean, int position) {
                if (position == 0) {
                    return HomeFragment.newInstance();
                } else {
                    return MineFragment.newInstance();
                }
            }

            @Override
            public void bindDataToTab(TabNoScrollViewHolder holder, int position, TabSelectBean bean, boolean isSelected) {
                TextView textView = holder.getView(R.id.tv_title);
                if (isSelected) {
                    textView.setTextColor(0xFF1094F5);
                    holder.setImageResource(R.id.iv_head, bean.getResID_selected());
                } else {
                    textView.setTextColor(0xff444444);
                    holder.setImageResource(R.id.iv_head, bean.getResID_normal());
                }
                textView.setText(bean.getText());
            }

            @Override
            public int getTabLayoutID(int position, TabSelectBean bean) {
                return R.layout.home_menu;
            }
        };

        TabAdapterNoScroll<TabSelectBean> tabAdapter = new TabMediatorVp2NoScroll<TabSelectBean>(mViewDataBind.tablayout, mViewDataBind.vp).setAdapter(fragmentPageAdapter);

        List<TabSelectBean> list = new ArrayList<>();
        list.add(new TabSelectBean("学校服务", R.mipmap.ic_home_un, R.mipmap.ic_home));
        list.add(new TabSelectBean("我的空间", R.mipmap.ic_mine_un, R.mipmap.ic_mine));
        fragmentPageAdapter.add(list);
        tabAdapter.add(list);

        mViewDataBind.tvHomeApp.setOnClickListener(v -> ActivityUtils.startActivity(MobileOfficeActivity.class));
    }

    @Override
    protected int initTitle() {
        return 0;
    }


}