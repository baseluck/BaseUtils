package com.yhzc.schooldormitorymobile.ui.mapLocation;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
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
import com.luck.basemodule.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 定位信息
 * @创建日期: 2021/4/14 13:51
 * @author: ProcyonLotor
 */

public class MapLocationActivity extends BaseActivity<MapLocationViewModel, ActivityMapLocationBinding>
        implements PoiSearch.OnPoiSearchListener {

    private AMap aMap;
    private MapLocationAdapter mAdapter;
    private LatLonPoint mLatLonPoint;
    private PoiSearch.Query mQuery;
    private Marker mMarker;


    private int currentPage = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_location;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        mLatLonPoint = getIntent().getParcelableExtra("latLonPoint");

        mAdapter = new MapLocationAdapter(R.layout.item_map_location, null);
        mViewDataBind.rvAddress.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBind.rvAddress.setAdapter(mAdapter);
        Utils.initLoadMore(mAdapter, () -> {
            if (mLatLonPoint != null) {
                currentPage++;
                doSearchQuery(mLatLonPoint, mQuery);

            }
        });

        showRightTextAndOnClickListener("确定",v -> {

        });

        mViewDataBind.mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mViewDataBind.mapView.getMap();
            UiSettings uiSettings = aMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
        }

        aMap.setMapLanguage(AMap.CHINESE);
        initLocationStyle();
        initClick();
        if (mLatLonPoint != null) {
            CameraPosition cameraPosition = new CameraPosition(new LatLng(mLatLonPoint.getLatitude(), mLatLonPoint.getLongitude()), 15, 0, 0);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            aMap.moveCamera(cameraUpdate);
            mQuery = new PoiSearch.Query("", "", "");

            doSearchQuery(mLatLonPoint, mQuery);

        }
    }


    private void initClick() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<PoiItem> data = (List<PoiItem>) adapter.getData();
            addMarker(data.get(position).getLatLonPoint());
            mViewDataBind.textView2.setText(data.get(position).getTitle());
        });
    }


    private void addMarker(LatLonPoint lp) {
        if (mMarker==null) {
            mMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.6f, 0.6f)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_poi_marker))));
        }

        mMarker.setPosition(new LatLng(lp.getLatitude(), lp.getLongitude()));
        mMarker.showInfoWindow();
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
        mViewDataBind.mapView.onDestroy();
    }


    protected void doSearchQuery(LatLonPoint lp, PoiSearch.Query query) {
        if (query == null) {
            query = new PoiSearch.Query("", "", "");
        }
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (lp != null) {
            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 300, true));
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(mQuery)) {
                    ArrayList<PoiItem> pois = poiResult.getPois();
                    if (pois != null && pois.size() > 0) {
                        if (currentPage == 1) {
                            mAdapter.setList(pois);
                        } else {
                            mAdapter.addData(pois);
                        }
                        mAdapter.getLoadMoreModule().loadMoreComplete();
                    } else {
                        mAdapter.getLoadMoreModule().loadMoreEnd(true);
                    }
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