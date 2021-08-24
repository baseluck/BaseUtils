package com.yhzc.schooldormitorymobile.ui.InSchool;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.bean.BaseBean;
import com.yhzc.schooldormitorymobile.databinding.ActivityInSchoolBinding;
import com.yhzc.schooldormitorymobile.http.ApiUrl;
import com.luck.basemodule.base.BaseCallback;
import com.luck.basemodule.utils.OkGoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/28 10:19
 * @描述: 入校
 */
public class InSchoolActivity extends BaseActivity<InSchoolViewModel, ActivityInSchoolBinding> {

    private int left;
    private String cardNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_school;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNfc();
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.tvName.setText(String.format(StringUtils.getString(R.string.in_name), ""));
        mViewDataBind.tvSex.setText(String.format(StringUtils.getString(R.string.in_sex), ""));
        mViewDataBind.tvIdcard.setText(String.format(StringUtils.getString(R.string.in_grade), ""));
        mViewDataBind.tvAddress.setText(String.format(StringUtils.getString(R.string.in_class), ""));

        mViewModel.getStudentModel().observe(this, studentModel -> {
            mViewDataBind.tvName.setText(String.format(StringUtils.getString(R.string.in_name), studentModel.getData().getVcRealName()));
            mViewDataBind.tvSex.setText(String.format(StringUtils.getString(R.string.in_sex), studentModel.getData().getVcSex()));
            mViewDataBind.tvIdcard.setText(String.format(StringUtils.getString(R.string.in_grade), studentModel.getData().getVcGradeId()));
            mViewDataBind.tvAddress.setText(String.format(StringUtils.getString(R.string.in_class), studentModel.getData().getVcClassId()));
            cardNum = studentModel.getData().getVcGeneralCardNum();
            Glide.with(this)
                    .load(studentModel.getData().getVcIdPhoto())
                    .into(mViewDataBind.ivHead);
        });


        mViewDataBind.tvSend.setOnClickListener(v -> sendInSchool());

        showRightImgAndOnClickListener(0, v -> ActivityUtils.startActivity(InSchoolListActivity.class));

    }

    private void sendInSchool() {
        if (StringUtils.isEmpty(cardNum)) {
            ToastUtils.showShort("未获取到卡号");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("vcIcCardNo", cardNum);
        map.put("vcDorm", mViewDataBind.etSs.getText().toString().trim());
        map.put("vcPhone", mViewDataBind.etLxdh.getText().toString().trim());
        map.put("textMark", mViewDataBind.etBz.getText().toString().trim());

        showLoading("正在提交");

        OkGoUtils.post(ApiUrl.STUDENTIN, map, new BaseCallback() {
            @Override
            protected void onSuccess(String callback) {
                dismissLoading();
                BaseBean baseBean = GsonUtils.fromJson(callback, BaseBean.class);
                ToastUtils.showShort(baseBean.getMsg());
                ActivityUtils.startActivity(InSchoolListActivity.class);
            }

            @Override
            protected void onError(String error) {
                dismissLoading();
                ToastUtils.showShort(error);
            }
        });
    }

    @Override
    protected int initTitle() {
        return R.string.in_school;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String s = processIntent(intent);
        if (!StringUtils.isEmpty(s)) {
            mViewModel.initStudent(s);
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