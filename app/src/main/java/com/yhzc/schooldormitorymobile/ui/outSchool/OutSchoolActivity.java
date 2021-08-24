package com.yhzc.schooldormitorymobile.ui.outSchool;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityOutSchoolBinding;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/28 10:19
 * @描述: 出校登记
 */
public class OutSchoolActivity extends BaseActivity<OutSchoolViewModel, ActivityOutSchoolBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_out_school;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNfc();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        mViewDataBind.ivCard.getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            int left1 = oldFocus.getLeft();
            int left2 = newFocus.getLeft();
        });

        mViewDataBind.ivPhone.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int left = mViewDataBind.ivPhone.getLeft();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mViewDataBind.ivCard, "translationX", (-left));
                objectAnimator.setInterpolator(new LinearInterpolator());
                objectAnimator.setDuration(2000);
                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                objectAnimator.start();
                mViewDataBind.ivPhone.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.out_school;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String s = processIntent(intent);
        if (!StringUtils.isEmpty(s)) {
            Intent intentDetails = new Intent(this, OutSchoolDetailsActivity.class);
            intentDetails.putExtra("idcard", s);
            ActivityUtils.startActivity(intentDetails);
        }

    }

    private String processIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        return ByteArrayToHexString(tagFromIntent.getId());
    }

    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F"};
        StringBuilder out = new StringBuilder();


        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out.append(hex[i]);
            i = in & 0x0f;
            out.append(hex[i]);
        }
        return out.toString();
    }

    private void initNfc() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (null == adapter) {
            Toast.makeText(this, "不支持NFC功能", Toast.LENGTH_SHORT).show();
        } else if (!adapter.isEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(intent);
        }

        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        String[][] mTechLists = new String[][]{new String[]{NfcB.class.getName()}, new String[]{NfcA.class.getName()}};

        if (adapter != null && !adapter.isEnabled()) {
            Toast.makeText(this, "NFC尚未开启", Toast.LENGTH_SHORT).show();
        }
        if (adapter != null) {
            adapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected}, mTechLists);
        }
    }
}