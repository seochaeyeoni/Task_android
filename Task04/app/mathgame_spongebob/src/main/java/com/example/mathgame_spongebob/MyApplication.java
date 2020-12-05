package com.example.mathgame_spongebob;

import android.app.Application;

public class MyApplication extends Application {

    public static boolean start_game;
    public static int type_prob; //0:덧셈, 1:뺄셈, 2:곱셈
    public static int level_prob; //0:1단계, 2:2단계, 3:3단계
    public static int time_prob; //0:30초, 1:60초, 3:120초

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
