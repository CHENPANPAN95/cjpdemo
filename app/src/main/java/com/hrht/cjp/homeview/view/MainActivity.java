package com.hrht.cjp.homeview.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.hrht.cjp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MapFragment.titleSelectInterface, Home_Fragment.dianhuaSelectInterface {

    private static final int msgKey1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TimeThread().start();
        FragmentManager manager = getSupportFragmentManager();
        home_Fragment = (com.hrht.cjp.homeview.view.Home_Fragment) manager.findFragmentById(R.id.fragment_right);
        map_Fragment = (MapFragment) manager.findFragmentById(R.id.fragment_left);
    }

    MapFragment map_Fragment;
    Home_Fragment home_Fragment;

    @Override
    public void onDianhuaSelect(boolean b) {
        map_Fragment.setMy_layout_2(b);
    }

    @Override
    public void onTitleSelect(String tianqi, String wendu) {
        String s = tianqi + " , " + wendu + "°";
        if (!TextUtils.isEmpty(tianqi)) {
            home_Fragment.setImage(tianqi);
            home_Fragment.setText(s);
        } else {
            home_Fragment.setText("—— ——");
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format_hh = new SimpleDateFormat("HH");
                    SimpleDateFormat format_mm = new SimpleDateFormat("mm");
                    SimpleDateFormat format_dd = new SimpleDateFormat("dd");
                    home_Fragment.setTv_xianxing_riqi(format_dd.format(date));
                    home_Fragment.settv_time_hh(format_hh.format(date));
                    home_Fragment.settv_time_mm(format_mm.format(date));
                    break;
                default:
                    break;
            }
        }
    };


    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(10000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}
