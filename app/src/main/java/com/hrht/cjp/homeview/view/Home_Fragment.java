package com.hrht.cjp.homeview.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.MyLocationStyle;
import com.hrht.cjp.R;

/**
 * Created by 陈攀进 on 2017/10/12.
 */

public class Home_Fragment extends Fragment {


    private TextView tv_tianqi;
    private ImageButton bt_1_weizhang;
    private ImageView iv_tianqi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_1_weizhang = ((ImageButton) getView().findViewById(R.id.bt_1_weizhang));
        iv_tianqi = ((ImageView) getView().findViewById(R.id.iv_tianqi));

        tv_tianqi = (TextView) getView().findViewById(R.id.tv_tianqi);

        init();

    }

    private void init() {
        bt_1_weizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"违章",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setImage(String text) {
        if ("多云".equals(text)){
            iv_tianqi.setImageResource(R.mipmap.duoyun);
        }
    }

    public void setText(String text) {
        tv_tianqi.setText(text);
    }
}
