package com.luck.basemodule.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.luck.basemodule.view.LoadingDialog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Description：
 * created by shuai
 * 2020-08-14
 * @author ProcyonLotor
 */
public abstract class BaseFragment<VM extends AndroidViewModel, VDB extends ViewDataBinding> extends Fragment {
    protected VM mViewModel;
    protected VDB mViewDataBind;
    protected LoadingDialog loadingDialog;
    protected Context mContext;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loadingDialog = new LoadingDialog(requireActivity(), "加载中");

        mViewDataBind = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mViewDataBind.setLifecycleOwner(this);
        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass()))
                .getActualTypeArguments()[0];
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(vmClass);
        return mViewDataBind.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreate(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 具体代码
     *
     * @param savedInstanceState
     */
    protected abstract void afterCreate(Bundle savedInstanceState);

    public void showLoading(CharSequence message) {
        loadingDialog.setMessage(message);
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
