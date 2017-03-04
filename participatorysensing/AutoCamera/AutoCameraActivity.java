package com.example.participatorysensing.AutoCamera;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.participatorysensing.AutoCamera.MyHorizontalScrollView.CurrentImageChangeListener;
import com.example.participatorysensing.AutoCamera.MyHorizontalScrollView.OnItemClickListener;
import com.example.participatorysensing.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCameraActivity extends Activity {

    private ArrayList<String> filterDensityData;
    private ArrayList<String> baseShutterSpeedData;
    private MyHorizontalScrollView fdScrollView;
    private MyHorizontalScrollViewBelow bssScrollView;
    private HorizontalScrollViewAdapter fdAdapter;
    private HorizontalScrollViewAdapterBelow bssAdapter;
    private LinearLayout linearLayoutAbove, linearLayoutBelow;
    private TextView fdTv, bssTv, resultTv, languageTv, tabsLine1, tabsLine2, fdTvLanguage, bssTvLanguage;
    private static String fdValue = "n.n";
    private static String bssValue = "n";
    private static double bssVal = 0.0;
    private static String fdStep = "n";
    private String resultValue = "UNKNOW";
    private List<Double> bssNumber = new ArrayList<Double>();
    private List<String> filterDensityNumber = new ArrayList<String>();
    private static int languageType = 1; // 1 means English, 0 means Chinese;
    private static int fdPos, bssPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.auto_camera_config);

        TextView pageTitle = (TextView) findViewById(R.id.centerText);
        pageTitle.setText("拍照设置");
        TextView button2 = (TextView) findViewById(R.id.Button2);
        button2.setText("确定");
        TextView goBack = (TextView) findViewById(R.id.Button1);
        goBack.setText("");

        initData();
        initView();
    }

    public void initData() {
        filterDensityData = new ArrayList<String>() {{
            add("1");
            add("2");
            add("5");
            add("10");
            add("15");
            add("20");
            add("30");
            add("50");
        }};
        filterDensityNumber.add("1");
        filterDensityNumber.add("2");
        filterDensityNumber.add("5");
        filterDensityNumber.add("10");
        filterDensityNumber.add("15");
        filterDensityNumber.add("20");
        filterDensityNumber.add("30");
        filterDensityNumber.add("50");


        baseShutterSpeedData = new ArrayList<String>();
        baseShutterSpeedData.add("0.25");
        baseShutterSpeedData.add("0.5");
        baseShutterSpeedData.add("1");
        baseShutterSpeedData.add("1.25");
        baseShutterSpeedData.add("1.5");
        baseShutterSpeedData.add("1.75");
        baseShutterSpeedData.add("2");
        baseShutterSpeedData.add("3");
        baseShutterSpeedData.add("4");
        baseShutterSpeedData.add("5");
        baseShutterSpeedData.add("6");
        baseShutterSpeedData.add("7");
        baseShutterSpeedData.add("8");
        baseShutterSpeedData.add("9");
        baseShutterSpeedData.add("10");


        bssNumber.add((1 / (double) 8000));
        bssNumber.add((1 / (double) 6400));
        bssNumber.add((1 / (double) 6000));
        bssNumber.add((1 / (double) 5000));
        bssNumber.add((1 / (double) 4000));
        bssNumber.add((1 / (double) 3200));
        bssNumber.add((1 / (double) 3000));
        bssNumber.add((1 / (double) 2500));
        bssNumber.add((1 / (double) 2000));
        bssNumber.add((1 / (double) 1600));
        bssNumber.add((1 / (double) 1500));
        bssNumber.add((1 / (double) 1250));
        bssNumber.add((1 / (double) 1000));
        bssNumber.add((1 / (double) 800));
        bssNumber.add((1 / (double) 750));
        bssNumber.add((1 / (double) 640));
        bssNumber.add((1 / (double) 500));
        bssNumber.add((1 / (double) 400));
        bssNumber.add((1 / (double) 350));
        bssNumber.add((1 / (double) 320));
        bssNumber.add((1 / (double) 250));
        bssNumber.add((1 / (double) 200));
        bssNumber.add((1 / (double) 180));
        bssNumber.add((1 / (double) 160));
        bssNumber.add((1 / (double) 125));
        bssNumber.add((1 / (double) 100));
        bssNumber.add((1 / (double) 90));
        bssNumber.add((1 / (double) 80));
        bssNumber.add((1 / (double) 60));
        bssNumber.add((1 / (double) 50));
        bssNumber.add((1 / (double) 45));
        bssNumber.add((1 / (double) 40));
        bssNumber.add((1 / (double) 30));
        bssNumber.add((1 / (double) 25));
        bssNumber.add((1 / (double) 20));
        bssNumber.add((1 / (double) 15));
        bssNumber.add((1 / (double) 13));
        bssNumber.add((1 / (double) 10));
        bssNumber.add((1 / (double) 8));
        bssNumber.add((1 / (double) 6));
        bssNumber.add((1 / (double) 5));
        bssNumber.add((1 / (double) 4));
        bssNumber.add(0.3);
        bssNumber.add(0.4);
        bssNumber.add(0.5);
        bssNumber.add(0.6);
        bssNumber.add(0.7);
        bssNumber.add(0.8);
        bssNumber.add(1.0);
        bssNumber.add(1.3);
        bssNumber.add(1.5);
        bssNumber.add(1.6);
        bssNumber.add(2.0);
        bssNumber.add(2.5);
        bssNumber.add(3.0);
        bssNumber.add(3.2);
        bssNumber.add(4.0);
        bssNumber.add(5.0);
        bssNumber.add(6.0);
        bssNumber.add(8.0);
        bssNumber.add(10.0);
        bssNumber.add(13.0);
        bssNumber.add(15.0);
        bssNumber.add(20.0);
        bssNumber.add(25.0);
        bssNumber.add(30.0);
    }

    private String resultArray[][] = {{"1/4000", "1/3200", "1/2500", "1/2000", "1/1600", "1/1250", "1/1000", "1/800", "1/640", "1/500", "1/400", "1/320", "1/250", "1/200", "1/160", "1/125", "1/100", "1/80", "1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "10", "15", "20", "25", "30", "0:00:40", "0:00:50", "0:01:00"},
        {"1/2000", "1/1600", "1/1250", "1/1000", "1/800", "1/640", "1/500", "1/400", "1/320", "1/250", "1/200", "1/160", "1/125", "1/100", "1/80", "1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "20", "30", "0:00:40", "0:00:52", "0:01:00", "0:01:20", "0:01:40", "0:02:00"},
        {"1/1000", "1/800", "1/640", "1/500", "1/400", "1/320", "1/250", "1/200", "1/160", "1/125", "1/100", "1/80", "1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:40", "0:01:04", "0:01:20", "0:01:44", "0:02:00", "0:02:40", "0:03:20", "0:04:00"},
        {"1/500", "1/400", "1/320", "1/250", "1/200", "1/160", "1/125", "1/100", "1/80", "1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:48", "0:01:04", "0:01:20", "0:01:36", "0:02:08", "0:02:40", "0:03:28", "0:04:00", "0:05:20", "0:06:40", "0:08:00"},
        {"1/250", "1/200", "1/160", "1/125", "1/100", "1/80", "1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:41", "0:00:51", "0:01:04", "0:01:20", "0:01:36", "0:02:08", "0:02:40", "0:03:12", "0:04:16", "0:05:20", "0:06:56", "0:08:00", "0:10:40", "0:13:20", "0:16:00"},
        {"1/125", "1/100", "1/80", "1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:49", "0:01:04", "0:01:23", "0:01:42", "0:02:08", "0:02:40", "0:03:12", "0:04:16", "0:05:20", "0:06:24", "0:08:32", "0:10:40", "0:13:52", "0:16:00", "0:21:20", "0:26:40", "0:32:00"},
        {"1/60", "1/50", "1/40", "1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:42", "0:00:51", "0:01:04", "0:01:20", "0:01:38", "0:02:08", "0:02:46", "0:03:24", "0:04:16", "0:05:20", "0:06:24", "0:08:32", "0:10:40", "0:12:48", "0:17:04", "0:21:20", "0:27:44", "0:32:00", "0:42:40", "0:53:20", "1:04:00"},
        {"1/30", "1/25", "1/20", "1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "1/1.6", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:42", "0:00:51", "0:01:04", "0:01:25", "0:01:42", "0:02:08", "0:02:40", "0:03:16", "0:04:16", "0:05:32", "0:06:49", "0:08:32", "0:10:40", "0:12:48", "0:17:04", "0:21:20", "0:25:36", "0:34:08", "0:42:40", "0:55:28", "1:04:00", "1:25:20", "1:46:40", "2:08:00"},
        {"1/15", "1/13", "1/10", "1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "0.7", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:39", "0:00:51", "0:01:04", "0:01:25", "0:01:42", "0:02:08", "0:02:50", "0:03:24", "0:04:16", "0:05:20", "0:06:33", "0:08:32", "0:11:05", "0:13:39", "0:17:04", "0:21:20", "0:25:36", "0:34:08", "0:42:40", "12:51:12", "1:08:16", "1:25:20", "1:50:56", "2:08:00", "2:50:40", "3:33:20", "4:16:00"},
        {"1/8", "1/6", "1/5", "1/4", "1/3", "1/2.5", "1/2", "0.7", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:51", "0:01:08", "0:01:18", "0:01:42", "0:02:08", "0:02:50", "0:03:24", "0:04:16", "0:05:41", "0:06:49", "0:08:32", "0:10:40", "0:13:07", "0:17:04", "0:22:11", "0:27:18", "0:34:08", "0:42:40", "0:51:12", "1:08:16", "1:25:20", "1:42:24", "2:16:32", "2:50:40", "3:41:52", "4:16:00", "5:41:20", "7:06:40", "8:32:00"},
        {"1/4", "0.4", "1/2.5", "1/2", "0.7", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:51", "0:01:08", "0:01:21", "0:01:42", "0:02:16", "0:02:37", "0:03:24", "0:04:16", "0:05:41", "0:06:49", "0:08:32", "0:11:22", "0:13:39", "0:17:04", "0:21:20", "0:26:15", "0:34:08", "0:44:22", "0:54:36", "1:08:16", "1:25:20", "1:42:24", "2:16:32", "2:50:40", "3:24:48", "4:33:04", "5:41:20", "7:23:44", "8:32:00", "11:22:40", "14:13:20", "17:04:00"},
        {"1/2", "0.7", "1/1.3", "1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:51", "0:01:08", "0:01:21", "0:01:42", "0:02:16", "0:02:43", "0:03:24", "0:04:33", "0:05:15", "0:06:49", "0:08:32", "0:11:22", "0:13:39", "0:17:04", "0:22:45", "0:27:18", "0:34:08", "0:42:40", "0:52:30", "1:08:16", "1:28:44", "1:49:13", "2:16:32", "2:50:40", "3:24:48", "4:33:04", "5:41:20", "6:49:36", "9:06:08", "11:22:40", "14:47:28", "17:04:00", "22:45:20", "28:26:40", "34:08:00"},
        {"1", "1.3", "1.6", "2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:51", "0:01:05", "0:01:21", "0:01:42", "0:02:16", "0:02:43", "0:03:24", "0:04:33", "0:05:27", "0:06:49", "0:09:06", "0:10:30", "0:13:39", "0:17:04", "0:22:45", "0:27:18", "0:34:08", "0:45:30", "0:54:36", "1:08:16", "1:25:20", "1:45:01", "2:16:32", "2:57:29", "3:38:27", "4:33:04", "5:41:20", "6:49:36", "9:06:08", "11:22:40","13:39:12", "18:12:16", "22:45:20", "29:34:56", "34:08:00", "45:30:40", "56:53:20", "65:16:00"},
        {"2", "2.5", "3", "4", "5", "6", "8", "10", "13", "15", "20", "25", "30", "0:00:40", "0:00:51", "0:01:05", "0:01:21", "0:01:42", "0:02:11", "0:02:43", "0:03:24", "0:04:33", "0:05:27", "0:06:49", "0:09:06", "0:10:55", "0:13:39", "0:18:12", "0:21:00", "0:27:18", "0:34:08", "0:45:30", "0:54:36", "1:08:16", "1:31:01", "1:49:13", "2:16:32", "2:50:40", "3:30:03", "4:33:04", "5:54:59", "7:16:54", "9:06:08", "11:22:40", "13:39:12", "18:12:16", "22:45:20", "27:18;24", "36:24:32", "45:30:40", "59:09:52", "68:16:00", "91:01:20","113:46:40", "136:32:00" }
    };


    public void initView() {

        fdTvLanguage = (TextView) findViewById(R.id.FD);
        bssTvLanguage = (TextView) findViewById(R.id.BSS);
        fdScrollView = (MyHorizontalScrollView) findViewById(R.id.FDScrollView);
        bssScrollView = (MyHorizontalScrollViewBelow) findViewById(R.id.BSSScrollView);
        fdAdapter = new HorizontalScrollViewAdapter(this, filterDensityData);
        bssAdapter = new HorizontalScrollViewAdapterBelow(this, baseShutterSpeedData);
        fdScrollView.initDatas(fdAdapter);
        bssScrollView.initDatas(bssAdapter);

        //添加滚动回调
        fdScrollView.setCurrentImageChangeListener(new CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
            }
        });
        //添加点击回调

        fdScrollView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(View view, int position) {
            }
        });

        //添加滚动回调
        bssScrollView.setCurrentImageChangeListener(new MyHorizontalScrollViewBelow.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
            }
        });
        //添加点击回调
        bssScrollView.setOnItemClickListener(new MyHorizontalScrollViewBelow.OnItemClickListener() {

            @Override
            public void onClick(View view, int position) {
            }
        });
    }
}