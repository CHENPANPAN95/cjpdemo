package com.hrht.cjp.homeview.view;


import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrht.cjp.R;

import java.io.IOException;


/**
 * Created by 陈攀进 on 2017/10/12.
 */

public class Home_Fragment extends Fragment {


    private TextView tv_tianqi;
    private ImageButton bt_1_weizhang;
    private ImageView iv_tianqi;
    private TextView tv_time_hh;
    private TextView tv_time_diandian;
    private TextView tv_time_mm;
    private TextView tv_xianxing_riqi;
    public SurfaceView surfaceView;
    private ImageButton bt_suishoupai;
    private ImageButton bt_3_dianhua;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);


        return view;
    }
    public interface dianhuaSelectInterface {
        public void onDianhuaSelect(boolean b);
    }
    AlphaAnimation animation;
    private void createAlphaAnimation() {

        //初始化AlphaAnimation
        animation = new AlphaAnimation(1.0f, 0.0f);

        animation.setDuration(1000);
        //动画重复次数-1表示不停重复
        animation.setRepeatCount(-1);
        //给控件设置动画
        tv_time_diandian.startAnimation(animation);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        surfaceView = ((SurfaceView) getView().findViewById(R.id.surface_view));
        bt_1_weizhang = ((ImageButton) getView().findViewById(R.id.bt_1_weizhang));
        iv_tianqi = ((ImageView) getView().findViewById(R.id.iv_tianqi));
        tv_tianqi = (TextView) getView().findViewById(R.id.tv_tianqi);
        tv_xianxing_riqi = ((TextView) getView().findViewById(R.id.tv_xianxing_riqi));

        tv_time_hh = ((TextView) getView().findViewById(R.id.tv_time_hh));
        tv_time_mm = ((TextView) getView().findViewById(R.id.tv_time_mm));
        tv_time_diandian = ((TextView) getView().findViewById(R.id.tv_time_diandian));
        bt_suishoupai = ((ImageButton) getView().findViewById(R.id.bt_suishoupai));

        bt_3_dianhua = ((ImageButton) getView().findViewById(R.id.bt_3_dianhua));
        init();

    }

    private void init() {
        createAlphaAnimation();

        onclick_weizhang();
        onclick_suishoupai();
        onclick_dianhua();

        surfaceView.getHolder().setFixedSize(200, 337);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new SurfaceCallback());


    }
    boolean IsVisible = false;
    private void onclick_dianhua() {
        bt_3_dianhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsVisible){
                    IsVisible = false;
                }else {
                    IsVisible = true;
                }
                mdianhuaSelectInterface.onDianhuaSelect(IsVisible);
            }
        });
    }

    private void onclick_suishoupai() {
        bt_suishoupai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "随手拍", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void onclick_weizhang() {
        bt_1_weizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "违章", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setImage(String text) {
        if (text != null) {
            if (text.contains("雨")) {
                iv_tianqi.setImageResource(R.mipmap.xiayu);
            } else if (text.contains("雪")) {
                iv_tianqi.setImageResource(R.mipmap.xiaxue);
            } else if (text.contains("晴")) {
                iv_tianqi.setImageResource(R.mipmap.qingtian);
            } else if (text.contains("阴")) {
                iv_tianqi.setImageResource(R.mipmap.yintian);
            } else if (text.contains("多云")) {
                iv_tianqi.setImageResource(R.mipmap.duoyun);
            } else {
                iv_tianqi.setImageResource(R.mipmap.yintian);
            }

        }
    }

    public void setTv_xianxing_riqi(String riqi) {
        int integer = Integer.parseInt(riqi);

        if (integer % 2 == 0) {
            tv_xianxing_riqi.setText("单号");
        } else {
            tv_xianxing_riqi.setText("双号");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void settv_time_hh(String time) {
        tv_time_hh.setText(time);
    }

    public void settv_time_mm(String time) {
        tv_time_mm.setText(time);
    }

    public void setText(String text) {
        tv_tianqi.setText(text);
    }
    private Camera camera;

    class SurfaceCallback implements SurfaceHolder.Callback {

        boolean isPreview;

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open();//打开硬件摄像头，这里导包得时候一定要注意是android.hardware.Camera
//                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器

//                Display display = wm.getDefaultDisplay();//得到当前屏幕
//                Camera.Parameters parameters = camera.getParameters();//得到摄像头的参数
//                parameters.setPreviewSize(display.getWidth(), display.getHeight());//设置预览照片的大小
//                parameters.setPreviewFrameRate(3);//设置每秒3帧
//                parameters.setPictureFormat(PixelFormat.JPEG);//设置照片的格式
//                parameters.setJpegQuality(85);//设置照片的质量
//                parameters.setPictureSize(display.getHeight(), display.getWidth());//设置照片的大小，默认是和   屏幕一样大
//                camera.setParameters(parameters);
                camera.setPreviewDisplay(surfaceView.getHolder());//通过SurfaceView显示取景画面
                camera.startPreview();//开始预览
                isPreview = true;//设置是否预览参数为真
            } catch (IOException e) {
                Log.e("eeeeee", e.toString());
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                if (isPreview) {//如果正在预览
                    camera.stopPreview();
                    camera.release();
                }
            }

        }


    }

    private dianhuaSelectInterface mdianhuaSelectInterface;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mdianhuaSelectInterface = (dianhuaSelectInterface) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString() + "must implement OnArticleSelectedListener");
        }
    }
}
