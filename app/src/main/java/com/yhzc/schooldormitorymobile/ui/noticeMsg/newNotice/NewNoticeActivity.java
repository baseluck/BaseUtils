package com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice;

import android.os.Bundle;

import com.widemouth.library.toolitem.WMToolAlignment;
import com.widemouth.library.toolitem.WMToolBackgroundColor;
import com.widemouth.library.toolitem.WMToolBold;
import com.widemouth.library.toolitem.WMToolItalic;
import com.widemouth.library.toolitem.WMToolItem;
import com.widemouth.library.toolitem.WMToolListBullet;
import com.widemouth.library.toolitem.WMToolListClickToSwitch;
import com.widemouth.library.toolitem.WMToolListNumber;
import com.widemouth.library.toolitem.WMToolQuote;
import com.widemouth.library.toolitem.WMToolSplitLine;
import com.widemouth.library.toolitem.WMToolStrikethrough;
import com.widemouth.library.toolitem.WMToolTextColor;
import com.widemouth.library.toolitem.WMToolTextSize;
import com.widemouth.library.toolitem.WMToolUnderline;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityNewNoticeBinding;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.NoticeViewModel;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/24 16:27
 * @描述: 发布通知
 */
public class NewNoticeActivity extends BaseActivity<NoticeViewModel, ActivityNewNoticeBinding> {

    private final WMToolItem toolBold = new WMToolBold();
    private final WMToolItem toolItalic = new WMToolItalic();
    private final WMToolItem toolUnderline = new WMToolUnderline();
    private final WMToolItem toolStrikethrough = new WMToolStrikethrough();
    private final WMToolItem toolTextColor = new WMToolTextColor();
    private final WMToolItem toolBackgroundColor = new WMToolBackgroundColor();
    private final WMToolItem toolTextSize = new WMToolTextSize();
    private final WMToolItem toolListNumber = new WMToolListNumber();
    private final WMToolItem toolListBullet = new WMToolListBullet();
    private final WMToolItem toolAlignment = new WMToolAlignment();
    private final WMToolItem toolQuote = new WMToolQuote();
    private final WMToolItem toolListClickToSwitch = new WMToolListClickToSwitch();
    private final WMToolItem toolSplitLine = new WMToolSplitLine();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_notice;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.wmtc.addToolItem(toolTextColor);
        mViewDataBind.wmtc.addToolItem(toolBackgroundColor);
        mViewDataBind.wmtc.addToolItem(toolTextSize);
        mViewDataBind.wmtc.addToolItem(toolBold);
        mViewDataBind.wmtc.addToolItem(toolItalic);
        mViewDataBind.wmtc.addToolItem(toolUnderline);
        mViewDataBind.wmtc.addToolItem(toolStrikethrough);
        mViewDataBind.wmtc.addToolItem(toolListNumber);
        mViewDataBind.wmtc.addToolItem(toolListBullet);
        mViewDataBind.wmtc.addToolItem(toolAlignment);
        mViewDataBind.wmtc.addToolItem(toolQuote);
        mViewDataBind.wmtc.addToolItem(toolListClickToSwitch);
        mViewDataBind.wmtc.addToolItem(toolSplitLine);

        mViewDataBind.wmet.setupWithToolContainer(mViewDataBind.wmtc);


        showRightTextAndOnClickListener("确定", v -> {
            String html = mViewDataBind.wmet.getHtml();
        });
    }

    @Override
    protected int initTitle() {
        return R.string.srnr;
    }
}