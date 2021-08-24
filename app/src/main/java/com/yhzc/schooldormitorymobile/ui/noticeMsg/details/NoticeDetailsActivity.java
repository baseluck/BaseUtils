package com.yhzc.schooldormitorymobile.ui.noticeMsg.details;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lzy.okgo.model.Progress;
import com.previewlibrary.ZoomMediaLoader;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.luck.basemodule.utils.PictureUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityNoticeDetailsBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.NoticeViewModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.SelectImgAdapter;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;
import com.luck.basemodule.utils.UrlImageLoader;
import com.luck.basemodule.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/22 22:24
 * @描述: 通知详情
 */
public class NoticeDetailsActivity extends BaseActivity<NoticeViewModel, ActivityNoticeDetailsBinding> {

    private static final String JS = "<script type=\"text/javascript\">" +
            "var imgs = document.getElementsByTagName('img');" +
            "for(var i = 0; i<imgs.length; i++){" +
            "imgs[i].style.width = '100%';" +
            "imgs[i].style.height = 'auto';" +
            "    imgs[i].onclick=function()  " +
            "    {  " +
            "        window.imagelistener.openImage(this.src);  " +
            "    }  " +
            "}" +
            "</script>";

    private String id;

    private final List<String> images = new ArrayList<>();
    private SelectImgAdapter mSelectImgAdapter;

