package com.hrht.cjp.homeview.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.hrht.cjp.R;

/**
 * Created by 陈攀进 on 2017/10/12.
 */

public class MapFragment extends Fragment {
    private MapView mapView;
    private MyLocationStyle myLocationStyle;
    private AMap aMap;
    private ImageButton ib_dingwei;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private titleSelectInterface mSelectInterface;
    private LinearLayout my_layout_2;
    private ImageButton bt_daolujiuyuan_1;
    private ImageButton bt_shigubaoan_2;
    private ImageButton bt_moshi;

    public interface titleSelectInterface {
        public void onTitleSelect(String tianqi,String wendu);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = ((MapView) view.findViewById(R.id.fragment_left_map));
        ib_dingwei = ((ImageButton) view.findViewById(R.id.ib_dingwei));
        my_layout_2 = ((LinearLayout) view.findViewById(R.id.my_layout_2));
        bt_daolujiuyuan_1 = ((ImageButton) view.findViewById(R.id.bt_daolujiuyuan_1));
        bt_shigubaoan_2 = ((ImageButton) view.findViewById(R.id.bt_shigubaoan_2));

        bt_moshi = ((ImageButton) view.findViewById(R.id.bt_moshi));
        click();
        mapView.onCreate(savedInstanceState);
        init();
        buttoninit();
        return view;
    }

    private void click() {
        click_bt_daolujiuyuan_1();
        click_bt_shigubaoan_2();

        click_bt_moshi();

    }

    boolean Is_2d=false;
    private void click_bt_moshi() {
        bt_moshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Is_2d) {
                    //2d模式开启
                    bt_moshi.setBackgroundResource(R.mipmap.d2_moshi);
                    Is_2d = true;
                    aMap.moveCamera(mCameraUpdate_2d);

                }else {

                    //巡航模式开启
                    bt_moshi.setBackgroundResource(R.mipmap.xunhang_moshi);
                    Is_2d = false;
                    aMap.moveCamera(mCameraUpdate);

                }

            }
        });
    }

    private void click_bt_daolujiuyuan_1() {

        //打电话
        bt_daolujiuyuan_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "15071164081";

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });
    }

    private void click_bt_shigubaoan_2() {
        //打电话
        bt_shigubaoan_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void setMy_layout_2(boolean b) {
        if (b){
            my_layout_2.setVisibility(View.VISIBLE);
        }else {
            my_layout_2.setVisibility(View.GONE);
        }

    }

    private void buttoninit() {
        ib_dingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylocation();
            }
        });

    }


    private void init() {

        myLocationStyle = new MyLocationStyle();
        //设置定位类型
        myLocationStyle.myLocationType(myLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        //myLocationStyle.myLocationType(myLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000);//设置连续定位模式下的定位间隔
        if (null == aMap) {
            aMap = mapView.getMap();
        }
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setZoomControlsEnabled(false);//这个方法设置了地图是否允许显示缩放按钮。
        aMap.setMyLocationEnabled(true);
        aMap.setTrafficEnabled(true);
        mLocationClient = new AMapLocationClient(getContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        mLocationOption.setSensorEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

        //设置定位回调监听
        mLocationClient.setLocationListener(mapLocationListener);
    }

    private WeatherSearch mweathersearch;
    private WeatherSearch.OnWeatherSearchListener mweatherSearchListener = new WeatherSearch.OnWeatherSearchListener() {

        @Override
        public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int i) {
            if (i == 1000) {
                if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                    LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                    String str = weatherlive.getWeather();

                    Log.i("weather2", str);


                    mSelectInterface.onTitleSelect(str,weatherlive.getTemperature());


                    Log.i("weather2", (weatherlive.getWeather()));
                } else {
                     Toast.makeText(getContext(),"网络异常",Toast.LENGTH_SHORT);

                }
            } else {
                Toast.makeText(getContext(), i + "", Toast.LENGTH_SHORT);

            }
        }

        @Override
        public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

        }
    };

    private void mylocation() {
        if (Is_2d){
            aMap.moveCamera(mCameraUpdate_2d);
        }else {
            aMap.moveCamera(mCameraUpdate);
        }
    }

    private CameraUpdate mCameraUpdate;
    private CameraUpdate mCameraUpdate_2d;

    private AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if (mapLocationListener != null && aMapLocation != null) {
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                    Log.i("fangxiangjiao", "方向角："+aMapLocation.getBearing());

                    if (!Is_2d) {
                        mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 17, 45, aMapLocation.getBearing()));
                        aMap.moveCamera(mCameraUpdate);
                    }

                    mCameraUpdate_2d = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 17, 0, 0));



                    queryweather(aMapLocation.getCity().toString());
                    Log.i("weather1", aMapLocation.getCity().toString());

                } else {
                    Toast.makeText(getContext(), "网络异常，请检查网络设置", Toast.LENGTH_SHORT);
                    Log.i("AmapErr", "err");
                }
            }
        }
    };
    private WeatherSearchQuery weatherSearchQuery;

    private void queryweather(String city) {
        weatherSearchQuery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(getContext());
        mweathersearch.setOnWeatherSearchListener(mweatherSearchListener);
        mweathersearch.setQuery(weatherSearchQuery);
        mweathersearch.searchWeatherAsyn(); //异步搜索

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        
    }


    @Override
    public void onResume() {
        super.onResume();

        mapView.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mSelectInterface = (titleSelectInterface) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString() + "must implement OnArticleSelectedListener");
        }
    }


}
