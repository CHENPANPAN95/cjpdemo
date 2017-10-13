package com.hrht.cjp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;

/**
 * Created by 陈攀进 on 2017/10/12.
 */

public class MapFragment extends Fragment {
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = ((MapView) view.findViewById(R.id.fragment_left_map));
        mapView.onCreate(savedInstanceState);
        return view;
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
}