    private RxPermissions mRxPermissions;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_details;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        showLoading("正在加载");
        mRxPermissions = new RxPermissions(this);
        id = getIntent().getStringExtra("id");
        ZoomMediaLoader.getInstance().init(new UrlImageLoader(Cache.getHttp()));
        mViewDataBind.clHf.setVisibility(View.GONE);
        ImageAdapter adapter = new ImageAdapter(R.layout.item_notice_img, null);
        mViewDataBind.rvImg.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvImg.setAdapter(adapter);
        ReplyAdapter mReplyAdapter = new ReplyAdapter(R.layout.item_notice_hf, null);
        mViewDataBind.rvFh.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvFh.setAdapter(mReplyAdapter);
        mReplyAdapter.setImageClick(Utils::showBigImg);
        initImgRv();
        mViewModel.initDetails(this, id);
        mViewModel.getNoticeDetailsModel().observe(this, noticeDetailsModel -> {
            dismissLoading();
            if (noticeDetailsModel != null) {
                mViewDataBind.tvTitle.setText(noticeDetailsModel.getData().getVcTitle());
                mViewDataBind.tvPublisher.setText(String.format(StringUtils.getString(R.string.author), noticeDetailsModel.getData().getVcCreateUserId()));
                mViewDataBind.tvPublishTime.setText(String.format(StringUtils.getString(R.string.add_time), noticeDetailsModel.getData().getDtSendTime()));
                if (noticeDetailsModel.getData().getVcImg() != null) {
                    adapter.setList(noticeDetailsModel.getData().getVcImg());
                }
                if (noticeDetailsModel.getData().getReplyList() != null) {
                    mReplyAdapter.setList(noticeDetailsModel.getData().getReplyList());
                }
                if (StringUtils.isEmpty(noticeDetailsModel.getData().getVcVideo())) {
                    mViewDataBind.jz.setVisibility(View.GONE);
                } else {
                    mViewDataBind.jz.setVisibility(View.VISIBLE);
                    mViewDataBind.jz.setUp(noticeDetailsModel.getData().getVcVideo(), "");
                }
                mViewDataBind.wvContent.loadData((EncodeUtils.htmlDecode(noticeDetailsModel.getData().getVcContent()) + JS)
                        .replace("&amp;zoom=600w", ""), "text/html;charset=utf-8", null);
                if (noticeDetailsModel.getData().getIsReply() == 1) {
                    mViewDataBind.tvHf.setVisibility(View.VISIBLE);
                } else {
                    mViewDataBind.tvHf.setVisibility(View.GONE);
                }
            }
        });
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<String> list = (List<String>) adapter1.getData();
            Utils.showBigImg(list, position);
        });
        mViewDataBind.tvHf.setOnClickListener(v -> {
            if (mViewDataBind.clHf.getVisibility() == View.GONE) {
                mViewDataBind.clHf.setVisibility(View.VISIBLE);
            } else {
                mViewDataBind.clHf.setVisibility(View.GONE);
            }
        });
        mViewDataBind.tvSend.setOnClickListener(v -> upLoadImage());
    }

    private void initImgRv() {
        mSelectImgAdapter = new SelectImgAdapter(R.layout.item_notice_select_img, null);
        mViewDataBind.rvHfImg.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBind.rvHfImg.setAdapter(mSelectImgAdapter);
        mViewDataBind.tvAddHfImg.setOnClickListener(v -> mRxPermissions.request(NEEDED_PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        List<File> files = mViewModel.getImages().getValue();
                        if (files == null) {
                            seletedImage();
                        } else {
                            if (files.size() < 3) {
                                seletedImage();
                            } else {
                                ToastUtils.showShort("最多选择3张");
                            }
                        }
                    } else {
                        ToastUtils.showShort("无法获取权限");
                    }
                }));
        mViewModel.getImages().observe(this, files -> {
            if (files != null) {
                mSelectImgAdapter.setList(files);
            }
        });
        mSelectImgAdapter.addChildClickViewIds(R.id.iv_delete, R.id.iv_img);
        mSelectImgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<File> data = adapter.getData();
            if (view.getId() == R.id.iv_delete) {
                data.remove(position);
                mViewModel.setImages(data);
            }
        });
    }

    private void seletedImage() {
        PictureUtils.createImage(this, 9, new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                List<File> imageFileList = PictureUtils.getImageFileList(result);
                List<File> files = mViewModel.getImages().getValue();
                if (files == null) {
                    files = new ArrayList<>(imageFileList);
                    mViewModel.setImages(files);
                } else {
                    if (files.size() + imageFileList.size() > 3) {
                        ToastUtils.showShort("最多3张");
                    } else {
                        files.addAll(imageFileList);
                        mViewModel.setImages(files);
                    }
                }
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("取消选择");
            }
        });
    }

    private void upLoadImage() {
        showLoading("正在提交");
        images.clear();
        List<File> files = mViewModel.getImages().getValue();
        if (files != null && files.size() != 0) {
            updateLoadingMsg("正在上传图片");
            OkGoUtils.upFiles(ApiUrl.UPLOADIMG, files, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpLoadBean upLoadBean = GsonUtils.fromJson(callback, UpLoadBean.class);
                    if (upLoadBean.getCode() == ApiUrl.SUCCESS) {
                        if (upLoadBean.getData() != null && upLoadBean.getData().size() != 0) {
                            for (UpLoadBean.DataBean dataBean : upLoadBean.getData()) {
                                images.add(dataBean.getUrl());
                            }
                            sendHf();
                        } else {
                            dismissLoading();
                            ToastUtils.showShort("图片地址不正确");
                        }
                    }
                }

                @Override
                protected void onError(String error) {
                    dismissLoading();
                    ToastUtils.showShort(error);
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    int p = (int) (progress.fraction * 100);
                    updateLoadingMsg("正在上传图片：" + p + "%");
                }
            });
        } else {
            sendHf();
        }
    }

    private void sendHf() {
        if (StringUtils.isEmpty(mViewDataBind.etHf.getText().toString().trim())) {
            ToastUtils.showShort("请输入回复内容");
            return;
        }
        Map<String, Object> map = new HashMap<>(5);
        map.put("vcNoticeId", id);
        map.put("vcContent", mViewDataBind.etHf.getText().toString().trim());
        if (images.size() != 0) {
            StringBuilder str2 = new StringBuilder();
            for (Iterator<String> iterator = images.iterator(); iterator.hasNext(); ) {
                String string = iterator.next();
                str2.append(string);
                if (iterator.hasNext()) {
                    str2.append(",");
                }
            }
            map.put("jsonPic", images);
        }

        OkGoUtils.post(ApiUrl.REPLY, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("回复成功");
                    mViewModel.initDetails(NoticeDetailsActivity.this, id);
                    mViewDataBind.clHf.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected int initTitle() {
        return R.string.tzxq;
    }
}