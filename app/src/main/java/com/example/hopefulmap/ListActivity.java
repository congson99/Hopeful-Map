package com.example.hopefulmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class ListActivity extends AppCompatActivity {

    Button b1;
    Button b3;
    ListView lv;
    TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Intent from Enter
        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        //Name List
        String[] AddressNames ={"Trường đại học Bách Khoa thành phố Hồ Chí Minh",   //0
                            "Trường Đại học Y Khoa Phạm Ngọc Thạch",                //1
                            "Trường đại học Y Dược thành phố Hồ Chí Minh",          //2
                            "Truờng đại học Khoa Hoc Tự Nhiên",                     //3
                            "Trường đại học Kinh Tế",                               //4
                            "Trường đại học Khoa Học Xã Hội và Nhân Văn"};          //5

        //Distance List
        int[][] DistanceArray = {
                        {0,     1500,  2100,   3700,   5800,   6800},   //HCMUT
                        {1500,  0,     2400,   5900,   4800,   4900},   //PNT
                        {2100,  2400,  0,      2800,   5300,   6000},   //YDS
                        {3700,  5900,  2800,   0,      3200,   3900},   //HCMUS
                        {5800,  4800,  5300,   3200,   0,      1100},   //UEH
                        {6800,  4900,  6000,   3900,   1100,   0   }};  //HCMUSSH


        b1 = (Button) findViewById(R.id.list_button1); //move to Enter
        b3 = (Button) findViewById(R.id.list_button3); //move to Map
        lv = (ListView) findViewById(R.id.listView);
        tt = (TextView) findViewById(R.id.total);

        //Show address list in order
        if (addressArrays != null){
            int count = 0;
            for (int i = 0; i < addressArrays.length; i++){
                if (addressArrays[i] == 1) count++;
            }
            String[] nametemp = new String[count];
            int temp = 0;
            for (int i=0; i < addressArrays.length; i++){
                if (addressArrays[i] == 1){
                    nametemp[temp] = AddressNames[i];
                    temp++;
                }
            }
            int ans = travllingSalesmanProblem(filter(DistanceArray, addressArrays), 0, count);
            int[] route = findroute(filter(DistanceArray,addressArrays),0,count);

            String[] strings = new String[route.length];
            for (int i = 0; i < route.length; i++){
                strings[i] = String.valueOf(i+1) + ": " + nametemp[route[i]];
                lv.setAdapter(new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_expandable_list_item_1, strings));
            }

            tt.setText("Tổng quãng đường là: " + String.valueOf(ans)+" m");
       }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, EnterActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MapsActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

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

    //Find the shortest DistanceArray
    int travllingSalesmanProblem(int graph[][], int s, int V)
    {
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

            // update minimum
            min_path = Math.min(min_path, currentPathweight);

        } while (findNextPermutation(vec));

        return min_path;
    }

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