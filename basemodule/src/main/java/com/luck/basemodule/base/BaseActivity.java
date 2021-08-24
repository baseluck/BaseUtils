package com.luck.basemodule.base;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.basemodule.R;
import com.luck.basemodule.databinding.ActionbarLayoutBinding;
import com.luck.basemodule.view.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @描述: BaseActivity
 * @创建日期: 2021/4/13 11:45
 * @author: ProcyonLotor
 */
public abstract class BaseActivity<VM extends AndroidViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {
    protected VM mViewModel;
    protected VDB mViewDataBind;
    protected LoadingDialog loadingDialog;
    protected ActionbarLayoutBinding actionbarLayoutBinding;
    private String title;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        loadingDialog = new LoadingDialog(this, "加载中");

        mViewDataBind = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBind.setLifecycleOwner(this);

        BarUtils.setStatusBarColor(this, 0xff1094f5);

        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType)
                Objects.requireNonNull(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        mViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(vmClass);


        setCustomActionBar();
        afterCreate(savedInstanceState);

    }

    /**
     * 布局
     *
     * @return 布局页面Id
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 具体代码
     *
     * @param savedInstanceState
     */
    protected abstract void afterCreate(Bundle savedInstanceState);

    /**
     * 标题
     *
     * @return 标题
     */
    protected abstract int initTitle();

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public void showLoading(CharSequence message) {
        if (loadingDialog != null) {
            loadingDialog.setMessage(message);
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    public void setTabTitle(CharSequence title) {
        if (actionbarLayoutBinding != null) {
            actionbarLayoutBinding.tvTitle.setText(title);
            actionbarLayoutBinding.tvTitle.setTextColor(0xffffffff);

        }
    }

    public void updateLoadingMsg(CharSequence message) {
        if (loadingDialog != null) {
            loadingDialog.updateMsg(message);
        }
    }


    public void dismissLoading() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    public void showToast(CharSequence toast) {
        ToastUtils.showShort(toast);
    }


    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        actionbarLayoutBinding = DataBindingUtil.bind(mActionBarView);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            if (actionbarLayoutBinding != null) {
                actionbarLayoutBinding.ivBack.setOnClickListener(v -> onBackPressed());
                if (initTitle() != 0) {
                    actionbarLayoutBinding.tvTitle.setText(StringUtils.getString(initTitle()));
                    actionbarLayoutBinding.tvTitle.setTextColor(0xffffffff);
                }
            }
            this.getWindow().setStatusBarColor(0xff1094f5);
        }
    }

    public void showRightImgAndOnClickListener(int imgId, View.OnClickListener onClickListener) {
        if (actionbarLayoutBinding != null) {
            actionbarLayoutBinding.ivRight.setVisibility(View.VISIBLE);
            if (imgId != 0) {
                Glide.with(this)
                        .load(imgId)
                        .into(actionbarLayoutBinding.ivRight);
            }
            actionbarLayoutBinding.ivRight.setOnClickListener(onClickListener);
        }
    }


    public void showRightTextAndOnClickListener(String right, View.OnClickListener onClickListener) {
        if (actionbarLayoutBinding != null) {
            actionbarLayoutBinding.tvRight.setVisibility(View.VISIBLE);
            actionbarLayoutBinding.tvRight.setTextColor(0xffffffff);
            if (!StringUtils.isEmpty(right)) {
                actionbarLayoutBinding.tvRight.setText(right);
            } else {
                actionbarLayoutBinding.tvRight.setText("确定");
            }
            actionbarLayoutBinding.tvRight.setOnClickListener(onClickListener);
        }
    }

    public void showRightText(String right) {
        if (actionbarLayoutBinding != null) {
            actionbarLayoutBinding.tvRight.setVisibility(View.VISIBLE);
            actionbarLayoutBinding.tvRight.setTextColor(0xffffffff);
            if (!StringUtils.isEmpty(right)) {
                actionbarLayoutBinding.tvRight.setText(right);
            } else {
                actionbarLayoutBinding.tvRight.setText("确定");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}