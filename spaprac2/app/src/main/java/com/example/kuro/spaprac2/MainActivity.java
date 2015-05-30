package com.example.kuro.spaprac2;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                setUpMap();
                mapInit();
            }
        }
    }
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    private void mapInit()
    {
        LatLng sydney = new LatLng(-33.867,151.206);
        LatLng tokyo = new LatLng(35.681382,139.766084);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        /*
        * Othe supported types include : MAP_TYPE_NORMAL,
        * MAP_TYPE_TERRAIN,MAP_HYBRID and MAP_TYPE_NONE
        * */
        mMap.setMyLocationEnabled(true);
        //CameraPosition camerapos = new CameraPosition.Builder()
        //        .target(new LatLng(35.681382,139.766084)).zoom(15.5f).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 13));
        mMap.addMarker(new MarkerOptions()
                .title("東京駅")
                .snippet("記念suicaが馬鹿売れした")
                .position(tokyo));
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Location myLocate = locationManager.getLastKnownLocation("gps");
        System.out.println();

    }
}
