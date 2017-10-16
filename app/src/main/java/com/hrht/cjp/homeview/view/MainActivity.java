package com.hrht.cjp.homeview.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.hrht.cjp.R;

public class MainActivity extends AppCompatActivity implements MapFragment.titleSelectInterface{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onTitleSelect(String tianqi ,String wendu) {
        FragmentManager manager = getSupportFragmentManager();
        Home_Fragment home_Fragment = (com.hrht.cjp.homeview.view.Home_Fragment) manager.findFragmentById(R.id.fragment_right);
        home_Fragment.setText(tianqi+","+wendu+"°");
        home_Fragment.setImage(tianqi);
    }
}
