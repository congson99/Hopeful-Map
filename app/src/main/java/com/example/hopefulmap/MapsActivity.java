package com.example.hopefulmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
        int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        //Check no choose
        if (addressArrays != null){
            int totaltemp = 0;
            for (int i = 0; i < addressArrays.length; i++){
                totaltemp += addressArrays[i];
            }
            if (totaltemp == 1) addressArrays = null;
        }

        //Array Name List
        String[] AddressNames ={"Trường đại học Bách Khoa thành phố Hồ Chí Minh",   //0
                "Trường Đại học Y Khoa Phạm Ngọc Thạch",            //1
                "Trường đại học Y Dược thành phố Hồ Chí Minh",      //2
                "Truờng đại học Khoa Hoc Tự Nhiên",                 //3
                "Trường đại học Kinh Tế",                           //4
                "Trường đại học Khoa Học Xã Hội và Nhân Văn"};      //5

        //Distance List
        int[][] DistanceArray = {
                {0,     1500,  2100,   3700,   5800,   6800},   //HCMUT
                {1500,  0,     2400,   5900,   4800,   4900},   //PNT
                {2100,  2400,  0,      2800,   5300,   6000},   //YDS
                {3700,  5900,  2800,   0,      3200,   3900},   //HCMUS
                {5800,  4800,  5300,   3200,   0,      1100},   //UEH
                {6800,  4900,  6000,   3900,   1100,   0   }};  //HCMUSSH

        //Draw line
        if (addressArrays != null){
            //get list in order
            int count = 0;
            for (int i = 0; i < addressArrays.length; i++){
                if (addressArrays[i] == 1) count++;
            }
            int[] route = findroute(filter(DistanceArray,addressArrays),0,count);

            //create list in order w real index
            int[] newRouteArray = new int[route.length];
            int temp = 1;
            for (int i=1; i < addressArrays.length; i++){
                if (addressArrays[i] == 1){
                    for (int j = 0; j < route.length; j++){
                        if (route[j] == temp) newRouteArray[j] = i;
                    }
                    temp++;
                }
            }

            //draw line from starting address
            if (newRouteArray[0] == 1){
                mMap.addPolyline(new PolylineOptions().add(
                        new LatLng(10.771399, 106.657877),
                        new LatLng(10.770353, 106.658132),
                        new LatLng(10.776467, 106.663592),
                        new LatLng(10.773367, 106.664814),
                        new LatLng(10.773784, 106.665841)
                        ).width(10).color(Color.RED)
                );
            }
            if (newRouteArray[0] == 2){
                mMap.addPolyline(new PolylineOptions().add(
                        new LatLng(10.771399, 106.657877),
                        new LatLng(10.755242, 106.662439),
                        new LatLng(10.755415, 106.662935)
                        ).width(10).color(Color.RED)
                );
            }
            if (newRouteArray[0] == 3){
                mMap.addPolyline(new PolylineOptions().add(
                        new LatLng(10.771399, 106.657877),
                        new LatLng(10.763808, 106.660013),
                        new LatLng(10.768066, 106.667892),
                        new LatLng(10.767674, 106.674395),
                        new LatLng(10.765422, 106.681644),
                        new LatLng(10.763115, 106.682562)
                        ).width(10).color(Color.RED)
                );
            }
            if (newRouteArray[0] == 4){
                mMap.addPolyline(new PolylineOptions().add(
                        new LatLng(10.771399, 106.657877),
                        new LatLng(10.770362, 106.658185),
                        new LatLng(10.782882, 106.672100),
                        new LatLng(10.776703, 106.683684),
                        new LatLng(10.785485, 106.692771),
                        new LatLng(10.783113, 106.695353)
                        ).width(10).color(Color.RED)
                );
            }
            if (newRouteArray[0] == 5){
                mMap.addPolyline(new PolylineOptions().add(
                        new LatLng(10.771399, 106.657877),
                        new LatLng(10.763861, 106.659970),
                        new LatLng(10.768000, 106.667927),
                        new LatLng(10.767740, 106.674376),
                        new LatLng(10.788364, 106.695563),
                        new LatLng(10.784587, 106.699662),
                        new LatLng(10.786622, 106.701636),
                        new LatLng(10.785482, 106.702834)
                        ).width(10).color(Color.RED)
                );
            }

            //Draw other line
            for (int i = 0; i<newRouteArray.length-1;i++){
                //Start at 1 or end at 1
                if (newRouteArray[i] == 1 || newRouteArray[i + 1]==1){
                    if (newRouteArray[i] == 2 || newRouteArray[i+1] == 2){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.773784, 106.665841),
                                new LatLng(10.773367, 106.664819),
                                new LatLng(10.759854, 106.668865),
                                new LatLng(10.756197, 106.666151),
                                new LatLng(10.755415, 106.662935)
                                ).width(10).color(Color.BLUE)
                        );
                    }
                    if (newRouteArray[i] == 3 || newRouteArray[i+1] == 3){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.773784, 106.665841),
                                new LatLng(10.773367, 106.664819),
                                new LatLng(10.767641, 106.667040),
                                new LatLng(10.767989, 106.667791),
                                new LatLng(10.767724, 106.674389),
                                new LatLng(10.765432, 106.681631),
                                new LatLng(10.763115, 106.682562)
                                ).width(10).color(Color.BLUE)
                        );
                    }
                    if (newRouteArray[i] == 4 || newRouteArray[i+1] == 4){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.773784, 106.665841),
                                new LatLng(10.773367, 106.664819),
                                new LatLng(10.767641, 106.667040),
                                new LatLng(10.767989, 106.667791),
                                new LatLng(10.767724, 106.674389),
                                new LatLng(10.765432, 106.681631),
                                new LatLng(10.781662, 106.696882),
                                new LatLng(10.783113, 106.695353)
                                ).width(10).color(Color.BLUE)
                        );
                    }
                    if (newRouteArray[i] == 5 || newRouteArray[i+1] == 5){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.773784, 106.665841),
                                new LatLng(10.773367, 106.664819),
                                new LatLng(10.767641, 106.667040),
                                new LatLng(10.767989, 106.667791),
                                new LatLng(10.767724, 106.674389),
                                new LatLng(10.765432, 106.681631),
                                new LatLng(10.786562, 106.701613),
                                new LatLng(10.785482, 106.702834)
                                ).width(10).color(Color.BLUE)
                        );
                    }
                }
                //Start at 2 or end at 2
                if (newRouteArray[i] == 2 || newRouteArray[i + 1]==2){
                    if (newRouteArray[i] == 3 || newRouteArray[i+1] == 3){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.755415, 106.662935),
                                new LatLng(10.757670, 106.674453),
                                new LatLng(10.765341, 106.681624),
                                new LatLng(10.763115, 106.682562)
                                ).width(10).color(Color.GREEN)
                        );
                    }
                    if (newRouteArray[i] == 4 || newRouteArray[i+1] == 4){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.755415, 106.662935),
                                new LatLng(10.757607, 106.674367),
                                new LatLng(10.781659, 106.696934),
                                new LatLng(10.783113, 106.695353)
                                ).width(10).color(Color.GREEN)
                        );
                    }
                    if (newRouteArray[i] == 5 || newRouteArray[i+1] == 5){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.755415, 106.662935),
                                new LatLng(10.757607, 106.674367),
                                new LatLng(10.786552, 106.701635),
                                new LatLng(10.785482, 106.702834)
                                ).width(10).color(Color.GREEN)
                        );
                    }
                }
                //Start at 3 or end at 3
                if (newRouteArray[i] == 3 || newRouteArray[i + 1]==3){
                    if (newRouteArray[i] == 4 || newRouteArray[i+1] == 4){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.763115, 106.682562),
                                new LatLng(10.765443, 106.681643),
                                new LatLng(10.781659, 106.696913),
                                new LatLng(10.783113, 106.695353)
                                ).width(10).color(Color.GRAY)
                        );
                    }
                    if (newRouteArray[i] == 5 || newRouteArray[i+1] == 5){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.763115, 106.682562),
                                new LatLng(10.765443, 106.681643),
                                new LatLng(10.786615, 106.701635),
                                new LatLng(10.785482, 106.702834)
                                ).width(10).color(Color.GRAY)
                        );
                    }
                }
                //Start at 4 or end at 4
                if (newRouteArray[i] == 4 || newRouteArray[i + 1]==4){
                    if (newRouteArray[i] == 5 || newRouteArray[i+1] == 5){
                        mMap.addPolyline(new PolylineOptions().add(
                                new LatLng(10.783113, 106.695353),
                                new LatLng(10.780324, 106.698449),
                                new LatLng(10.785217, 106.703108),
                                new LatLng(10.785482, 106.702834)
                                ).width(10).color(Color.BLACK)
                        );
                    }
                }
            }

        }


        // Add a marker in HCMUT and move the camera
        // HCMUT is the starting address
        LatLng hcmut = new LatLng(10.771399, 106.657877);
        mMap.addMarker(new MarkerOptions().position(hcmut).title(AddressNames[0])
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.777879, 106.681648), 13));

        //Add markets in the destination addresses
        if (addressArrays != null){
            if (addressArrays[1] == 1){
                LatLng pnt = new LatLng(10.773784, 106.665841);
                mMap.addMarker(new MarkerOptions().position(pnt).title(AddressNames[1])
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[2] == 1){
                LatLng yds = new LatLng(10.755415, 106.662935);
                mMap.addMarker(new MarkerOptions().position(yds).title(AddressNames[2])
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[3] == 1){
                LatLng hcmus = new LatLng(10.763115, 106.682562);
                mMap.addMarker(new MarkerOptions().position(hcmus).title(AddressNames[3])
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[4] == 1){
                LatLng ueh = new LatLng(10.783113, 106.695353);
                mMap.addMarker(new MarkerOptions().position(ueh).title(AddressNames[4])
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
            if (addressArrays[5] == 1){
                LatLng hcmussh = new LatLng(10.785482, 106.702834);
                mMap.addMarker(new MarkerOptions().position(hcmussh).title(AddressNames[5])
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
            }
        }
    }


    //Create new Array from DistanceArray
    static int[][] filter(int[][] DistanceArray, int[] addressArrays){
        int count = 0;
        for (int i = 0; i < addressArrays.length; i++){
            if (addressArrays[i] == 1) count++;
        }
        int[][] tempArray = new int[count][count];
        int temprow = 0, tempcolumn = 0;
        for (int i = 0; i < addressArrays.length; i++){
            if (addressArrays[i] == 1){
                for (int j = 0; j < addressArrays.length; j++){
                    if (addressArrays[j] ==1){
                        tempArray[temprow][tempcolumn] = DistanceArray[i][j];
                        tempcolumn++;
                    }
                }
                temprow++;
                tempcolumn = 0;
            }
        }
        return tempArray;
    }

    //_________NextPermutation____________
    // Function to swap the data
    // present in the left and right indices
    public static int[] swap(int data[], int left, int right)
    {

        // Swap the data
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;

        // Return the updated array
        return data;
    }
    // Function to reverse the sub-array
    // starting from left to the right
    // both inclusive
    public static int[] reverse(int data[], int left, int right)
    {

        // Reverse the sub-array
        while (left < right) {
            int temp = data[left];
            data[left++] = data[right];
            data[right--] = temp;
        }

        // Return the updated array
        return data;
    }
    // Function to find the next permutation
    // of the given integer array
    public static boolean findNextPermutation(int data[])
    {

        // If the given dataset is empty
        // or contains only one element
        // next_permutation is not possible
        if (data.length <= 1)
            return false;

        int last = data.length - 2;

        // find the longest non-increasing suffix
        // and find the pivot
        while (last >= 0) {
            if (data[last] < data[last + 1]) {
                break;
            }
            last--;
        }

        // If there is no increasing pair
        // there is no higher order permutation
        if (last < 0)
            return false;

        int nextGreater = data.length - 1;

        // Find the rightmost successor to the pivot
        for (int i = data.length - 1; i > last; i--) {
            if (data[i] > data[last]) {
                nextGreater = i;
                break;
            }
        }

        // Swap the successor and the pivot
        data = swap(data, nextGreater, last);

        // Reverse the suffix
        data = reverse(data, last + 1, data.length - 1);

        // Return true as the next_permutation is done
        return true;
    }
    //____________end_NextPermutation___________


    //Find the list in order
    int[] findroute(int graph[][], int s, int V)
    {
        int[] route = new int[V-1];
        // store all vertex apart from source vertex
        int[] vec = new int[V-1];
        int temp = 0;
        for (int i = 0; i < V; i++)
            if (i != s){
                vec[temp] = i;
                temp++;
            }

        // store minimum weight Hamiltonian Cycle.
        int min_path = Integer.MAX_VALUE;
        do {

            // store current Path weight(cost)
            int currentPathweight = 0;

            // compute current path weight
            int k = s;
            for (int i = 0; i < vec.length; i++) {
                currentPathweight += graph[k][vec[i]];
                k = vec[i];
            }
//            currentPathweight += graph[k][s];

            if (currentPathweight < min_path) route = vec;
            // update minimum
            min_path = Math.min(min_path, currentPathweight);

        } while (findNextPermutation(vec));
        int[] newroute = new int[route.length];
        for (int i = 0;i<route.length;i++){
            newroute[i] = route[route.length-1-i];
        }
        return newroute;
    }
}