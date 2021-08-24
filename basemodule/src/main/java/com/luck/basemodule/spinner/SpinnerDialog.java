package com.luck.basemodule.spinner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.luck.basemodule.R;

import java.util.ArrayList;


/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/21 13:36
 * @描述:
 */

public class SpinnerDialog {
    ArrayList<String> items;
    Activity context;
    String dTitle, closeTitle = "关闭";
    OnSpinerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    int pos;
    int style;
    boolean cancellable = false;
    boolean showKeyboard = false;
    boolean useContainsFilter = false;

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle = closeTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }

    public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle = closeTitle;
    }

    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.spinner_dialog_layout, null);
        TextView rippleViewClose = v.findViewById(R.id.close);
        TextView title = v.findViewById(R.id.spinerTitle);
        ImageView searchIcon = v.findViewById(R.id.searchIcon);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        final ListView listView = v.findViewById(R.id.list);
        ColorDrawable sage = new ColorDrawable(context.getResources().getColor(R.color.colorLightGray));
        listView.setDivider(sage);
        listView.setDividerHeight(1);
        final EditText searchBox = v.findViewById(R.id.searchBox);
        if (isShowKeyboard()) {
            showKeyboard(searchBox);
        }
        final MySpinnerAdapter adapter = new MySpinnerAdapter(context, R.layout.items_view, items) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(R.id.text1);
                return view;
            }
        };
        listView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView t = view.findViewById(R.id.text1);
            for (int j = 0; j < items.size(); j++) {
                if (t.getText().toString().equalsIgnoreCase(items.get(j))) {
                    pos = j;
                }
            }
            onSpinerItemClick.onClick(t.getText().toString(), pos);
            closeSpinerDialog();
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isUseContainsFilter()) {
                    adapter.getContainsFilter(searchBox.getText().toString());
                } else {
                    adapter.getFilter().filter(searchBox.getText().toString());
                }
            }
        });
        rippleViewClose.setOnClickListener(v1 -> closeSpinerDialog());
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();
    }

    public void closeSpinerDialog() {
        hideKeyboard();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignored) {

        }
    }

    private void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(() -> {
                    InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(ettext, 0);
                }
                , 200);
    }

    private boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    private boolean isShowKeyboard() {
        return showKeyboard;
    }

    private boolean isUseContainsFilter() {
        return useContainsFilter;
    }


    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    public void setUseContainsFilter(boolean useContainsFilter) {
        this.useContainsFilter = useContainsFilter;
    }
}
