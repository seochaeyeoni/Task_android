package com.example.musicplayer_ex;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_MusicPlayer4 extends AppCompatActivity {

    ImageButton btn_play;
    ImageButton btn_back;
    ImageButton btn_1;
    ImageButton btn_2;
    ImageButton btn_heart;

    MediaPlayer mediaPlayer; //음악 재생을 위한 객체
    boolean flag = true;
    int pos; //재생 멈춘 시점
    SeekBar seekBar1; //음악 재생위치를 나타내는 시크바
    boolean isPlaying = false; //재생중인지 확인할 변수
    private int seekForwardTime = 10000; //단위가 milliseconds
    private int seekBackwardTime = 10000;

    private boolean isPaused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player4);
        mediaPlayer = MediaPlayer.create(MainActivity_MusicPlayer4.this, R.raw.summer_night_citypop); //음악파일

        //Toast.makeText(this, "onCreate() 호출됨", Toast.LENGTH_LONG).show();



        seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        seekBar1.setMax(mediaPlayer.getDuration()); //음악의 총 길이를 시크바 최대값에 적용
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) //사용자가 시크바를 움직이면
                    mediaPlayer.seekTo(progress); //재생위치를 바꿔준다(움직인 곳에서의 음악재생)
            }
        });

        //이 코드가 지금 작동이 안되는 듯?
        new Thread(new Runnable() { //쓰레드 생성
            @Override
            public void run() {
                while(mediaPlayer.isPlaying()){ //음악이 실행중일 때 계속 돌아가게 함
                    try{
                        Thread.sleep(1000); //1초마다 시크바 움직이게 함
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    //현재 재생중인 위치를 가져와 시크바에 적용
                    seekBar1.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        }).start();

        //이렇게 해봐도 안되는 듯?
        class MyThread extends Thread {
            @Override
            public void run() { // 쓰레드가 시작되면 콜백되는 메서드
                // 씨크바 막대기 조금씩 움직이기 (노래 끝날 때까지 반복)
                while(isPlaying) {
                    seekBar1.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        }
        //출처: https://bitsoul.tistory.com/28 [Happy Programmer~]



        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.putExtra("name", "mike"); //다른 액티비티로 전달_예제로 해 본 거
                //setResult(Activity.RESULT_OK, intent);
                finish(); //이전 액티비티로 백
            }
        });

        btn_play = (ImageButton) findViewById(R.id.btn_play);
        //재생과 일시정지를 한 번에!
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                mediaPlayer.setLooping(false); //true는 무한반복

                if(mediaPlayer.isPlaying()) {
                    btn_play.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else {
                    btn_play.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });



        btn_2 = (ImageButton) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current song position
                int currentPosition = mediaPlayer.getCurrentPosition();
                //check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mediaPlayer.getDuration()){
                    //forward song
                    mediaPlayer.seekTo(currentPosition + seekForwardTime);
                } else { //forward to end position
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });


        btn_1 = (ImageButton) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current song position
                int currentPosition = mediaPlayer.getCurrentPosition();
                //check if seekBackward time is lesser than current position
                if(currentPosition - seekBackwardTime >= 0 ){
                    //backward song
                    mediaPlayer.seekTo(currentPosition - seekBackwardTime);
                } else { //backward to starting position
                        mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });

        btn_heart = (ImageButton) findViewById(R.id.btn_heart);
        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_heart.setBackgroundResource(R.drawable.heart_pink);
            }
        });

    }


    //onStart()에서 첫번째로 지나갈 때만 자동재생이 안 되게 해보자
    //onResume()에서 onPause()이후에 실행될때만 볼륨을 다시 높이게끔 해보자

   @Override
    protected void onStart() {
       super.onStart();
        //처음 시작할 때는 노래가 나오게 하고 싶지 않음.
        //stop 되었다가 다시 액티비티로 돌아올 때는 자동으로 재생 -> onRestart()를 이용해서 할 수도 있음.
       //Toast.makeText(this, "onStart() 호출됨", Toast.LENGTH_LONG).show();

       if(isPaused) {
           mediaPlayer.start(); //전화 통화가 끝나면 멈춤상태 음악이 다시 플레이 된다.
       }
        //UI에 영향을 주는 변화를 감지하기 위해 BroadcastReceiver를 등록할 수 있다.
        //그리고 onStop()에서 반드시 해제시켜줘야 한다.
    }

    @Override
    protected void onResume() { //앱이 다시 실행이 될 때 무조건 실행해야 하는 것은 여기서 실행
        super.onResume();
        //시작 애니메이션, 카메라 같은 exclusive-access 디바이스를 오픈.
        //화면을 가득 채우지는 않는 화면을 없애고, MediaVolume 음량을 현재의 5배로 서서히 늘리자(onPause()상태로부터 원상복구)
        //Toast.makeText(this, "onResume() 호출됨", Toast.LENGTH_LONG).show();

        };

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        //여기서 전화가 왔을 때 MediaVolume 음량을 현재의 20퍼센트로 서서히 줄이자
        //AudioManager 이용
        //Toast.makeText(this, "onPause() 호출됨", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //홈버튼을 누르면 백그라운드로 들어가면서 재생되는 음악이 멈춘다.
        //전화를 받으면 재생되던 음악이 자동으로 멈춘다.
        //Toast.makeText(this, "onStop() 호출됨", Toast.LENGTH_LONG).show();

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        //이걸 빼면 백그라운드에서도 재생이 된다. 근데 왜 백그라운드 재생은 서비스 이용하지?
    }

    //    public void onClickButton(View view){
//        if(flag) {
//            mediaPlayer.pause();
//            flag = false;
//        } else {
//            mediaPlayer.start();
//            flag = true;
//        }
//    }


    //MediaPlayer는 시스템 리소스를 잡아먹는다.
    //MediaPlayer는 필요이상으로 사용하지 않도록 주의해야 한다.


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "onDestroy() 호출됨", Toast.LENGTH_LONG).show();

        if(mediaPlayer != null) {
            mediaPlayer.release(); //release메소드를 이용해서 리소스를 release
            mediaPlayer = null;
        }
        }
    }

