package com.yhzc.schooldormitorymobile.ui.signIn.goWork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.luck.basemodule.base.BaseFragment;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.FragmentGoWordBinding;
import com.yhzc.schooldormitorymobile.ui.face.FaceRecognizeActivity;
import com.yhzc.schooldormitorymobile.ui.faceRegister.FaceRegisterActivity;
import com.yhzc.schooldormitorymobile.ui.signIn.SignInAdapter;
import com.yhzc.schooldormitorymobile.ui.signIn.SignInModel;
import com.yhzc.schooldormitorymobile.ui.signIn.SignInViewModel;
import com.yhzc.schooldormitorymobile.utils.Cache;
import com.luck.basemodule.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @描述:
 * @创建日期: 2021/4/13 15:17
 * @author: ProcyonLotor
 */
public class GoWorkFragment extends BaseFragment<SignInViewModel, FragmentGoWordBinding> implements
        GeoFenceListener {
    public static final int FENCESIZE = 3;
    private GeoFenceClient fenceClient = null;
    private int activatesAction;
    private final List<LatLng> polygonPoints = new ArrayList<>();
    private boolean canDk = false;
    private static final String GEOFENCE_BROADCAST_ACTION = "com.example.geofence.round";

    public static GoWorkFragment newInstance(Bundle bundle) {
        GoWorkFragment fragment = new GoWorkFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_go_word;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.initSignList(requireContext());
        canDk = false;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBind.slSignIn.setClickable(false);
        init();
        mViewModel.getAMapLocation().observe(this, aMapLocation -> mViewDataBind.tvLocation.setText(aMapLocation.getAddress()));
        SignInAdapter adapter = new SignInAdapter(R.layout.item_need_sign_in, null);
        mViewDataBind.rvNeedSign.setLayoutManager(new LinearLayoutManager(requireContext()));
        mViewDataBind.rvNeedSign.setAdapter(adapter);
        mViewModel.getSignInModel().observe(this, signInModel -> {
            adapter.setEmptyView(Utils.getGoWorkEmptyView(requireActivity(), null, v -> mViewModel.initSignList(requireContext())));
            if (signInModel.getData().getSignList() != null) {
                adapter.setList(signInModel.getData().getSignList());
                if (signInModel.getData().getSignList().size() != 0) {
                    can(signInModel.getData().getSignList());
                }
            }

            if (signInModel.getData().getSignLocation() != null) {
                addRoundFence(signInModel);
                return;
            }

            if (signInModel.getData().getMultiPointLocation() != null && signInModel.getData().getMultiPointLocation().size() >= FENCESIZE) {
                addPolygonFence(signInModel);
            }

        });


        mViewDataBind.slSignIn.setOnClickListener(v -> {
            if (StringUtils.isEmpty(Cache.getLogin().getData().getVcFacePic())) {
                ActivityUtils.startActivity(FaceRegisterActivity.class);
            } else {
                ActivityUtils.startActivity(FaceRecognizeActivity.class);
            }
        });


    }

    private void can(List<SignInModel.DataBean.SignListBean> signListBeans) {
        int i = 0;
        while (!canDk && i < signListBeans.size()) {
            canDk(signListBeans.get(i).getSignTime());
            i++;
        }
    }

    private void canDk(String timeRange) {
        String[] split = timeRange.split("-");
        String time = split[0];
        String time1 = split[1];
        String[] split1 = time.split(":");
        String[] split2 = time1.split(":");
        int start1 = Integer.parseInt(split1[0]);
        int start2 = Integer.parseInt(split1[1]);
        int end1 = Integer.parseInt(split2[0]);
        int end2 = Integer.parseInt(split2[1]);
        int start = start1 * 60 + start2;
        int end = end1 * 60 + end2;
        String nowString = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("HH:mm"));
        String[] split3 = nowString.split(":");
        int now1 = Integer.parseInt(split3[0]);
        int now2 = Integer.parseInt(split3[1]);
        int now = now1 * 60 + now2;
        canDk = now >= start && now <= end;
    }

    private void init() {
        activatesAction |= GeoFenceClient.GEOFENCE_IN;
        activatesAction |= GeoFenceClient.GEOFENCE_OUT;
        activatesAction |= GeoFenceClient.GEOFENCE_STAYED;
        fenceClient = new GeoFenceClient(requireContext());
        fenceClient.setActivateAction(activatesAction);
        mViewDataBind.tvLocation.setText("正在定位");
        IntentFilter filter = new IntentFilter();
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        requireActivity().registerReceiver(mGeoFenceReceiver, filter);
        fenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        fenceClient.setGeoFenceListener(this);
    }

    private void addRoundFence(SignInModel signInModel) {
        double latitude = Double.parseDouble(signInModel.getData().getSignLocation().getLatitude());
        double longitude = Double.parseDouble(signInModel.getData().getSignLocation().getLongitude());
        float fenceRadius = signInModel.getData().getSignLocation().getRadius();
        Random r = new Random(1);
        int customId = r.nextInt(10000);
        DPoint centerPoint = new DPoint(latitude, longitude);
        fenceClient.addGeoFence(centerPoint, fenceRadius, customId + "");
    }

    private void addPolygonFence(SignInModel signInModel) {
        Random r = new Random(1);
        int customId = r.nextInt(10000);
        List<DPoint> pointList = new ArrayList<>();
        for (SignInModel.DataBean.MultiPointLocationBean multiPointLocation : signInModel.getData().getMultiPointLocation()) {
            double latitude = Double.parseDouble(multiPointLocation.getLatitude());
            double longitude = Double.parseDouble(multiPointLocation.getLongitude());
            pointList.add(new DPoint(latitude, longitude));
        }
        fenceClient.addGeoFence(pointList, customId + "");
    }
    List<GeoFence> fenceList = new ArrayList<>();

    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
        Message msg = Message.obtain();
        if (i == GeoFence.ADDGEOFENCE_SUCCESS) {
            fenceList.addAll(list);
            msg.obj = s;
            msg.what = 0;
        } else {
            msg.arg1 = i;
            msg.what = 1;
        }
        handler.sendMessage(msg);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            requireActivity().unregisterReceiver(mGeoFenceReceiver);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (null != fenceClient) {
            fenceClient.removeGeoFence();
        }
    }

    private final BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                Message msg = Message.obtain();
                msg.obj = status;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }
    };
    Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case 0:
                break;
            case 1:
                int errorCode = msg.arg1;
                break;
            case 2:
                int statusStr = (int) msg.obj;
                if (statusStr == GeoFence.STATUS_LOCFAIL) {
                    mViewDataBind.tvLocation.setText("定位失败");
                } else if (canDk && statusStr == GeoFence.STATUS_IN || statusStr == GeoFence.STATUS_STAYED) {
                    mViewDataBind.slSignIn.setClickable(true);
                    mViewDataBind.tvDk.setText("打卡");
                } else if (statusStr == GeoFence.STATUS_OUT) {
                    mViewDataBind.slSignIn.setClickable(false);
                    mViewDataBind.tvDk.setText("不在范围");
                }
                break;
            default:
                break;
        }
        return false;
    });
}
