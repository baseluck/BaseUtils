package com.luck.basemodule.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;
import com.luck.basemodule.R;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/5/9 16:10
 * @描述: 大图展示
 */
public class UrlImageLoader implements IZoomMediaLoader {

    private final String http;

    public UrlImageLoader(String http) {
        this.http = http;
    }

    @Override
    public void displayImage(@NonNull Fragment fragment, @NonNull String s, ImageView imageView, @NonNull MySimpleTarget mySimpleTarget) {
        Glide.with(fragment)
                .asBitmap()
                .load(http + s)
                .error(R.mipmap.ic_img_error)
                .fitCenter()
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        mySimpleTarget.onLoadFailed(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        mySimpleTarget.onResourceReady();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void displayGifImage(@NonNull Fragment fragment, @NonNull String s, ImageView imageView, @NonNull MySimpleTarget mySimpleTarget) {
        Glide.with(fragment)
                .asGif()
                .load(http + s)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).dontAnimate())
                .listener(new RequestListener<GifDrawable>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                  mySimpleTarget.onLoadFailed(null);
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                  mySimpleTarget.onResourceReady();
                                  return false;
                              }
                          }
                )
                .into(imageView);

    }

    @Override
    public void onStop(@NonNull Fragment fragment) {
        Glide.with(fragment).onStop();
    }

    @Override
    public void clearMemory(@NonNull Context c) {
        Glide.get(c).clearMemory();
    }
}
