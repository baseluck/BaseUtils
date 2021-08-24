package com.yhzc.schooldormitorymobile.ui.news.details;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNewsDetailsBinding;
import com.yhzc.schooldormitorymobile.ui.news.NewsViewModel;

/**
* @author: Procyonlotor
* @创建日期: 2021/5/20 10:48
* @描述:
*/
public class NewsDetailsActivity extends BaseActivity<NewsViewModel, ActivityNewsDetailsBinding> {

    private static final String JS = "<script type=\"text/javascript\">" +
            "var imgs = document.getElementsByTagName('img');" + // 找到img标签
            "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
            "imgs[i].style.width = '100%';" +  // 宽度改为100%
            "imgs[i].style.height = 'auto';" +
            "    imgs[i].onclick=function()  " +
            "    {  "+
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
        String id = getIntent().getStringExtra("id");
        mViewModel.initNewsDetails(this,id);
        mViewDataBind.wvDetails.setVerticalScrollBarEnabled(false);
        mViewDataBind.wvDetails.setHorizontalScrollBarEnabled(false);
        mViewDataBind.wvDetails.getSettings().setJavaScriptEnabled(true);
        mViewDataBind.wvDetails.clearCache(true);
        mViewModel.getNewsDetailsModel().observe(this, newsDetailsModel -> {
            mViewDataBind.tvDetailsTitle.setText(newsDetailsModel.getData().getVcTitle());
            mViewDataBind.tvDetailsXmNumber.setText(String.format(StringUtils.getString(R.string.author),newsDetailsModel.getData().getVcAuthor()));
            mViewDataBind.tvDetailsSendTime.setText(String.format(StringUtils.getString(R.string.add_time), TimeUtils.millis2String(newsDetailsModel.getData().getDtSendTime())));
            mViewDataBind.tvLlrs.setText(String.format(StringUtils.getString(R.string.views),newsDetailsModel.getData().getIntShowCount()));
            mViewDataBind.wvDetails.loadData((newsDetailsModel.getData().getTextContent() + JS)
                    .replace("&amp;zoom=600w", ""), "text/html;charset=utf-8", null);
        });
    }

    @Override
    protected int initTitle() {
        return R.string.news_details;
    }
}