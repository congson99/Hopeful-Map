package com.example.hopefulmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Intent result from Enter
        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        //Array Name List
        String[] AddressNames ={"Trường đại học Bách Khoa thành phố Hồ Chí Minh",   //0
                                "Trường Đại học Y Khoa Phạm Ngọc Thạch",            //1
                                "Trường đại học Y Dược thành phố Hồ Chí Minh",      //2
                                "Truờng đại học Khoa Hoc Tự Nhiên",                 //3
                                "Trường đại học Kinh Tế",                           //4
                                "Trường đại học Khoa Học Xã Hội và Nhân Văn"};      //5



        b1 = (Button) findViewById(R.id.map_button1); //move to Enter
        b2 = (Button) findViewById(R.id.map_button2); //move to Address List

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, EnterActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, ListActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });
    }

    //Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Intent from Enter
        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        //Array Name List
        String[] AddressNames ={"Trường đại học Bách Khoa thành phố Hồ Chí Minh",   //0
                "Trường Đại học Y Khoa Phạm Ngọc Thạch",            //1
                "Trường đại học Y Dược thành phố Hồ Chí Minh",      //2
                "Truờng đại học Khoa Hoc Tự Nhiên",                 //3
                "Trường đại học Kinh Tế",                           //4
                "Trường đại học Khoa Học Xã Hội và Nhân Văn"};      //5


        // Add a marker in HCMUT and move the camera
        // HCMUT is the starting address
        LatLng hcmut = new LatLng(10.771399, 106.657877);
        mMap.addMarker(new MarkerOptions().position(hcmut).title(AddressNames[0]).icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmut, 12));

        //Add markets in the destination addresses
        if (addressArrays != null){
            if (addressArrays[1] == 1){
                LatLng pnt = new LatLng(10.773784, 106.665841);
                mMap.addMarker(new MarkerOptions().position(pnt).title(AddressNames[1]).icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[2] == 1){
                LatLng yds = new LatLng(10.755415, 106.662935);
                mMap.addMarker(new MarkerOptions().position(yds).title(AddressNames[2]).icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[3] == 1){
                LatLng hcmus = new LatLng(10.763115, 106.682562);
                mMap.addMarker(new MarkerOptions().position(hcmus).title(AddressNames[3]).icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[4] == 1){
                LatLng ueh = new LatLng(10.783113, 106.695353);
                mMap.addMarker(new MarkerOptions().position(ueh).title(AddressNames[4]).icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[5] == 1){
                LatLng hcmussh = new LatLng(10.785482, 106.702834);
                mMap.addMarker(new MarkerOptions().position(hcmussh).title(AddressNames[5]).icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
        }
    }
}