package com.example.mathgame_spongebob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.example.mathgame_spongebob.MyApplication.type_prob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //처음 배경화면 투명도 설정
        Drawable alpha = ((ImageView)findViewById(R.id.background_main)).getBackground();
        alpha.setAlpha(50);

        ImageButton start = (ImageButton) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity_Play.class);
                startActivity(intent1); //응답을 받고 싶은 때는 startActivityForResult를 써라.. 코드..
                MyApplication.start_game = true;
            }
        });
        ImageButton close = (ImageButton) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RadioGroup rGroup1 = (RadioGroup) findViewById(R.id.type);
        rGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.plus:
                        MyApplication.type_prob = 0;
//                        Toast.makeText(getApplicationContext(),
//                                "radio btn select plz", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.minus:
                        MyApplication.type_prob = 1;
                        break;
                    case R.id.multiply:
                        MyApplication.type_prob = 2;
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),
                                "radio btn select plz", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RadioGroup rGroup2 = (RadioGroup) findViewById(R.id.level);
        rGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.easy:
                        MyApplication.level_prob = 0;
                        break;
                    case R.id.normal:
                        MyApplication.level_prob = 1;
                        break;
                    case R.id.difficult:
                        MyApplication.level_prob = 2;
                        break;
                }
            }
        });
        RadioGroup rGroup3 = (RadioGroup) findViewById(R.id.time);
        rGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.time30:
                        MyApplication.time_prob = 0;
                        break;
                    case R.id.time60:
                        MyApplication.time_prob = 1;
                        break;
                    case R.id.time120:
                        MyApplication.time_prob = 2;
                        break;
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0);
        }
        return false;
    }

    //이렇게 해도 됨!
//    public void onRadioButtonCLicked(View v) {
//        boolean checked = ((RadioButton) v).isChecked();
//
//        switch (v.getId()) {
//            case R.id.plus:
//                MyApplication.type_prob = 0;
//                Toast.makeText(getApplicationContext(),
//                        "radio btn select plz", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.minus:
//                MyApplication.type_prob = 1;
//                break;
//            case R.id.multiply:
//                MyApplication.type_prob = 2;
//                break;
//        }
//    }
    }


