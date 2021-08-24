package com.yhzc.schooldormitorymobile.ui.mapLocation;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.basemodule.base.BaseActivity;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.databinding.ActivityMapLocationBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @描述: 定位信息
 * @创建日期: 2021/4/14 13:51
 * @author: ProcyonLotor
 */

public class MapLocationActivityBackUp extends BaseActivity<MapLocationViewModel, ActivityMapLocationBinding>
        implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener {

    private AMap aMap;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private MapLocationAdapter mAdapter;

    private PoiSearch poiSearch;

    private int currentPage = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_location;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mAdapter = new MapLocationAdapter(R.layout.item_map_location, null);
        mViewDataBind.rvAddress.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvAddress.setAdapter(mAdapter);
//        Utils.initLoadMore(mAdapter, new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
        mViewDataBind.mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mViewDataBind.mapView.getMap();
            UiSettings uiSettings = aMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
        }

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMapLanguage(AMap.CHINESE);
        aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
        initLocationStyle();
    }


    @Override
    protected int initTitle() {
        return R.string.sign_location;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewDataBind.mapView.onResume();
    }

    private void initLocationStyle() {
        if (aMap != null) {
            // 自定义系统定位蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            // 自定义定位蓝点图标
            myLocationStyle.myLocationIcon(
                    BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
            // 自定义精度范围的圆形边框颜色
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
            // 自定义精度范围的圆形边框宽度
            myLocationStyle.strokeWidth(0);
            // 设置圆形的填充颜色
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
            // 将自定义的 myLocationStyle 对象添加到地图上
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewDataBind.mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewDataBind.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
        mViewDataBind.mapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation.getErrorCode() == 0) {
            mListener.onLocationChanged(aMapLocation);
            LatLonPoint lp = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            doSearchQuery(lp, null);
        } else {
            ToastUtils.showShort("定位失败");
        }
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }


    private void destroyLocation() {
        mListener = null;
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);

            mLocationClient.startLocation();
        }

    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    protected void doSearchQuery(LatLonPoint lp, PoiSearch.Query query) {
        if (query == null) {
            query = new PoiSearch.Query("", "", "");
        }
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 300, true));
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                ArrayList<PoiItem> pois = poiResult.getPois();
                if (pois != null && pois.size() > 0) {
                    mAdapter.setList(pois);
                }
            }
        } else {
            ToastUtils.showShort("地址获取失败");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}