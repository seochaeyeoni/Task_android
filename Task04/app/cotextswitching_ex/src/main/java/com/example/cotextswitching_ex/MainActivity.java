package com.example.cotextswitching_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> imageArrayList = new ArrayList<>();
    Handler handler = new Handler(); //이걸써서 화면에 그려줘야 한다, 여러개의 화면을 이걸로 해 줄 수 있겠지요
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Runnable firstRunnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <1000; i++) {
                    Log.d(TAG, i + "");
                }
            }
        };
        Thread firstThread = new Thread(firstRunnable);
        Thread secondThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1000; i > 0; i--) {
                    Log.d(TAG, i + "");
                }

            }
        });
        firstThread.start();
        secondThread.start();
        //그 때 그 때에 따라 랜덤으로 컴퓨터가 순서 결정


    }
}
