package com.luck.basemodule.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.R;

import java.lang.ref.WeakReference;


/**
 * @描述: 自定义加载框
 * @创建日期: 2021/4/13 11:42
 * @author: ProcyonLotor
 */
public class LoadingDialog extends Dialog {

    private final TextView tvMessage;

    public LoadingDialog(@NonNull Context context, CharSequence message) {
        super(context, R.style.CustomProgressDialog);
        WeakReference<Context> context1 = new WeakReference<>(context);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        tvMessage = view.findViewById(R.id.tv_message);
        ImageView imageView = view.findViewById(R.id.progress_bar);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 720f);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(1500);
        //无限循环
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
        if (!StringUtils.isEmpty(message)) {
            tvMessage.setText(message);
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);
    }


    public void updateMsg(CharSequence updateMsg) {
        if (!StringUtils.isEmpty(updateMsg) && ObjectUtils.isNotEmpty(tvMessage)) {
            tvMessage.setText(updateMsg);
        }
    }


    public void setMessage(CharSequence message) {
        if (!StringUtils.isEmpty(message) && ObjectUtils.isNotEmpty(tvMessage)) {
            tvMessage.setText(message);
        }
    }

    public void showDialog() {
        show();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
