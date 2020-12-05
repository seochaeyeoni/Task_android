package com.example.mathgame_spongebob;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Random;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    Handler handler = new Handler();

    //SurfaceView
    MyThread mThread;
    MusicThread musicThread;
    TimeThread timeThread;
    SurfaceHolder mHolder;
    Context mContext;

    boolean isThread = true;

    Bitmap basket;
    int basket_x, basket_y;
    int basketWidth; //바구니 가로 크기
    int basketHeight; //바구니 세로 크기
    Bitmap leftKey, rightKey;
    int leftKey_x, leftKey_y;
    int rightKey_x, rightKey_y;
    int Width, Height;
    int score;
    int button_width;
    int basket_speed; //바구니 속도
    int balloon_speed; //풍선 속도
    int oNumber;
    int xNumber;

    Bitmap balloonimg; //풍선 이미지
    int balloonWidth; //풍선 가로 크기
    int balloonHeight; //풍선 세로 크기

    AnswerBalloon answerBalloon;
    Bitmap screen;

    Bitmap resultShow; //결과보기 이미지
    int resultShow_x, resultShow_y;

//    Bitmap closeButton;
//    int closeButton_x, closeButton_y;

    Bitmap scoreImage; //점수 30점
    int scoreImage_x, scoreImage_y;
    int score_count; //이 값만큼 점수가 화면에 남아 있는다
    int scoreImageOk = 0;
    int count = 3000;

    ArrayList<Balloon> balloon; //Balloon 클래스를 자료형으로 하는 ArrayList 객체선언
    ArrayList<Ranking> rankings;



    int number1, number2; //덧셈에 사용될 숫자
    int answer; //정답
    int[] wrongNumber = new int[5]; //오답 숫자 5개를 담을 배열

    SoundPool sPool;
    int dingdongdaeng, taeng;

    MediaPlayer mediaPlayer; //음악 재생을 위한 객체

    int type = MyApplication.type_prob;
    int level = MyApplication.level_prob;
    int time = MyApplication.time_prob;




    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mThread = new MyThread(holder, context);
        musicThread = new MusicThread(holder, context);
        timeThread = new TimeThread(holder, context);
        mediaPlayer = MediaPlayer.create(context, R.raw.music_spongebob); //음악파일
        InitApp();
        setFocusable(true);


    }

    private void InitApp() {
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();

        basket_speed = Width / 40;

        //level 에 따라 balloon speed 가 달라지게끔!
        if (level == 0) {
            balloon_speed = Width / 160;
        }
        if (level == 1) {
            balloon_speed = Width / 140;
        }
        if (level == 2) {
            balloon_speed = Width / 120;
        }

        balloon = new ArrayList<Balloon>(); //배열 형태의 오답 풍선 생성하기(객체명이 balloon인 객체 생성)
        button_width = Width / 6;
        basket = BitmapFactory.decodeResource(getResources(), R.drawable.spongebob);
        int x = Width / 4;
        int y = Height / 4;
        basket = Bitmap.createScaledBitmap(basket, x, y, true);

        //Bitmap 클래스의 getWidth, getHeight 메소드를 통해 그림크기 구하기
        basketWidth = basket.getWidth();
        basketHeight = basket.getHeight();

        basket_x = Width * 1 / 9;
        basket_y = Height - basketHeight - 100;

        leftKey = BitmapFactory.decodeResource(getResources(), R.drawable.toleft);
        leftKey_x = Width * 5 / 9;
        leftKey_y = Height * 7 / 9;
        leftKey = Bitmap.createScaledBitmap(leftKey, button_width, button_width, true);

        rightKey = BitmapFactory.decodeResource(getResources(), R.drawable.toright);
        rightKey_x = Width * 7 / 9;
        rightKey_y = Height * 7 / 9;
        rightKey = Bitmap.createScaledBitmap(rightKey, button_width, button_width, true);

        //풍선의 가로 크기와 세로 크기를 버튼을 가로 크기와 같게 하였다
        balloonimg = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
        balloonimg = Bitmap.createScaledBitmap(balloonimg, button_width, button_width, true);

        balloonWidth = balloonimg.getWidth();
        balloonHeight = balloonimg.getHeight();

        //배경화면
        screen = BitmapFactory.decodeResource(getResources(), R.drawable.background_spongebob);
        screen = Bitmap.createScaledBitmap(screen, Width, Height, true);

        Random r1 = new Random();
        int xx = r1.nextInt(Width);
        answerBalloon = new AnswerBalloon(x, 0, balloon_speed);


        //나중에 고쳐야 함
        scoreImage = BitmapFactory.decodeResource(getResources(), R.drawable.rainbow);
        scoreImage = Bitmap.createScaledBitmap(scoreImage, button_width, button_width, true);

        resultShow = BitmapFactory.decodeResource(getResources(), R.drawable.spongebob_rainbow);
        resultShow = Bitmap.createScaledBitmap(resultShow, Width * 2 / 3, Height * 1 / 3, true);
        resultShow_x = button_width;
        resultShow_y = button_width / 2;

        sPool = new SoundPool((10), AudioManager.STREAM_MUSIC, 0);
        dingdongdaeng = sPool.load(mContext, R.raw.dingdongdaeng, 1);
        taeng = sPool.load(mContext, R.raw.taeng, 2);

        //time에 따른 실행
        if (time == 0) {
            count = 30;
        }
        if (time == 1) {
            count = 60;
        }
        if (time == 2) {
            count = 120;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.start();
        mediaPlayer.start();

        //musicThread.start();
        timeThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isThread = false;
        mThread.interrupt();
        if (mediaPlayer != null) {
            mediaPlayer.release(); //release메소드를 이용해서 리소스를 release
            mediaPlayer = null;
        }
    }

    class TimeThread extends Thread {
        public TimeThread(SurfaceHolder holder, Context context) {
            mHolder = holder;
            mContext = context;
        }

        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    count--;
                } catch (Exception e) {
                    e.printStackTrace();
                } //try
            } //while
        } //run


    }

    class MusicThread extends Thread {
        public MusicThread(SurfaceHolder holder, Context context) {
            mHolder = holder;
            mContext = context;
        }
//        public void checkCollision() {
//            //바구니와 오답 풍선이 접촉했는지 체크
//            for (int i = balloon.size() - 1; i >= 0; i--) {
//                if (balloon.get(i).x + balloonWidth / 2 > basket_x &&
//                        balloon.get(i).x + balloonWidth / 2 < basket_x + basketWidth &&
//                        balloon.get(i).y + balloonHeight > basket_y &&
//                        balloon.get(i).y + balloonHeight < basket_y + basketWidth) {
//                    sPool.play(taeng, 1, 1, 9, 0, 1);
//                }
//            }
//            //바구니와 정답 풍선이 접촉했는지 체크
//            if (answerBalloon.x + balloonWidth / 2 > basket_x &&
//                    answerBalloon.x + balloonWidth / 2 < basket_x + balloonWidth &&
//                    answerBalloon.y + balloonHeight > basket_y &&
//                    answerBalloon.y + balloonHeight < basket_y + basketWidth) {
//                sPool.play(dingdongdaeng, 1, 1, 9, 0, 1);
//            }
//        }

        public void run() {
            Canvas canvas = null;
            while (isThread) {
                try {
                    synchronized (mHolder) {
                        //바구니와 오답 풍선이 접촉했는지 체크
                        for (int i = balloon.size() - 1; i >= 0; i--) {
                            if (balloon.get(i).x + balloonWidth / 2 > basket_x &&
                                    balloon.get(i).x + balloonWidth / 2 < basket_x + basketWidth &&
                                    balloon.get(i).y + balloonHeight > basket_y &&
                                    balloon.get(i).y + balloonHeight < basket_y + basketWidth) {
                                sPool.play(taeng, 1, 1, 9, 0, 1);
                            }
                        }
                        //바구니와 정답 풍선이 접촉했는지 체크
                        if (answerBalloon.x + balloonWidth / 2 > basket_x &&
                                answerBalloon.x + balloonWidth / 2 < basket_x + balloonWidth &&
                                answerBalloon.y + balloonHeight > basket_y &&
                                answerBalloon.y + balloonHeight < basket_y + basketWidth) {
                            sPool.play(dingdongdaeng, 1, 1, 9, 0, 1);
                        }
                    } //sync
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                } //try
            } //while

        } //run
    }

    ;



    //-------------------------------------------------------------
    // MyThread Class
    //-------------------------------------------------------------
    class MyThread extends Thread {
        public MyThread(SurfaceHolder holder, Context context) {
            mHolder = holder;
            mContext = context;
            makeQuestion();
        }

        //-------------------------------------------------------------
        // drawEverything
        //-------------------------------------------------------------
        public void drawEverything(Canvas canvas) {
            //오답 풍선이 4개보다 작으면 오답 풍선을 만든다
            if (balloon.size() < 4) {
                Random r1 = new Random(); //랜덤값 r1을 이용해서 풍선의 위치를 랜덤시킴
                int x = r1.nextInt(Width - button_width);
                int y = r1.nextInt(Height / 4);
                balloon.add(new Balloon(x, -y, balloon_speed));
            }


            Paint p1 = new Paint(); //풍선 안에 들어가는 흰 글씨
            Paint p2 = new Paint(); //투명도 있는 paint P2
            Paint p3 = new Paint(); //문제용 파란 글씨
            Paint p4 = new Paint(); //검은 글씨

            p1.setColor(Color.WHITE);
            p1.setTextSize(Width / 14);

            p2.setColor(Color.WHITE);
            p2.setTextSize(Width / 14);
            p2.setAlpha(100);

            p3.setColor(Color.BLUE);
            p3.setTextSize(Width / 12);

            p4.setColor(Color.BLACK);
            p4.setTextSize(Width / 14);


            Paint pp = new Paint();
            pp.setColor(0xFFFFD9EC);

            //게임 배경
            canvas.drawBitmap(screen, 0, 0, p1);

            //canvas.drawRect(0, 0, Width, Height, pp); //이게 그 분홍색 배경이구나ㅎㅎ 잡았다 요놈
            canvas.drawText("남은 시간 : " + Integer.toString(count), 0, Height / 7, p4);
            canvas.drawText("점수 : " + Integer.toString(score), 0, Height / 5, p4);

            if (type == 0) answer = number1 + number2;
            else if (type == 1) answer = number1 - number2;
            else answer = number1 * number2;

            if (type == 0)
                canvas.drawText("문제 : " + Integer.toString(number1) + "+" + Integer.toString(number2), 0, Height / 13, p3);
            else if (type == 1)
                canvas.drawText("문제 : " + Integer.toString(number1) + "-" + Integer.toString(number2), 0, Height / 13, p3);
            else if (type == 2)
                canvas.drawText("문제 : " + Integer.toString(number1) + "×" + Integer.toString(number2), 0, Height / 13, p3);

            //바구니가 화면을 거의 벗어 났을 경우 처리(양쪽모두)
            if (basket_x < -basketWidth + basketWidth / 8)
                basket_x = -basketWidth + basketWidth / 8;
            if (basket_x > Width - basketWidth / 8)
                basket_x = Width - basketWidth / 8;


            //바구니 왼쪽키, 오른쪽키 캔버스에 그리기
            canvas.drawBitmap(basket, basket_x, basket_y, p1);
            canvas.drawBitmap(leftKey, leftKey_x, leftKey_y, p1);
            canvas.drawBitmap(rightKey, rightKey_x, rightKey_y, p1);

            //오답 풍선 그리기
            for (Balloon tmp : balloon)
                canvas.drawBitmap(balloonimg, tmp.x, tmp.y, p1);

            //오답 풍선 안에 숫자 나타내기
            for (int i = balloon.size() - 1; i >= 0; i--)
                canvas.drawText(Integer.toString(wrongNumber[i]), balloon.get(i).x + balloonWidth / 6, balloon.get(i).y + balloonWidth * 2 / 3, p1);

            //정답 풍선 그리기
            canvas.drawBitmap(balloonimg, answerBalloon.x, answerBalloon.y, p1);

            //정답 풍선 안에 숫자 나타내기
            canvas.drawText(Integer.toString(answer), answerBalloon.x + balloonWidth / 6, answerBalloon.y + balloonWidth * 2 / 3, p1);

            //정답풍선 처리하기
            if (answerBalloon.y > Height) {
                Random r1 = new Random();
                int xx = r1.nextInt(Width - button_width);
                answerBalloon.x = xx;
                xx = r1.nextInt(300);
                answerBalloon.y = -xx;
            }


            moveBalloon(); //풍선 움직이기

            //풍선과 바구니가 맞닿았는지 체크하기
            checkCollision();

            //count--;

            if (count < 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.add_box, null, false);
                builder.setView(view);

                final Button ButtonSubmit = (Button) view.findViewById(R.id.btn_submit);
                final  Button ButtonBack = (Button) view.findViewById(R.id.btn_back);
                final RadioButton Boy = (RadioButton) view.findViewById(R.id.boy);
                final RadioButton Girl = (RadioButton) view.findViewById(R.id.girl);
                final EditText addName = (EditText) view.findViewById(R.id.name_add);
                final EditText addScore = (EditText) view.findViewById(R.id.grade_add);
                final EditText addMemo = (EditText) view.findViewById(R.id.memo_add);

                ButtonSubmit.setText("추가");

                final AlertDialog dialog = builder.create();
                dialog.show();

                //3. 다이얼로그에 있는 추가 버튼을 클릭하면
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //4.사용자가 입력한 내용을 가져와서
                        String strName = addName.getText().toString();
                        String strScore = addScore.getText().toString();

                        Ranking dict = new Ranking(strName, strScore);
                        rankings.add(0, dict);


                        //6. 어댑터에서 RecyclerView에 반영하도록 한다.
                        //classAdapter.notifyDataSetChanged();

                        //7. dialog 종료
                        dialog.dismiss();
                    }
                });

                //8. 뒤로가기 버튼 누르면 dialog 종료
                ButtonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

