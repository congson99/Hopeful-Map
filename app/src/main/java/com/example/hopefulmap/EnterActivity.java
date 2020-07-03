package com.example.hopefulmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
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

        String[] AddressNames ={"Trường đại học Bách Khoa thành phố Hồ Chí Minh",
                                "Trường Đại học Y Khoa Phạm Ngọc Thạch",
                                "Trường đại học Y Dược thành phố Hồ Chí Minh",
                                "Truờng đại học Khoa Hoc Tự Nhiên",
                                "Trường đại học Kinh Tế",
                                "Trường đại học Khoa Học Xã Hội và Nhân Văn"};

        b2 = (Button) findViewById(R.id.enter_button2);
        b3 = (Button) findViewById(R.id.enter_button3);
        c1 = (CheckBox) findViewById(R.id.checkBox1);
        c2 = (CheckBox) findViewById(R.id.checkBox2);
        c3 = (CheckBox) findViewById(R.id.checkBox3);
        c4 = (CheckBox) findViewById(R.id.checkBox4);
        c5 = (CheckBox) findViewById(R.id.checkBox5);

        c1.setText(AddressNames[1]);
        c2.setText(AddressNames[2]);
        c3.setText(AddressNames[3]);
        c4.setText(AddressNames[4]);
        c5.setText(AddressNames[5]);

        //recheck
        if (addressArrays != null){
            if (addressArrays[1] == 1) c1.setChecked(true);
            if (addressArrays[2] == 1) c2.setChecked(true);
            if (addressArrays[3] == 1) c3.setChecked(true);
            if (addressArrays[4] == 1) c4.setChecked(true);
            if (addressArrays[5] == 1) c5.setChecked(true);
        }

        //move to Address List
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] addressArrays = {1,0,0,0,0,0};
                if (c1.isChecked()) addressArrays[1] = 1;
                if (c2.isChecked()) addressArrays[2] = 1;
                if (c3.isChecked()) addressArrays[3] = 1;
                if (c4.isChecked()) addressArrays[4] = 1;
                if (c5.isChecked()) addressArrays[5] = 1;
                Intent intent = new Intent(EnterActivity.this, ListActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

        //move to Map
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] addressArrays = {1,0,0,0,0,0};
                if (c1.isChecked()) addressArrays[1] = 1;
                if (c2.isChecked()) addressArrays[2] = 1;
                if (c3.isChecked()) addressArrays[3] = 1;
                if (c4.isChecked()) addressArrays[4] = 1;
                if (c5.isChecked()) addressArrays[5] = 1;
                Intent intent = new Intent(EnterActivity.this, MapsActivity.class);
                intent.putExtra("AddressArray", addressArrays);
                startActivity(intent);
            }
        });

    }
}