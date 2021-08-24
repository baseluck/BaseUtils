package com.yhzc.schooldormitorymobile.ui.face;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityFaceBinding;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/20 11:34
 * @描述: 人脸认证注册
 */
public class FaceActivity extends BaseActivity<FaceViewModel, ActivityFaceBinding> {


    private ImageCapture mImageCapture;
    private Bitmap mBitmap;


    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE

    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_face;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(NEEDED_PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        startCamera();
                    } else {
                        ToastUtils.showShort("获取权限失败");
                    }
                });

        mViewDataBind.tvTake.setOnClickListener(v -> takePic());

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

                cameraProvider.unbindAll();

                Camera camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        mImageCapture
                );


                preview.setSurfaceProvider(mViewDataBind.pv.getSurfaceProvider());


            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));


    }

    private void takePic() {
        if (mImageCapture != null) {
            mImageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
                @Override
                public void onCaptureSuccess(@NonNull @NotNull ImageProxy image) {
                    super.onCaptureSuccess(image);

                    mBitmap = imageProxyToBitmap(image);
                    Glide.with(mViewDataBind.iv)
                            .load(mBitmap)
                            .into(mViewDataBind.iv);
                }
            });
        }
    }

    private void upLoad(){

    }

    public Bitmap rotateMyBitmap(Bitmap bmp) {
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postRotate(90);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return rotateMyBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null));
    }

    @Override
    protected int initTitle() {
        return R.string.face;
    }

}