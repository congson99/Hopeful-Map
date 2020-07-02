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

        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        // Add a marker in HCMUT and move the camera
        // HCMUT is the starting address
        LatLng hcmut = new LatLng(10.771399, 106.657877);
        mMap.addMarker(new MarkerOptions().position(hcmut).title("HCMUT").icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmut, 12));

        //Add markets in the destination addresses
        if (addressArrays != null){
            if (addressArrays[0] == 1){
                LatLng pnt = new LatLng(10.773784, 106.665841);
                mMap.addMarker(new MarkerOptions().position(pnt).title("PNT").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[1] == 1){
                LatLng yds = new LatLng(10.755415, 106.662935);
                mMap.addMarker(new MarkerOptions().position(yds).title("YDS").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[2] == 1){
                LatLng hcmus = new LatLng(10.763115, 106.682562);
                mMap.addMarker(new MarkerOptions().position(hcmus).title("HCMUS").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[3] == 1){
                LatLng ueh = new LatLng(10.783113, 106.695353);
                mMap.addMarker(new MarkerOptions().position(ueh).title("UEH").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[4] == 1){
                LatLng hcmussh = new LatLng(10.785482, 106.702834);
                mMap.addMarker(new MarkerOptions().position(hcmussh).title("HCMUS").icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
        }
    }
}