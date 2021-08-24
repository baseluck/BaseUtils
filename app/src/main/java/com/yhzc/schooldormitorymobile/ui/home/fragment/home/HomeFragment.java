package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseFragment;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.adapter.ImageRoundAdapter;
import com.yhzc.schooldormitorymobile.databinding.FragmentHomeBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.yhzc.schooldormitorymobile.ui.home.HomeAppAdapter;
import com.yhzc.schooldormitorymobile.ui.home.HomeViewModel;
import com.yhzc.schooldormitorymobile.ui.message.list.MessageListActivity;
import com.yhzc.schooldormitorymobile.ui.news.details.NewsDetailsActivity;
import com.yhzc.schooldormitorymobile.ui.news.list.NewsListActivity;
import com.yhzc.schooldormitorymobile.utils.StartActivityUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: procyonlotor
 * @创建日期: 2021/4/22 14:50
 * @描述: 首页
 */
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {

    private static final int LISTSIZE = 3;

    private int height = 500;
    private HomeNewsAdapter mHomeNewsAdapter;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.initNewNotice(requireContext());
        mViewModel.initHomeOffice();
        mViewModel.initUnReadMsg();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initBar();
        initNews();
        initApp();
        mViewModel.getUnReadMsgModel().observe(this, unReadMsgModel -> {
            if (unReadMsgModel != null) {
                if (unReadMsgModel.getData().isIsUnRead()) {
                    mViewDataBind.vNewMsg.setVisibility(View.VISIBLE);
                } else {
                    mViewDataBind.vNewMsg.setVisibility(View.GONE);
                }
            }
        });
        mViewModel.getNoticeModel().observe(this, noticeModel -> {
            if (noticeModel.getCode() == ApiUrl.SUCCESS) {
                if (noticeModel.getData() != null) {
                    initNotice(noticeModel.getData());
                }
            }
        });
        mViewModel.getBannerModel().observe(this, bannerModel -> {
            if (bannerModel.getCode() == ApiUrl.SUCCESS) {
                if (bannerModel.getData().getList() != null) {
                    initBanner(bannerModel.getData().getList());
                }
            }
        });
        mViewModel.getContentList().observe(this, contentList -> {
            if (contentList.getCode() == ApiUrl.SUCCESS) {
                if (contentList.getData().getList() != null) {
                    if (contentList.getData().getList().size() <= LISTSIZE) {
                        mHomeNewsAdapter.setList(contentList.getData().getList());
                    } else {
                        List<ContentList.DataBean.ListBean> listBeans = new ArrayList<>();
                        for (int i = 0; i < LISTSIZE; i++) {
                            listBeans.add(contentList.getData().getList().get(i));
                        }
                        mHomeNewsAdapter.setList(listBeans);
                    }
                }
            }
        });
        mViewDataBind.vNewMsg.setVisibility(View.VISIBLE);
        mViewDataBind.ivMsg2.setOnClickListener(v -> ActivityUtils.startActivity(MessageListActivity.class));
        mViewDataBind.ivMsg.setOnClickListener(v -> ActivityUtils.startActivity(MessageListActivity.class));
    }

    private void initApp() {
        mViewDataBind.rvApp.setLayoutManager(new GridLayoutManager(requireContext(), 4) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        HomeAppAdapter appAdapter = new HomeAppAdapter(R.layout.item_home_app, null);
        mViewDataBind.rvApp.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<HomeOfficeModel.DataBean> data = (List<HomeOfficeModel.DataBean>) adapter.getData();
            StartActivityUtils.startActivity(requireActivity(), data.get(position).getEnCode());
        });
        mViewModel.getHomeOfficeModel().observe(this, homeModel -> {
            if (homeModel != null) {
                if (homeModel.getData() == null || homeModel.getData().size() == 0) {
                    mViewDataBind.clApp.setVisibility(View.GONE);
                } else {
                    mViewDataBind.clApp.setVisibility(View.VISIBLE);
                    appAdapter.setList(homeModel.getData());
                }
            }
        });
    }

    private void initNotice(List<NoticeModel.DataBean> noticeBeans) {
        if (noticeBeans != null && noticeBeans.size() != 0) {
            mViewDataBind.clNotice.setVisibility(View.VISIBLE);
        } else {
            mViewDataBind.clNotice.setVisibility(View.GONE);
        }
        mViewDataBind.bannerNotice.setAdapter(new HomeNoticeAdapter(noticeBeans))
                .setOrientation(Banner.VERTICAL)
                .setOnBannerListener((data, position) -> {
                    NoticeModel.DataBean dataBean = (NoticeModel.DataBean) data;
                    Intent intent = new Intent(requireActivity(), MsgDetailsActivity.class);
                    intent.putExtra("message", dataBean);
                    ActivityUtils.startActivity(intent);
                });
    }

    private void initNews() {
        mViewDataBind.rvNews.setLayoutManager(new LinearLayoutManager(requireContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mHomeNewsAdapter = new HomeNewsAdapter(R.layout.item_home_news, null);
        mViewDataBind.rvNews.setAdapter(mHomeNewsAdapter);
        mViewDataBind.tvNewsMore.setOnClickListener(v -> ActivityUtils.startActivity(NewsListActivity.class));
        mHomeNewsAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<ContentList.DataBean.ListBean> data = (List<ContentList.DataBean.ListBean>) adapter.getData();
            Intent intent = new Intent(requireActivity(), NewsDetailsActivity.class);
            intent.putExtra("id", data.get(position).getVcId());
            ActivityUtils.startActivity(intent);
        });
    }

    private void initBar() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mViewDataBind.imageView2.measure(w, h);
        mViewDataBind.clTop.measure(w, h);
        int heightIv = mViewDataBind.imageView2.getMeasuredHeight();
        int heightTop = mViewDataBind.clTop.getMeasuredHeight();
        height = heightIv - heightTop;
        mViewDataBind.clTop.getBackground().setAlpha(0);
        mViewDataBind.svHome.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY <= 0) {
                        mViewDataBind.clTop.getBackground().setAlpha(0);
                    } else if (scrollY <= height) {
                        float scale = (float) scrollY / height;
                        float alpha = (255 * scale);
                        mViewDataBind.clTop.getBackground().setAlpha((int) alpha);
                    } else {
                        mViewDataBind.clTop.getBackground().setAlpha(255);
                    }
                });
    }

    private void initBanner(List<BannerModel.DataBean.ListBean> bannerBeans) {
        mViewDataBind.bannerView.setAdapter(new ImageRoundAdapter(bannerBeans));
        mViewDataBind.bannerView.setIndicator(mViewDataBind.indicator, false);
        mViewDataBind.bannerView.setBannerGalleryEffect(ConvertUtils.dp2px(3), ConvertUtils.dp2px(4));
        mViewDataBind.bannerView.setOnBannerListener((data, position) -> {
            BannerModel.DataBean.ListBean listBean = (BannerModel.DataBean.ListBean) data;
            if (StringUtils.equals(listBean.getVcType(), "out")) {
                openBrowser(requireContext(), listBean.getVcUrl());
            } else {
                Intent intent = new Intent(requireActivity(), NewsDetailsActivity.class);
                intent.putExtra("id", listBean.getVcId());
                ActivityUtils.startActivity(intent);
            }
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            ToastUtils.showShort("链接错误或无浏览器");
        }
    }
}