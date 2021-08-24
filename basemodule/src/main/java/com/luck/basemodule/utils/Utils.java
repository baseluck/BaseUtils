package com.luck.basemodule.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.previewlibrary.GPreviewBuilder;
import com.luck.basemodule.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述:
 * @创建日期: 2021/4/14 17:29
 * @author: ProcyonLotor
 */
public class Utils {

    public static void showBigImg(List<String> images, int position) {
        List<String> urls = new ArrayList<>(images);
        List<CompanyImgInfo> companyImgInfos = new ArrayList<>();
        for (String url : urls) {
            companyImgInfos.add(new CompanyImgInfo(url));
        }
        GPreviewBuilder.from(ActivityUtils.getTopActivity())
                .setData(companyImgInfos)
                .setCurrentIndex(position)
                //是否在黑屏区域点击返回
                .setSingleFling(true)
                .setSingleShowType(false)
                //是否禁用图片拖拽返回
                .setDrag(true)
                //指示器类型
                .setType(GPreviewBuilder.IndicatorType.Dot)
                //启动
                .start();
    }

    public static void showBigImgFile(List<File> images, int position) {
        List<File> urls = new ArrayList<>(images);
        List<CompanyImgInfo> companyImgInfos = new ArrayList<>();
        for (File url : urls) {
            companyImgInfos.add(new CompanyImgInfo(url.getAbsolutePath()));
        }
        GPreviewBuilder.from(ActivityUtils.getTopActivity())
                .setData(companyImgInfos)
                .setCurrentIndex(position)
                //是否在黑屏区域点击返回
                .setSingleFling(true)
                .setSingleShowType(false)
                //是否禁用图片拖拽返回
                .setDrag(true)
                //指示器类型
                .setType(GPreviewBuilder.IndicatorType.Dot)
                //启动
                .start();
    }

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i)).append(",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }


    /**
     * 加载更多
     *
     * @param adapter
     * @param onLoadMoreListener
     */
    public static void initLoadMore(BaseQuickAdapter adapter, OnLoadMoreListener onLoadMoreListener) {
        adapter.getLoadMoreModule();
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setAutoLoadMore(true);
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);
        adapter.getLoadMoreModule().setEnableLoadMoreEndClick(false);
        adapter.getLoadMoreModule().setPreLoadNumber(1);
        adapter.getLoadMoreModule().setOnLoadMoreListener(onLoadMoreListener);
    }

    /**
     * 解析本地json
     *
     * @param raw json地址
     * @return json数据
     */
    public static String getJson(int raw) {
        InputStream stream = ActivityUtils.getTopActivity().getResources().openRawResource(raw);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder jsonStr = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr.toString();
    }

    /**
     * 设置空置页面
     *
     * @param activity
     * @param resId
     * @param text
     * @param viewGroup
     * @param onClickListener
     * @return
     */
    public static View getEmptyView(Activity activity, int resId, String text, ViewGroup viewGroup, View.OnClickListener onClickListener) {
        View emptyView = LayoutInflater.from(activity).inflate(R.layout.empty_view, viewGroup);
        ConstraintLayout clEmpty = emptyView.findViewById(R.id.cl_empty);
        ImageView imgEmpty = emptyView.findViewById(R.id.img_empty);
        TextView tvEmpty = emptyView.findViewById(R.id.tv_empty);
        if (onClickListener != null) {
            clEmpty.setOnClickListener(onClickListener);
        }
        if (resId != 0) {
            imgEmpty.setImageResource(resId);
        }
        if (text != null) {
            tvEmpty.setText(text);
        }
        return emptyView;
    }

    /**
     * 设置空置页面
     *
     * @param activity
     * @param viewGroup
     * @param onClickListener
     * @return
     */
    public static View getGoWorkEmptyView(Activity activity, ViewGroup viewGroup, View.OnClickListener onClickListener) {
        View emptyView = LayoutInflater.from(activity).inflate(R.layout.go_work_empty, viewGroup);
        TextView tvRetry = emptyView.findViewById(R.id.tv_re);
        if (onClickListener != null) {
            tvRetry.setOnClickListener(onClickListener);
        }
        return emptyView;

    }

    public static String list2String(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i)).append(",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }
}
