package com.yhzc.schooldormitorymobile.ui.meeting.signin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Size;
import android.view.Surface;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.common.util.concurrent.ListenableFuture;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityFaceRecognizeBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.yhzc.schooldormitorymobile.ui.face.BaiduFaceModel;
import com.yhzc.schooldormitorymobile.ui.face.FaceViewModel;
import com.yhzc.schooldormitorymobile.ui.noticeMsg.newNotice.UpLoadBean;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/1 10:23
 * @描述:
 */
public class MeetingSignInFaceActivity extends BaseActivity<FaceViewModel, ActivityFaceRecognizeBinding> {

    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    private ImageCapture mImageCapture;
    private ImageAnalysis mImageAnalysis;
    private Size mSize;
    private boolean toBd = true;
    private String userName;
    private String vcMeetId;
    private String vcJoinUserId;
    private String intType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_recognize;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        vcMeetId = getIntent().getStringExtra("vcMeetId");
        userName = getIntent().getStringExtra("name");
        vcJoinUserId = getIntent().getStringExtra("vcJoinUserId");
        intType = getIntent().getStringExtra("intType");

        mViewDataBind.tvFace.setText("请正视摄像头");

        mSize = new Size(640, 480);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(NEEDED_PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        startCamera();
                    } else {
                        ToastUtils.showShort("没有权限");
                    }
                });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();

                mImageCapture = new ImageCapture.Builder()
                        .setTargetRotation(Surface.ROTATION_180)
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();


                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                mImageAnalysis = new ImageAnalysis.Builder()
                        .setTargetResolution(mSize)
                        .setTargetRotation(Surface.ROTATION_180)
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
                        .setImageQueueDepth(1)
                        .build();

                mImageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageProxy -> {

                    @SuppressLint("UnsafeOptInUsageError")
                    byte[] bytes = YUV420toNV21(Objects.requireNonNull(imageProxy.getImage()));
                    if (toBd) {
                        toBd = false;
                        searchBaiduFace(bytes, imageProxy.getWidth(), imageProxy.getHeight());
                    }
                    imageProxy.close();

                });


                Camera camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        mImageAnalysis,
                        mImageCapture
                );

                preview.setSurfaceProvider(mViewDataBind.pv.getSurfaceProvider());


            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));


    }

    private void searchBaiduFace(byte[] nv21, int width, int height) {

        String base64 = NV21toBase64(nv21, width, height, 100);

        Map<String, String> map = new HashMap<>();
        map.put("image", base64);
        map.put("liveness_control", "NORMAL");
        map.put("group_id_list", "ylzx");
        map.put("max_face_num", "1");
        map.put("match_threshold", "60");
        map.put("image_type", "BASE64");
        map.put("quality_control", "LOW");

        OkGo.<String>post(ApiUrl.BAIDUFACE)
                .params("access_token", Cache.getBaiduToken())
                .isSpliceUrl(true)
                .upJson(GsonUtils.toJson(map))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaiduFaceModel baiduFaceModel = GsonUtils.fromJson(response.body(), BaiduFaceModel.class);
                        if (baiduFaceModel.getError_code() == 0) {
                            if (baiduFaceModel.getResult().getUser_list().size() != 0) {
                                for (BaiduFaceModel.ResultBean.UserListBean userListBean : baiduFaceModel.getResult().getUser_list()) {
                                    if (StringUtils.equals(userListBean.getUser_info(), userName)) {
                                        if (userListBean.getScore() > 80) {
                                            updateFace(nv21, width, height);
                                            mImageAnalysis.clearAnalyzer();
                                            mViewDataBind.tvFace.setText("认证成功");
                                        } else {
                                            mViewDataBind.tvFace.setText("人脸置信度低");
                                            toBd = true;
                                        }
                                    }else {
                                        mViewDataBind.tvFace.setText("用户信息不匹配");
                                        toBd = true;
                                    }
                                }
                            } else {
                                mViewDataBind.tvFace.setText("未找到人脸信息");
                                toBd = true;
                            }

                        } else {
                            String msg;
                            switch (baiduFaceModel.getError_code()) {
                                case 222207:
                                    msg = "未找到匹配的用户";
                                    break;
                                case 223113:
                                    msg = "请勿遮挡面部";
                                    break;
                                case 223114:
                                    msg = "人脸模糊";
                                    break;
                                case 223115:
                                    msg = "光线不好";
                                    break;
                                case 223116:
                                    msg = "人脸不完整";
                                    break;
                                case 223120:
                                    msg = "活体检测未通过";
                                    break;
                                case 223121:
                                    msg = "请勿遮挡左眼";
                                    break;
                                case 223122:
                                    msg = "请勿遮挡右眼";
                                    break;
                                case 223123:
                                    msg = "请勿遮挡左脸颊";
                                    break;
                                case 223124:
                                    msg = "请勿遮挡右脸颊";
                                    break;
                                case 223125:
                                    msg = "请勿遮挡下巴";
                                    break;
                                case 223126:
                                    msg = "请勿遮挡鼻子";
                                    break;
                                case 223127:
                                    msg = "请勿遮挡嘴巴";
                                    break;
                                default:
                                    msg = "正在识别";
                                    break;
                            }
                            mViewDataBind.tvFace.setText(msg);
                            toBd = true;
                        }
                    }
                });
    }

    private void updateFace(byte[] nv21, int width, int height) {
        byte[] bytes = NV21toJPEG(nv21, width, height, 50);
        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
        File file = new File(getExternalFilesDir("pic") + "/" + nowString + ".png");
        boolean b = FileIOUtils.writeFileFromBytesByChannel(file, bytes, true);
        if (b) {
            List<File> files=new ArrayList<>();
            files.add(file);
            OkGoUtils.upFiles(ApiUrl.UPLOADIMG, files, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpLoadBean upLoadBean = GsonUtils.fromJson(callback, UpLoadBean.class);
                    if (upLoadBean.getCode() == ApiUrl.SUCCESS) {
                        if (upLoadBean.getData() != null && upLoadBean.getData().size() != 0) {
                            signIn(upLoadBean.getData().get(0).getUrl());
                        }
                    }
//                    UpImageBean upImageBean = GsonUtils.fromJson(callback, UpImageBean.class);
//                    if (upImageBean.getCode() == ApiUrl.SUCCESS) {
//                        signIn(upImageBean.getData().getUrl());
//                    }
                }

                @Override
                protected void onError(String error) {
                    ToastUtils.showShort(error);
                }
            });
        }

    }

    private void signIn(String picUrl) {
        Map<String, String> map = new HashMap<>();
        map.put("vcMeetId", vcMeetId);
        map.put("vcJoinUserId", vcJoinUserId);
        map.put("vcFaceImg", picUrl);
        map.put("intType", intType);

        OkGoUtils.post(ApiUrl.MEETINGSIGN, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("签到成功");
                    finish();
                }
            }

            @Override
            protected void onError(String error) {
                ToastUtils.showShort(error);
                finish();
            }
        });
    }

    private static byte[] NV21toJPEG(byte[] nv21, int width, int height, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        YuvImage yuv = new YuvImage(nv21, ImageFormat.NV21, width, height, null);

        yuv.compressToJpeg(new Rect(0, 0, width, height), quality, out);

        return out.toByteArray();

    }

    private static String NV21toBase64(byte[] nv21, int width, int height, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuv = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
        yuv.compressToJpeg(new Rect(0, 0, width, height), quality, out);
        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    private static byte[] YUV420toNV21(Image image) {
        Rect crop = image.getCropRect();
        int format = image.getFormat();
        int width = crop.width();
        int height = crop.height();
        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[width * height * ImageFormat.getBitsPerPixel(format) / 8];
        byte[] rowData = new byte[planes[0].getRowStride()];
        int channelOffset = 0;
        int outputStride = 1;
        for (int i = 0; i < planes.length; i++) {
            switch (i) {
                case 0:
                    channelOffset = 0;
                    outputStride = 1;
                    break;
                case 1:
                    channelOffset = width * height + 1;
                    outputStride = 2;
                    break;
                case 2:
                    channelOffset = width * height;
                    outputStride = 2;
                    break;
            }
            ByteBuffer buffer = planes[i].getBuffer();
            int rowStride = planes[i].getRowStride();
            int pixelStride = planes[i].getPixelStride();
            int shift = (i == 0) ? 0 : 1;
            int w = width >> shift;
            int h = height >> shift;
            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
            for (int row = 0; row < h; row++) {
                int length;
                if (pixelStride == 1 && outputStride == 1) {
                    length = w;
                    buffer.get(data, channelOffset, length);
                    channelOffset += length;
                } else {
                    length = (w - 1) * pixelStride + 1;
                    buffer.get(rowData, 0, length);
                    for (int col = 0; col < w; col++) {
                        data[channelOffset] = rowData[col * pixelStride];
                        channelOffset += outputStride;
                    }
                }
                if (row < h - 1) {
                    buffer.position(buffer.position() + rowStride - length);
                }
            }
        }
        return data;
    }

    @Override
    protected int initTitle() {
        return R.string.face;
    }
}