//            //시간이 0초 남게 되면 결과창이 나오도록 하기
//            if (count < 0) {
//                menuOk = 3;
//                count = timeValue * 1500 + 1500;
//                if (timeValue == 2) count = 6000;
//            }
//
//            if (menuOk == 0)
//                canvas.drawBitmap(menuButton, menuButton_x, menuButton_y, p1);


//            //도움말 나오기
//            if (menuOk == 2) {
//                canvas.drawRect(0, 0, Width, Height, pp);
//                canvas.drawText("스폰지밥을 움직여서", Width / 20, Height / 20, p3);
//                canvas.drawText("정답 게살버거를 받는 게임", Width / 20, Height / 9, p3);
//                canvas.drawText("만든이 : 서채연", Width / 20, Height / 4, p4);
//                canvas.drawBitmap(balloonimg, Width / 2, Height / 2, p1);
//                canvas.drawBitmap(basket, Width / 3, Height * 3 / 4, p1);
//                canvas.drawBitmap(closeButton, closeButton_x - button_width, closeButton_y + button_width * 1 / 3, p1);
//            }

            //정답 풍선을 받았을 경우 rainbow 표시하기
            if (scoreImageOk == 1) {
                score_count += 1;
                if (score_count < 40) {
                    canvas.drawBitmap(scoreImage, scoreImage_x, scoreImage_y, p1);
                } else {
                    score_count = 0;
                    scoreImageOk = 0;
                }
            }
        } //end of drawEverything

        public void makeQuestion() {
            Random r1 = new Random();

            //정답 풍선에 들어갈 숫자
            int x = r1.nextInt(99) + 1; //1부터 100까지 난수 발생
            number1 = x;
            x = r1.nextInt(99) + 1;
            number2 = x;

            if (type == 0) answer = number1 + number2;

            if (type == 1)
                if (number1 < number2) {
                    int tmp = number1;
                    number1 = number2;
                    number2 = tmp;
                }
            answer = number1 - number2; //여기 위치 맞겠지?

            if (type == 2) {
                r1 = new Random();
                x = r1.nextInt(8) + 1; //두자리 수 곱하기 한 자리 수 하려고!
                number2 = x;
                answer = number1 * number2;
            }

            //오답 풍선에 들어갈 숫자
            if (type == 2) //곱셈문제일 경우
                for (int i = 0; i < 5; i++) {
                    x = r1.nextInt(899) + 1;
                    while (x == answer) {
                        //오답 숫자가 정답 숫자가 같으면 다시 다른 숫자를 찾는다
                        x = r1.nextInt(197) + 1;
                    }
                    wrongNumber[i] = x;
                }
            else { //덧셈, 뺄셈 문제일 경우
                for (int i = 0; i < 5; i++) {
                    x = r1.nextInt(197) + 1;
                    while (x == answer) {
                        //오답 숫자가 정답 숫자가 같으면 다시 다른 숫자를 찾는다
                        x = r1.nextInt(197) + 1;
                    }
                    wrongNumber[i] = x;
                }
            }
        }

        public void moveBalloon() {
            //오답 풍선 움직이기
            for (int i = balloon.size() - 1; i >= 0; i--) {
                //balloon.get(i).move();
                balloon.get(i).y += balloon_speed;
            }

            //풍선이 화면아래로 사라지면 다시 위에서 나오도록 하기(위치 다시 랜덤하게!)
            for (int i = balloon.size() - 1; i >= 0; i--) {
                if (balloon.get(i).y > Height) {
                    Random r1 = new Random(); //랜덤값 r1을 이용해서 풍선의 위치를 랜덤시킴
                    int x = r1.nextInt(Width - button_width);
                    int y = r1.nextInt(Height / 4);
                    balloon.get(i).x = x;
                    balloon.get(i).y = -y;
                }
            }
            //정답 풍선 움직이기
            //answerBalloon.move();
            answerBalloon.y += balloon_speed;
        }

        public void checkCollision() {
            //바구니와 오답 풍선이 접촉했는지 체크
            for (int i = balloon.size() - 1; i >= 0; i--) {
                if (balloon.get(i).x + balloonWidth / 2 > basket_x &&
                        balloon.get(i).x + balloonWidth / 2 < basket_x + basketWidth &&
                        balloon.get(i).y + balloonHeight > basket_y &&
                        balloon.get(i).y + balloonHeight < basket_y + basketWidth) {
                    balloon.remove(i);
                    score -= 10;
                    xNumber += 1;
                    //sPool.play(taeng, 1, 1, 9, 0, 1);
                }
            }
            //바구니와 정답 풍선이 접촉했는지 체크
            if (answerBalloon.x + balloonWidth / 2 > basket_x &&
                    answerBalloon.x + balloonWidth / 2 < basket_x + balloonWidth &&
                    answerBalloon.y + balloonHeight > basket_y &&
                    answerBalloon.y + balloonHeight < basket_y + basketWidth) {
                score += 30;
                xNumber += 1;
                makeQuestion();
                Random r1 = new Random();
                int xx = r1.nextInt(Width - button_width);
                answerBalloon.x = xx;
                xx = r1.nextInt(300);
                answerBalloon.y = -xx;
                scoreImageOk = 1;
                scoreImage_x = basket_x - button_width / 2;
                scoreImage_y = basket_y - button_width / 2;
                //sPool.play(dingdongdaeng, 1, 1, 9, 0, 1);
            }
        }

        public void run() {
            Canvas canvas = null;
            while (isThread) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        drawEverything(canvas);
                    } //sync
                }
                finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                } //try
            } //while
        } //run
    } //Thread

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0, y = 0;
        boolean returnValue = false;

        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE) {
            x = (int) event.getX();
            y = (int) event.getY(); //invalidate();
            returnValue = true;

            if ((x > leftKey_x) && (x < leftKey_x + button_width) &&
                    (y > leftKey_y) && (y < leftKey_y + button_width))
                basket_x -= basket_speed;

            if ((x > rightKey_x) && (x < rightKey_x + button_width) &&
                    (y > rightKey_y) && (y < rightKey_y + button_width))
                basket_x += basket_speed;
        }
        //왜 이렇게 쓰는 거지? 여기만 한 번 쓰면 안되냐옹이
        if ((x > leftKey_x) && (x < leftKey_x + button_width) &&
                (y > leftKey_y) && (y < leftKey_y + button_width))
            basket_x -= basket_speed;

        if ((x > rightKey_x) && (x < rightKey_x + button_width) &&
                (y > rightKey_y) && (y < rightKey_y + button_width))
            basket_x += basket_speed;

        //이렇게 하니까 아무데나 누르면 문제 새로 만든다,,ㅎㅎ
//        //start 버튼 누르면 스레드가 실행되게?
//        if (MyApplication.start_game) {
//            mThread.makeQuestion();
//            oNumber = 0;
//            xNumber = 0;
//        }




        return returnValue;
        //end of touchEvent
    }


    //-------------------------------------------------------------
    // onKeyDown
    //-------------------------------------------------------------
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        synchronized (mHolder) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_DPAD_LEFT:
//                    break;
//
//                case KeyEvent.KEYCODE_DPAD_RIGHT:
//                    break;
//
//                case KeyEvent.KEYCODE_DPAD_UP:
//                    break;
//
//                default:
//            }
//        }
//        return false;
//    }
} //SurfaceView
