package com.example.hopefulmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class EnterActivity extends AppCompatActivity {

    Button b2;
    Button b3;

    CheckBox c1, c2, c3, c4, c5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        Intent intentf = getIntent();
        final int[] addressArrays = intentf.getIntArrayExtra("AddressArray");

        b2 = (Button) findViewById(R.id.enter_button2);
        b3 = (Button) findViewById(R.id.enter_button3);
        c1 = (CheckBox) findViewById(R.id.checkBox1);
        c2 = (CheckBox) findViewById(R.id.checkBox2);
        c3 = (CheckBox) findViewById(R.id.checkBox3);
        c4 = (CheckBox) findViewById(R.id.checkBox4);
        c5 = (CheckBox) findViewById(R.id.checkBox5);

        //recheck
        if (addressArrays != null){
            if (addressArrays[0] == 1) c1.setChecked(true);
            if (addressArrays[1] == 1) c2.setChecked(true);
            if (addressArrays[2] == 1) c3.setChecked(true);
            if (addressArrays[3] == 1) c4.setChecked(true);
            if (addressArrays[4] == 1) c5.setChecked(true);
        }

        //move to Address List
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] addressArrays = {0,0,0,0,0};
                if (c1.isChecked()) addressArrays[0] = 1;
                if (c2.isChecked()) addressArrays[1] = 1;
                if (c3.isChecked()) addressArrays[2] = 1;
                if (c4.isChecked()) addressArrays[3] = 1;
                if (c5.isChecked()) addressArrays[4] = 1;
                Intent intent = new Intent(EnterActivity.this, ListActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

        //move to Map
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] addressArrays = {0,0,0,0,0};
                if (c1.isChecked()) addressArrays[0] = 1;
                if (c2.isChecked()) addressArrays[1] = 1;
                if (c3.isChecked()) addressArrays[2] = 1;
                if (c4.isChecked()) addressArrays[3] = 1;
                if (c5.isChecked()) addressArrays[4] = 1;
                Intent intent = new Intent(EnterActivity.this, MapsActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

    }
}