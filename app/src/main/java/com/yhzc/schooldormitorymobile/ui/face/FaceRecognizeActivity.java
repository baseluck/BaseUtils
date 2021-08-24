package com.yhzc.schooldormitorymobile.ui.face;

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

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
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
import com.yhzc.schooldormitorymobile.ui.home.fragment.mine.UpImageBean;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.OkGoUtils;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/1 10:23
 * @描述:
 */
@SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming",
        "AlibabaUndefineMagicConstant",
        "AlibabaMethodTooLong"})
public class FaceRecognizeActivity extends BaseActivity<FaceViewModel, ActivityFaceRecognizeBinding> {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_face_recognize;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

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

    @SuppressLint("UnsafeOptInUsageError")
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

                mImageAnalysis
                        .setTargetRotation(Surface.ROTATION_180);

                mImageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageProxy -> {

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

    private void sendImage() {
        mImageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull @NotNull ImageProxy image) {
                super.onCaptureSuccess(image);

            }

            @Override
            public void onError(@NonNull @NotNull ImageCaptureException exception) {
                super.onError(exception);
            }
        });
    }


    private void initHt(ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError")
        Image image = imageProxy.getImage();
        assert image != null;
        String base64 = NV21toBase64(YUV420toNV21(image), image.getWidth(), image.getHeight(), 100);

        Map<String, String> map = new HashMap<>(16);
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
                                    if (StringUtils.equals(userListBean.getUser_info(), Cache.getLogin().getData().getVcRealName())) {
                                        if (userListBean.getScore() > 80) {
                                            upFace(image);
                                            mImageAnalysis.clearAnalyzer();
                                            mViewDataBind.tvFace.setText("认证成功");
                                        } else {
                                            mViewDataBind.tvFace.setText("人脸置信度低");
                                            imageProxy.close();
                                        }
                                    }
                                }
                            } else {
                                mViewDataBind.tvFace.setText("未找到人脸信息");
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
                            imageProxy.close();
                        }
                    }
                });
    }

    private void searchBaiduFace(byte[] nv21, int width, int height) {

        String base64 = NV21toBase64(nv21, width, height, 100);

        Map<String, String> map = new HashMap<>(16);
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
                                    if (StringUtils.equals(userListBean.getUser_info(), Cache.getLogin().getData().getVcRealName())) {
                                        if (userListBean.getScore() > 80) {
                                            updateFace(nv21, width, height);
                                            mImageAnalysis.clearAnalyzer();
                                            mViewDataBind.tvFace.setText("认证成功");
                                        } else {
                                            mViewDataBind.tvFace.setText("人脸置信度低");
                                            toBd = true;
                                        }
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


    private String toBase64(ImageProxy imageProxy) {
        ImageProxy.PlaneProxy[] planes = imageProxy.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, mSize.getWidth(), mSize.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private String toBase642(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private Bitmap toBipmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    private Bitmap toBitmap2(ImageProxy image) {
        ImageProxy.PlaneProxy[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


    private void upFace(Image image) {
        byte[] bytes = NV21toJPEG(YUV420toNV21(Objects.requireNonNull(image)), image.getWidth(), image.getHeight(), 50);
        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
        File file = new File(getExternalFilesDir("pic") + "/" + nowString + ".png");
        boolean b = FileIOUtils.writeFileFromBytesByChannel(file, bytes, true);
        if (b) {
            OkGoUtils.upFile(ApiUrl.UPFACE, file, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpImageBean upImageBean = GsonUtils.fromJson(callback, UpImageBean.class);
                    if (upImageBean.getCode() == ApiUrl.SUCCESS) {
                        dk(upImageBean.getData().getUrl());
                        image.close();
                    }
                }

                @Override
                protected void onError(String error) {
                    ToastUtils.showShort(error);
                }
            });
        }

    }

    private void updateFace(byte[] nv21, int width, int height) {
        byte[] bytes = NV21toJPEG(nv21, width, height, 50);
        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
        File file = new File(getExternalFilesDir("pic") + "/" + nowString + ".png");
        boolean b = FileIOUtils.writeFileFromBytesByChannel(file, bytes, true);
        if (b) {

            OkGoUtils.upFile(ApiUrl.UPFACE, file, new BaseCallback() {
                @Override
                protected void onSuccess(String callback) {
                    UpImageBean upImageBean = GsonUtils.fromJson(callback, UpImageBean.class);
                    if (upImageBean.getCode() == ApiUrl.SUCCESS) {
                        dk(upImageBean.getData().getUrl());
                    }
                }

                @Override
                protected void onError(String error) {
                    ToastUtils.showShort(error);
                }
            });

        }

    }

    private void savePic(byte[] nv21, int width, int height) {
        byte[] bytes = NV21toJPEG(nv21, width, height, 50);
        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));
        File file = new File(getExternalFilesDir("pic") + "/" + nowString + ".png");
        boolean b = FileIOUtils.writeFileFromBytesByChannel(file, bytes, true);

    }

    private void dk(String picUrl) {

        Map<String, String> map = new HashMap<>();
        map.put("vcHeadIcon", picUrl);

        OkGoUtils.get(ApiUrl.DK, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                if (baseBean.getCode() == ApiUrl.SUCCESS) {
                    ToastUtils.showShort("打卡成功");
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

    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss"));

        File file = new File(getExternalFilesDir("headPic") + "/" + nowString + ".jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
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
                default:
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

    private byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
        int i = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                yuv[i] = data[y * imageWidth + x];
                i++;
            }
        }
        i = imageWidth * imageHeight * 3 / 2 - 1;
        for (int x = imageWidth - 1; x > 0; x = x - 2) {
            for (int y = 0; y < imageHeight / 2; y++) {
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + x];
                i--;
                yuv[i] = data[(imageWidth * imageHeight) + (y * imageWidth) + (x - 1)];
                i--;
            }
        }
        return yuv;
    }

    @Override
    protected int initTitle() {
        return R.string.face;
    }
}
