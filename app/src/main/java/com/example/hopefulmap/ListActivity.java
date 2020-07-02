package com.example.hopefulmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class ListActivity extends AppCompatActivity {

    Button b1;
    Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        String[] AddressNames = {"HCMUT", "PNT", "YDS", "HCMUS", "UEH", "HCMUSSH"};

        int[][] DistanceArray = {
                        {0,     1500,  2100,   3700,   5800,   6800},   //HCMUT
                        {1500,  0,     2400,   5900,   4800,   4900},   //PNT
                        {2100,  2400,  0,      2800,   5300,   6000},   //YDS
                        {3700,  5900,  2800,   0,      3200,   3900},   //HCMUS
                        {5800,  4800,  5300,   3200,   0,      1100},   //UEH
                        {6800,  4900,  6000,   3900,   1100,   0   }};  //HCMUSSH

        b1 = (Button) findViewById(R.id.list_button1); //move to Enter
        b3 = (Button) findViewById(R.id.list_button3); //move to Map

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
}