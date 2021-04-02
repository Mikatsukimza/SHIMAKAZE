package com.example.afinal.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.TextOptions;
import com.amap.api.services.core.LatLonPoint;
import com.example.afinal.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  implements GeoFenceListener, AMap.OnMapClickListener, LocationSource, AMapLocationListener  {
    //能不能去掉？
    private LocationSource.OnLocationChangedListener mListener;

    private View root;
    // 中心点坐标
    private LatLng centerLatLng = null;
    // 中心点marker
    private Marker centerMarker;

    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
    private BitmapDescriptor ICON_RED = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
    private MarkerOptions markerOption = null;
    private List<Marker> markerList = new ArrayList<Marker>();

    private TextView tv;
    private TextView tvResult;

    private double currentLat;
    private double currentLon;
    private LatLonPoint latLonPoint;


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LatLng latLng = new LatLng(aMapLocation.getLatitude(),
                            aMapLocation.getLongitude());//取出经纬度
                    //添加Marker显示定位位置
                    if (centerMarker == null) {
                        //如果是空的添加一个新的,icon方法就是设置定位图标，可以自定义
                        centerMarker = aMap.addMarker(new MarkerOptions()
                                .position(latLng).snippet("您在这里").draggable(true).setFlat(true));
                        centerMarker.showInfoWindow();//主动显示indowindow
                        aMap.addText(new TextOptions().position(latLng).text(aMapLocation.getAddress()));
                        //固定标签在屏幕中央
                        centerMarker.setPositionByPixels(mapView.getWidth() / 2,mapView.getHeight() / 2);
                    } else {
                        //已经添加过了，修改位置即可
                        centerMarker.setPosition(latLng);
                    }
                    //然后可以移动到定位点,使用animateCamera就有动画效果
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));//参数提示:1.经纬度 2.缩放级别

                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    currentLat = aMapLocation.getLatitude();//获取纬度
                    currentLon = aMapLocation.getLongitude();//获取经度
                    latLonPoint = new LatLonPoint(currentLat, currentLon);  // latlng形式的
                    /*currentLatLng = new LatLng(currentLat, currentLon);*/   //latlng形式的
                    Log.i("currentLocation", "currentLat : " + currentLat + " currentLon : " + currentLon);
                    aMapLocation.getAccuracy();//获取精度信息

                    //tvResult = root.findViewById(R.id.tvResult);
                    String str = "纬度:"+currentLat+"经度:"+currentLon;
                    //tvResult.setText(str);

                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:" +aMapLocation.getErrorCode() + ", errInfo:"+ aMapLocation.getErrorInfo());
                }
            }

        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    MapView mapView = null;
    AMap aMap ;//得到一个map对象

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        //获取根view
        root = inflater.inflate(R.layout.fragment_home, container, false);

        //tv = root.findViewById(R.id.tv);
        mapView =root.findViewById(R.id.map);//找到地图控件
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        markerOption = new MarkerOptions().draggable(true);
        init();


        return root;
    }


    void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.getUiSettings().setRotateGesturesEnabled(false);
            aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
            setUpMap();
        }
    }

    //设置一些amap的属性
    private void setUpMap() {
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//        //初始化AMapLocationClientOption对象
//        mLocationOption = new AMapLocationClientOption();
//        /*为选项选择定位场景,支持三种场景（签到、出行、运动，默认无场景）*/
//        mLocationOption .setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
//        if(null != mLocationClient){
//            mLocationClient.setLocationOption(mLocationOption);
//            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//            mLocationClient.stopLocation();
//            mLocationClient.startLocation();
//        }
//        /*为选项选择定位模式*/
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位同时是否需要返回地址描述
//        mLocationOption.setNeedAddress(true);
//        /*启动定位*/
//        //给定位客户端对象设置定位参数（选项）
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();

        //getCurrentLocationLatLng();

        aMap.setOnMapClickListener(this);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }





    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {

//        Message msg = Message.obtain();
//        if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {
//            fenceList = geoFenceList;
//            msg.obj = customId;
//            msg.what = 0;
//        } else {
//            msg.arg1 = errorCode;
//            msg.what = 1;
//        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        markerOption.icon(ICON_YELLOW);
        centerLatLng = latLng;
        addCenterMarker(centerLatLng);
        //tv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        //tv.setText("选中的坐标：" + centerLatLng.longitude + ","+ centerLatLng.latitude);
    }
    private void addCenterMarker(LatLng latlng) {
        if (null == centerMarker) {
            centerMarker = aMap.addMarker(markerOption);
        }
        centerMarker.setPosition(latlng);
        markerList.add(centerMarker);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getContext());
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                //     tvResult.setVisibility(View.GONE);
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                //   tvResult.setVisibility(View.VISIBLE);
                //tvResult.setText(errText);
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }



}


