package com.yhzc.schooldormitorymobile.ui.home.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNewsDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.news.NewsViewModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/20 10:48
 * @描述:
 */
public class MsgDetailsActivity extends BaseActivity<NewsViewModel, ActivityNewsDetailsBinding> {

    private static final String JS = "<script type=\"text/javascript\">" +
            "var imgs = document.getElementsByTagName('img');" + // 找到img标签
            "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
            "imgs[i].style.width = '100%';" +  // 宽度改为100%
            "imgs[i].style.height = 'auto';" +
            "    imgs[i].onclick=function()  " +
            "    {  " +
            "        window.imagelistener.openImage(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
            "    }  " +
            "}" +
            "</script>";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void afterCreate(Bundle savedInstanceState) {


        NoticeModel.DataBean dataBean = getIntent().getParcelableExtra("message");

        mViewDataBind.wvDetails.setVerticalScrollBarEnabled(false);
        mViewDataBind.wvDetails.setHorizontalScrollBarEnabled(false);

        //支持javascript
        mViewDataBind.wvDetails.getSettings().setJavaScriptEnabled(true);
        mViewDataBind.wvDetails.clearCache(true);

        mViewDataBind.tvDetailsTitle.setText(dataBean.getTitle());
        mViewDataBind.tvDetailsXmNumber.setText(String.format(StringUtils.getString(R.string.author), dataBean.getCreatorUser()));
        mViewDataBind.tvDetailsSendTime.setText(String.format(StringUtils.getString(R.string.add_time), dataBean.getCreatorTime()));
        mViewDataBind.tvLlrs.setVisibility(View.GONE);

        mViewDataBind.wvDetails.loadData((dataBean.getBodyText() + JS)
                .replace("&amp;zoom=600w", ""), "text/html;charset=utf-8", null);

    }

    @Override
    protected int initTitle() {
        return R.string.gg_details;
    }
}