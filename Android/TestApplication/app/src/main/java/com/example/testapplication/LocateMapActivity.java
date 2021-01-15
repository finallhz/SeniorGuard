package com.example.testapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class LocateMapActivity extends AppCompatActivity {
    private MapView mapView;//手表的定位组件
    private MapView mapView1;//上次老人跌倒的定位组件
    private BaiduMap mBaiduMap;
    private BaiduMap mBaiduMap1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化地图SDK
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.record_locatemap);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        mapView = (MapView) findViewById(R.id.bmapview);
        mBaiduMap = mapView.getMap();
        mapView1 = (MapView) findViewById(R.id.bmapview1);
        mBaiduMap1 = mapView1.getMap();
        //定义Maker坐标点
        LatLng point = new LatLng(34.161538, 108.898113);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location_pin);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(20).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        mBaiduMap1.setMapStatus(mapStatusUpdate);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        mapView1.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
        mapView1.onDestroy();
        mapView1 = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        mapView1.onResume();
    }
}
