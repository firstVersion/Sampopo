package com.example.kuro.spaprac2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends FragmentActivity {

    private GoogleMap mMap;
    private View childView, containerView;

    private Animation inAnimation;
    private Animation outAnimation;
    private static MarkerOptions mMyMarkerOptions = null;
    int flag = 0;

    private Marker mMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerView = findViewById(R.id.menu);
        childView = findViewById(R.id.childview);
        inAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.in_animetion);
        outAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.out_animetion);
        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ビューが表示されてるか判定
                if (childView.getVisibility() == View.GONE) {
                    // アニメーションしながらViewを表示
                    childView.startAnimation(inAnimation);
                    childView.setVisibility(View.VISIBLE);
                } else {
                    // アニメーションしながらViewを隠す
                    childView.startAnimation(outAnimation);
                    childView.setVisibility(View.GONE);
                }
            }
        });

        setUpMapIfNeeded();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {


      /*
        Intent intentTitle = getIntent();
        String Title = intentTitle.getStringExtra("intent_details_Title");

        Intent intentMessage = getIntent();
        String Message = intentMessage.getStringExtra("intent_details_Message");

        Intent intentPhoto = getIntent();
        Bundle b = intentPhoto.getExtras();
        Bitmap Photo =(Bitmap) b.get("intent_details_Photo");
*/
/*
        Intent intentPhoto = getIntent();
        Bitmap Photo = (Bitmap) intentPhoto.getParcelableExtra("data");
*/

                setUpMap();
                mapInit();

                getDetails("waaaaa", "setumeibunndesuyoooooo", new LatLng(35.681382, 139.766083));
                getDetails("ここどこ", "迷い込んだ", new LatLng(35.685175, 139.752799));
                getDetails("面白いひと見つけた", "のわああ", new LatLng(35.661382, 139.766084));

              /*  mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mMyMarkerOptions = new MarkerOptions();
                        mMyMarkerOptions.position(latLng);
                        System.out.println(latLng);

                        // 古いピンを消去する
                        //   mMap.clear();
// タップした位置にピンを立てる
                        mMap.addMarker(mMyMarkerOptions);
                        flag = 1;

// 逆ジオコーディングでピンを立てた位置の住所を取得する
                        //       requestReverseGeocode(latLng.latitude, latLng.longitude);


                    }


                });
                        */

            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    private void mapInit() {

        LatLng tokyo = new LatLng(35.681382, 139.766084);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        /*
        * Othe supported types include : MAP_TYPE_NORMAL,
        * MAP_TYPE_TERRAIN,MAP_HYBRID and MAP_TYPE_NONE
        * */
        //  mMap.setBuiltInZoomControls(true);
        mMap.setMyLocationEnabled(true);
        mMap.setInfoWindowAdapter(new CustomInfoAdapter());
        //CameraPosition camerapos = new CameraPosition.Builder()
        //        .target(new LatLng(35.681382,139.766084)).zoom(15.5f).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 13));


        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
//        .fromResource(R.drawable.ic_logo);//マーカーを画像にする
        mMarker = mMap.addMarker(new MarkerOptions()
                .title("東京駅")
                .snippet("記念suicaが馬鹿売れした")
                .position(tokyo)
                .icon(icon));


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Location myLocate = locationManager.getLastKnownLocation("gps");
        System.out.println();

    }


    /*------------------------------------------------------------------------------------*/


    private class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {

        /**
         * Window の View.
         */
        private final View mWindow;

        /**
         * コンストラクタ.
         */
        public CustomInfoAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.detail, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        private void render(Marker marker, View view) {
            // ここでどの Marker がタップされたか判別する
            if (marker.equals(mMarker)) {
                // 画像
                ImageView badge = (ImageView) view.findViewById(R.id.camera);
                badge.setImageResource(R.drawable.camera);
            }
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView snippet = (TextView) view.findViewById(R.id.snippet);
            title.setText(marker.getTitle());
            snippet.setText(marker.getSnippet());
        }

    }


    public void getDetails(String title, String snippet, LatLng position) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pin);//マーカーを画像にする
        mMarker = mMap.addMarker(new MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(position)
                .icon(icon));
    }

    private Uri fileUri;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public void camera(View v) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

}

