package com.luck.basemodule.utils;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.UriUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.basemodule.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * created by shuai
 * 2020-06-29
 *
 * @author ProcyonLotor
 */
public class PictureUtils {

    private final static String MIME_TYPE_JPG = "image/jpg";

    public static void create(Activity activity, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .isCompress(true)

                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .isCamera(true)
                .forResult(onResultCallbackListener);
    }

    public static void createTakePic(Activity activity, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .isCompress(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(onResultCallbackListener);
    }

    public static void createImage(Activity activity, int selectNum, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .isCompress(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(selectNum)
                .isCamera(true)
                .forResult(onResultCallbackListener);


    }

    public static void createVideo(Activity activity, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())
                .isCompress(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .maxVideoSelectNum(1)
                .isCamera(false)
                .forResult(onResultCallbackListener);


    }

    public static List<String> getImagePath(List<LocalMedia> result) {

        List<String> imagePaths = new ArrayList<>();
        String url = "";
        for (LocalMedia localMedia : result) {
            if (localMedia.getCompressPath() != null) {
                url = localMedia.getCompressPath();
            } else if (localMedia.getAndroidQToPath() != null) {
                url = localMedia.getAndroidQToPath();
            } else if (localMedia.getRealPath() != null) {
                url = localMedia.getRealPath();
            } else if (localMedia.getPath() != null) {
                url = localMedia.getPath();
            }
            imagePaths.add(url);
        }

        return imagePaths;
    }

    public static File getVideoFile(List<LocalMedia> result) {
        String url = result.get(0).getPath();
        File file;
        if (url.contains("content:")) {
            Uri uri = Uri.parse(url);
            file = UriUtils.uri2File(uri);
        } else {
            file = new File(url);
        }
        return file;
    }

    public static File url2VideoFile(String url) {
        File file;
        if (url.contains("content:")) {
            Uri uri = Uri.parse(url);
            file = UriUtils.uri2File(uri);
        } else {
            file = new File(url);
        }
        return file;
    }


    public static File getImageFile(List<LocalMedia> result) {

        String url = "";
        if (result.get(0).getCompressPath() != null) {
            url = result.get(0).getCompressPath();
        } else if (result.get(0).getAndroidQToPath() != null) {
            url = result.get(0).getAndroidQToPath();
        } else if (result.get(0).getRealPath() != null) {
            url = result.get(0).getRealPath();
        } else if (result.get(0).getPath() != null) {
            url = result.get(0).getPath();
        }
        File file;
        if (url.contains("content:")) {
            Uri uri = Uri.parse(url);
            file = UriUtils.uri2File(uri);
        } else {
            file = new File(url);
        }
        return file;
    }

    public static List<File> getImageFiles(List<String> result) {
        List<File> files = new ArrayList<>();
        for (String url : result) {
            File file;
            if (url.contains("content:")) {
                Uri uri = Uri.parse(url);
                file = UriUtils.uri2File(uri);
            } else {
                file = new File(url);
            }
            files.add(file);
        }
        return files;
    }

    public static List<File> getImageFileList(List<LocalMedia> result) {

        List<File> files = new ArrayList<>();
        for (LocalMedia localMedia : result) {
            String url = "";
            if (localMedia.getCompressPath() != null) {
                url = localMedia.getCompressPath();
            } else if (localMedia.getAndroidQToPath() != null) {
                url = localMedia.getAndroidQToPath();
            } else if (localMedia.getRealPath() != null) {
                url = localMedia.getRealPath();
            } else if (localMedia.getPath() != null) {
                url = localMedia.getPath();
            }
            File file;
            if (url.contains("content:")) {
                Uri uri = Uri.parse(url);
                file = UriUtils.uri2File(uri);
            } else {
                file = new File(url);
            }
            files.add(file);
        }
        return files;
    }


    private static PictureParameterStyle getPictureParameterStyle(Activity activity) {
        PictureParameterStyle mPictureParameterStyle = new PictureParameterStyle();
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        mPictureParameterStyle.isOpenCheckNumStyle = false;
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        mPictureParameterStyle.pictureContainerBackgroundColor = ContextCompat.getColor(activity, R.color.white);
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up;
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down;
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(activity, R.color.picture_color_white);
        mPictureParameterStyle.pictureRightDefaultTextColor = ContextCompat.getColor(activity, R.color.picture_color_white);
        mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_item_select_bg;
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector;
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(activity, R.color.picture_color_grey);
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval;
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(activity, R.color.picture_color_fa632d);
        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(activity, R.color.picture_color_white);
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(activity, R.color.picture_color_fa632d);
        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(activity, R.color.picture_color_white);
        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(activity, R.color.picture_color_grey);
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_wechat_checkbox;
        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(activity, R.color.white);
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e");
        return mPictureParameterStyle;
    }

    private static PictureCropParameterStyle getPictureCropParameterStyle(Activity activity) {

        return new PictureCropParameterStyle(
                ContextCompat.getColor(activity, R.color.app_color_grey),
                ContextCompat.getColor(activity, R.color.app_color_grey),
                Color.parseColor("#393a3e"),
                ContextCompat.getColor(activity, R.color.white),
                false);
    }
}
