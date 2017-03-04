/**
 * class name:
 * class description:
 * author: dengchaoyue
 * version: 1.0
 */
package com.example.participatorysensing.mappage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.participatorysensing.Http.HttpCallbackListener;
import com.example.participatorysensing.R;
import com.example.participatorysensing.R.drawable;
import com.example.participatorysensing.firstpage.AirQualityActivity;
import com.example.participatorysensing.usercenterpage.UserNotSignedActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapPage extends Activity {

    public static int k = 0;
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationManager locationManager;
    private String provider;
    private boolean isFirstLocation = true;
    public static boolean loadingFinish = true;
    private static LatLng BaiduLocation;
    private Marker newStationMarkerOverlayout;
    private MarkerOptions myMarkerOptions;
    private String newStationID = "";
    private Location location;
    public static double currentLongitude, currentLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityController.addActivity(this);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_layout);
        cameraDIY.mark = 0;
        LinearLayout tab_home = (LinearLayout) findViewById(R.id.tab_home);
        LinearLayout tab_map = (LinearLayout) findViewById(R.id.tab_map);
        LinearLayout tab_task = (LinearLayout) findViewById(R.id.tab_task);
        LinearLayout tab_user = (LinearLayout) findViewById(R.id.tab_user);
        tab_home.setSelected(false);
        tab_map.setSelected(true);
        tab_task.setSelected(false);
        tab_user.setSelected(false);
        TextView pageTitle = (TextView) findViewById(R.id.centerText);
        pageTitle.setText("地图界面");
        TextView button2 = (TextView) findViewById(R.id.Button2);
        button2.setText("");
        TextView goBack = (TextView) findViewById(R.id.Button1);
        goBack.setText("");
        mapView = (MapView) findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            navigateTo(location);
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        super.onResume();
        mapView.onResume();
    }


    private void navigateTo(Location location) {
        //onChangeLocation,更改全局位置变量
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        double[] Baidu = CoordinateTranformUtil.wgs84tobd09(ll.longitude, ll.latitude);
        BaiduLocation = new LatLng(Baidu[1], Baidu[0]);
        Log.i("BaiduLocation.latitude", String.valueOf(BaiduLocation.latitude));
        Log.i("BaiduLocation.longitude", String.valueOf(BaiduLocation.longitude));

        double[] Gao = CoordinateTranformUtil.wgs84togcj02(ll.longitude, ll.latitude);
        LatLng GaodeLocation = new LatLng(Gao[1], Gao[0]);

        if (isFirstLocation == true) {

            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(BaiduLocation);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocation = false;
        }


        //MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        //locationBuilder.latitude(desLatLng.latitude);
        //locationBuilder.longitude(desLatLng.longitude);
        //Toast.makeText(this, location.getLatitude()+"+"+location.getLongitude(), Toast.LENGTH_LONG).show();
        //MyLocationData locationData = locationBuilder.build();
        //baiduMap.setMyLocationData(locationData);


        //11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
        //String BASE_URL = "http://182.92.116.126:8080/ps/SubPMStations?longitude=116.351387&latitude=39.961449&baidu=0";
        String BASE_URL = URLCollector.GetSubSationList + "?longitude=" + BaiduLocation.longitude + "&latitude=" + BaiduLocation.latitude + "&baidu=1";
        try {
            HttpUtil.sentHttpRequest(BASE_URL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    // TODO Auto-generated method stub
                    parseJSONWithJSONObject(response);
                    Thread.currentThread().interrupt();
                }

                @Override
                public void onError(Exception e) {
                    // TODO Auto-generated method stub
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    ProgressDialog myDialog = null;
    SubPMStationList subPMStationList = new SubPMStationList();

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONObject msg = json.getJSONObject("message");
            JSONArray stationsArray = null;
            JSONObject currentStation = null;


            if ("yes".equals(msg.getString("existPOI"))) {
                currentStation = msg.getJSONObject("existStation");
            }

            if (msg.getJSONArray("stations") != null) {
                stationsArray = msg.getJSONArray("stations");
            } else {
                Toast.makeText(getApplicationContext(), "No station has been established nearby", Toast.LENGTH_LONG).show();
            }

            if (currentStation != null) {
                stationsArray.put(currentStation);
            } else {
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(drawable.nopoi);
                myMarkerOptions = new MarkerOptions().icon(bitmap).position(BaiduLocation);
                Marker marker = (Marker) baiduMap.addOverlay(myMarkerOptions);
                newStationMarkerOverlayout = marker;
                marker.setTitle("My current position !");
            }

            k = stationsArray.length();
            for (int i = 0; i < stationsArray.length(); i++) {
                SubPMStationItem item = new SubPMStationItem();
                JSONObject jsonObject = stationsArray.getJSONObject(i);
                item.setStationID(Integer.parseInt(jsonObject.getString("stationID")));
                item.setStationAddress(jsonObject.getString("stationAddress"));
                item.setLatestPic(jsonObject.getString("latestPic"));
                item.setFPM(Integer.parseInt(jsonObject.getString("fpm")));
                item.setActual_fpm(Integer.parseInt(jsonObject.getString("actual_fpm")));
                item.setReputation(Integer.parseInt(jsonObject.getString("reputation")));
                item.setNearPmStation(jsonObject.getString("nearPmStation"));
                item.setLatestTime(jsonObject.getString("latestTime"));
                item.setLongitude(Double.parseDouble(jsonObject.getString("lon")));
                item.setLatitude(Double.parseDouble(jsonObject.getString("lat")));
                item.setBlongitude(Double.parseDouble(jsonObject.getString("blon")));
                item.setBlatitude(Double.parseDouble(jsonObject.getString("blat")));
                subPMStationList.add(item);

                LatLng point = new LatLng(item.getBlatitude(), item.getBlongitude());
//				CoordinateConverter converter  = new CoordinateConverter();    
//				converter.from(CoordType.GPS);    
//				converter.coord(point);    
//				LatLng desPoint = converter.convert(); 
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(drawable.poi);
                if (item.getBlatitude() == BaiduLocation.latitude && item.getBlongitude() == BaiduLocation.longitude) {
                    newStationMarkerOverlayout.remove();
                }
                if ("yes".equals(msg.getString("existPOI")) && (i == stationsArray.length() - 1)) {
                    BitmapDescriptor currentbitmap = BitmapDescriptorFactory.fromResource(drawable.currentpoi);
                    MarkerOptions markerOptions = new MarkerOptions().icon(currentbitmap).position(point);
                    Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
                    marker.setTitle(item.getStationID() + "");
                    marker.setPosition(point);
                } else {
                    MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(point);
                    Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
                    marker.setTitle(item.getStationID() + "");
                    marker.setPosition(point);
                }
                //Bundle featureInfo = new Bundle();
                //featureInfo.put
                //marker.setExtraInfo(featureInfo);
            }
            baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    // TODO Auto-generated method stub
                    if ("My current position !".equals(arg0.getTitle())) {
                        cameraDIY.mark = 0;
                        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                        final String username = preferences.getString("username", "");
                        if ("".equals(username)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MapPage.this);
                            builder.setNegativeButton("前去登录", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialogNote, int which) {//确定按钮的响应事件
                                    dialogNote.dismiss();
                                    Intent register = new Intent(MapPage.this, UserNotSignedActivity.class);

                                    startActivity(register);
                                    MapPage.this.finish();
                                }
                            });
                            AlertDialog dialogNote = builder.create();
                            dialogNote.setTitle("Note: ");//设置对话框标题
                            dialogNote.setMessage("您还没有登录，请先登录后再进行拍照");//设置显示的内容
                            Window win = dialogNote.getWindow();
                            win.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialogNote.show();
                        } else {
                            String BASE_URL = "http://182.92.116.126:8080/ps/newPMStation?userName=" + username + "&latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude();
                            try {
                                HttpUtil.requestCreateNewStation(BASE_URL, new HttpCallbackListener() {

                                    @Override
                                    public void onError(Exception e) {
                                        Message message = new Message();
                                        message.what = 101;
                                        requestCreateNewStation.sendMessage(message);
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onFinish(String response) {
                                        if ("500".equals(response)) {
                                            Message message = new Message();
                                            message.what = 102;
                                            requestCreateNewStation.sendMessage(message);
                                        } else {
                                            newStationID = decode(response);
                                            if ("".equals(newStationID)) {
                                                Message message = new Message();
                                                message.what = 104;
                                                requestCreateNewStation.sendMessage(message);
                                            } else {
                                                Intent takePhoto = new Intent(MapPage.this, cameraDIY.class);
                                                Bundle myBundle = new Bundle();
                                                myBundle.putString("stationID", "");
                                                myBundle.putString("longitude", location.getLongitude()+"");
                                                myBundle.putString("latitude",location.getLatitude()+"");
                                                myBundle.putString("altitude",location.getAltitude()+"");
                                                myBundle.putString("username",username);
                                                takePhoto.putExtras(myBundle);
                                                startActivity(takePhoto);
                                            }
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }

                    } else {
                        cameraDIY.mark = 1;
                        String stationID = arg0.getTitle();
                        SubPMStationItem item = subPMStationList.getItem(stationID);
                        Toast.makeText(getApplicationContext(), item.getStationID() + "Station被点击", Toast.LENGTH_SHORT).show();
                        AskPhoto ask = new AskPhoto(getBaseContext(), item.getStationID(), item.getStationAddress());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent jumpToPhotoWall = new Intent(MapPage.this, PhotoWall.class);
                        if (item.getStationAddress() != null) {
                            jumpToPhotoWall.putExtra("subStationItem", item);
                            jumpToPhotoWall.putExtra("realLatitude", location.getLatitude()+"");
                            jumpToPhotoWall.putExtra("realLongitude", location.getLongitude()+"");
                        }
                        startActivity(jumpToPhotoWall);
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  JSONObject json = null;
    private String stateCode = "";
    private String poiID = "";
    private String decode(String response) {
        try {
            json = new JSONObject(response);
            stateCode = json.getString("stateCode");
            if ("000".equals(stateCode)) {
                poiID = json.getString("poiID");
                return poiID;
            } else {
                Message message = new Message();
                message.what = 103;
                requestCreateNewStation.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = 104;
            requestCreateNewStation.sendMessage(message);
        }

        return poiID;
    }

    private Handler requestCreateNewStation = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    Toast.makeText(getApplicationContext(), "Download error", Toast.LENGTH_LONG).show();
                    break;
                case 102:
                    Toast.makeText(getApplicationContext(), "Servers refused", Toast.LENGTH_LONG).show();
                    break;
                case 103:
                    Toast.makeText(getApplicationContext(), "Cannot establish new Station:" + stateCode, Toast.LENGTH_LONG).show();
                    break;
                case 104:
                    Toast.makeText(getApplicationContext(), "Parse error", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    /**
     * @param featureInfo
     */
    private void setExtraInfo(Bundle featureInfo) {
        // TODO Auto-generated method stub

    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if (location != null) {
                navigateTo(location);
            }
        }
    };

    @Override
    protected void onStop() {
        baiduMap.clear();
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        baiduMap.clear();
        mapView.onDestroy();
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
        ActivityController.removeActivity(this);
    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    public void onBackPressed() {
        ActivityController.finishAll();
    }

}
