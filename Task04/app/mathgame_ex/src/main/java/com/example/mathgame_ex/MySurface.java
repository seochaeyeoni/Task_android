package com.example.mathgame_ex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowId;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    //SurfaceView
    MyThread mThread;
    SurfaceHolder mHolder;
    Context mContext;

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

    int menuOk = 1; //환경설정이 나오게 하는 변수. 1이면 환경설정 창이 나온다. 2는 도움말
    Bitmap menuButton; //메뉴 버튼 이미지
    Bitmap helpButton; //도움말 버튼
    int helpButton_x, helpButton_y;
    Bitmap closeButton;
    int closeButton_x, closeButton_y;
    Bitmap applyButton;
    int applyButton_x, applyButton_y;

    Bitmap plusicon;
    Bitmap minusicon;
    Bitmap multiicon;

    int menuButton_x, menuButton_y;
    int plusicon_x, plusicon_y;
    int minusicon_x, minusicon_y;
    int multiicon_x, multiicon_y;

    Bitmap time30; //시간선택 이미지
    Bitmap time60;
    Bitmap time120;

    int time30_x, time30_y;
    int time60_x, time60_y;
    int time120_x, time120_y;

    Bitmap level1; //난이도 쉬움
    Bitmap level2; //난이도 중간
    Bitmap level3; //난이도 어려움

    int level1_x, level1_y;
    int level2_x, level2_y;
    int level3_x, level3_y;

    Bitmap scoreImage; //점수 30점
    int scoreImage_x, scoreImage_y;
    int score_count; //이 값만큼 점수가 화면에 남아 있는다
    int scoreImageOk = 0;
    int count = 3000;

    //alpha 값 처리를 위한 변수. 하나가 눌려지면 다른 것들은 흐리게 나오도록 함.
    int level = 1; //값이 0이면 쉬움. 1이면 중간, 2이면 어려움
    int timeValue = 1; //값이 0이면 30초, 1이면 1분, 2이면 2분
    int operator; //operator가 0이면 덧셈문제, 1이면 뺄셈문제, 2이면 곱셈문제

    ArrayList<Balloon> balloon; //Balloon 클래스를 자료형으로 하는 ArrayList 객체선언
    Bitmap menuTitle; //메뉴 타이틀

    int number1, number2; //덧셈에 사용될 숫자
    int answer; //정답
    int[] wrongNumber = new int[5]; //오답 숫자 5개를 담을 배열

    SoundPool sPool;
    int dingdongdaeng, taeng;

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mThread = new MyThread(holder, context);
        InitApp();
        setFocusable(true);
    }

    private void InitApp() {
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();

        basket_speed = Width / 40;
        balloon_speed = Width / 140;

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
        basket_y = Height * 6 / 9;

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

        menuTitle = BitmapFactory.decodeResource(getResources(), R.drawable.menutitle);
        menuTitle = Bitmap.createScaledBitmap(menuTitle, Width * 3 / 5, Height / 7, true);

        Random r1 = new Random();
        int xx = r1.nextInt(Width);
        answerBalloon = new AnswerBalloon(x, 0, balloon_speed);

        menuButton = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
        menuButton = Bitmap.createScaledBitmap(menuButton, button_width, button_width, true);
        menuButton_x = Width - button_width * 2 + button_width / 2;
        menuButton_y = Height / 30;

        //도움
        helpButton = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
        helpButton = Bitmap.createScaledBitmap(helpButton, button_width, button_width, true);
        helpButton_x = Width - button_width - button_width / 4;
        helpButton_y = Height / 8;

        //덧셈, 뺄셈, 곱셈 문제 아이콘 만들기
        plusicon = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
        plusicon = Bitmap.createScaledBitmap(plusicon, button_width, button_width, true);
        plusicon_x = button_width;
        plusicon_y = Height / 5;

        minusicon = BitmapFactory.decodeResource(getResources(), R.drawable.minus);
        minusicon = Bitmap.createScaledBitmap(minusicon, button_width, button_width, true);
        minusicon_x = button_width * 2 + button_width / 4;
        minusicon_y = Height / 5;

        multiicon = BitmapFactory.decodeResource(getResources(), R.drawable.multiply);
        multiicon = Bitmap.createScaledBitmap(multiicon, button_width, button_width, true);
        multiicon_x = button_width * 3 + button_width / 2;
        multiicon_y = Height / 5;

        //난이도 조절 버튼
        level1 = BitmapFactory.decodeResource(getResources(), R.drawable.easy);
        level1 = Bitmap.createScaledBitmap(level1, button_width, button_width, true);
        level1_x = plusicon_x;
        level1_y = plusicon_y + button_width * 2;

        level2 = BitmapFactory.decodeResource(getResources(), R.drawable.normal);
        level2 = Bitmap.createScaledBitmap(level2, button_width, button_width, true);
        level2_x = minusicon_x;
        level2_y = plusicon_y + button_width * 2;

        level3 = BitmapFactory.decodeResource(getResources(), R.drawable.difficult);
        level3 = Bitmap.createScaledBitmap(level3, button_width, button_width, true);
        level3_x = multiicon_x;
        level3_y = plusicon_y + button_width * 2;

        time30 = BitmapFactory.decodeResource(getResources(), R.drawable.time30);
        time30 = Bitmap.createScaledBitmap(time30, button_width, button_width, true);
        time30_x = plusicon_x;
        time30_y = plusicon_y + button_width * 4;

        time60 = BitmapFactory.decodeResource(getResources(), R.drawable.time60);
        time60 = Bitmap.createScaledBitmap(time60, button_width, button_width, true);
        time60_x = minusicon_x;
        time60_y = plusicon_y + button_width * 4;

        time120 = BitmapFactory.decodeResource(getResources(), R.drawable.time120);
        time120 = Bitmap.createScaledBitmap(time120, button_width, button_width, true);
        time120_x = multiicon_x;
        time120_y = plusicon_y + button_width * 4;

        applyButton = BitmapFactory.decodeResource(getResources(), R.drawable.applybutton);
        applyButton = Bitmap.createScaledBitmap(applyButton, button_width * 4 / 3, button_width * 3 / 4, true);
        applyButton_x = Width / 4 - button_width / 2;
        applyButton_y = plusicon_y + button_width * 6 + button_width / 2;

        closeButton = BitmapFactory.decodeResource(getResources(), R.drawable.closebutton);
        closeButton = Bitmap.createScaledBitmap(closeButton, button_width * 4 / 3, button_width * 3 / 4, true);
        closeButton_x = Width * 2 / 3 - button_width / 2;
        closeButton_y = plusicon_y + button_width * 6 + button_width / 2;

        scoreImage = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
        scoreImage = Bitmap.createScaledBitmap(scoreImage, button_width, button_width, true);

        resultShow = BitmapFactory.decodeResource(getResources(), R.drawable.spongebob_rainbow);
        resultShow = Bitmap.createScaledBitmap(resultShow, Width * 2 / 3, Height * 1 / 3, true);
        resultShow_x = button_width;
        resultShow_y = button_width / 2;

        sPool = new SoundPool((10), AudioManager.STREAM_MUSIC, 0);
        //dingdongdaeng = sPool.load(mContext, R.raw.dingdongdaeng, 1);
        //taeng = sPool.load(mContext, R.raw.taeng, 2);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

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
            canvas.drawText("남은 시간 : " + Integer.toString(count / 50), 0, Height / 7, p4);
            canvas.drawText("점수 : " + Integer.toString(score), 0, Height / 5, p4);

            if (operator == 0) answer = number1 + number2;
            else if (operator == 1) answer = number1 - number2;
            else answer = number1 * number2;

            if (operator == 0)
                canvas.drawText("문제 : " + Integer.toString(number1) + "+" + Integer.toString(number2), 0, Height / 13, p3);
            else if (operator == 1)
                canvas.drawText("문제 : " + Integer.toString(number1) + "-" + Integer.toString(number2), 0, Height / 13, p3);
            else
                canvas.drawText("문제 : " + Integer.toString(number1) + "×" + Integer.toString(number2), 0, Height / 13, p3);

            //바구니가 화면을 거의 벗어 났을 경우 처리
            if (basket_x < -basketWidth + basketWidth / 8)
                basket_x = -basketWidth + basketWidth / 8;

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

            if (menuOk == 0)
                moveBalloon(); //풍선 움직이기

            //풍선과 바구니가 맞닿았는지 체크하기
            if (menuOk == 0)
                checkCollision();

            if (menuOk == 0)
                count--;

            //시간이 0초 남게 되면 결과창이 나오도록 하기
            if (count < 0) {
                menuOk = 3;
                count = timeValue * 1500 + 1500;
                if (timeValue == 2) count = 6000;
            }

            if (menuOk == 0)
                canvas.drawBitmap(menuButton, menuButton_x, menuButton_y, p1);

            //환경설정(메뉴) - 문제유형 난이도, 시간 선택하기
            if (menuOk == 1) {
                canvas.drawRect(0, 0, Width, Height, pp);
                canvas.drawBitmap(menuTitle, 0, 0, p1);
                canvas.drawText("* 문제유형 선택", button_width, plusicon_y - button_width / 4, p4);

                canvas.drawBitmap(helpButton, helpButton_x, helpButton_y, p1);

                if (operator == 0) {
                    canvas.drawBitmap(plusicon, plusicon_x, plusicon_y, p1);
                    canvas.drawBitmap(minusicon, minusicon_x, minusicon_y, p2);
                    canvas.drawBitmap(multiicon, multiicon_x, multiicon_y, p2);
                }
                if (operator == 1) {
                    canvas.drawBitmap(plusicon, plusicon_x, plusicon_y, p2);
                    canvas.drawBitmap(minusicon, minusicon_x, minusicon_y, p1);
                    canvas.drawBitmap(multiicon, multiicon_x, multiicon_y, p2);
                }
                if (operator == 2) {
                    canvas.drawBitmap(plusicon, plusicon_x, plusicon_y, p2);
                    canvas.drawBitmap(minusicon, minusicon_x, minusicon_y, p2);
                    canvas.drawBitmap(multiicon, multiicon_x, multiicon_y, p1);
                }

                canvas.drawText("난이도 선택", button_width, level1_y - button_width / 4, p4);

                if (level == 0) {
                    canvas.drawBitmap(level1, level1_x, level1_y, p1);
                    canvas.drawBitmap(level2, level2_x, level2_y, p2);
                    canvas.drawBitmap(level3, level3_x, level3_y, p2);
                }
                if (level == 1) {
                    canvas.drawBitmap(level1, level1_x, level1_y, p2);
                    canvas.drawBitmap(level2, level2_x, level2_y, p1);
                    canvas.drawBitmap(level3, level3_x, level3_y, p2);
                }
                if (level == 2) {
                    canvas.drawBitmap(level1, level1_x, level1_y, p2);
                    canvas.drawBitmap(level2, level2_x, level2_y, p2);
                    canvas.drawBitmap(level3, level3_x, level3_y, p1);
                }

                if (timeValue == 0) {
                    canvas.drawBitmap(time30, time30_x, time30_y, p1);
                    canvas.drawBitmap(time60, time60_x, time60_y, p2);
                    canvas.drawBitmap(time120, time120_x, time120_y, p2);
                }
                if (timeValue == 1) {
                    canvas.drawBitmap(time30, time30_x, time30_y, p2);
                    canvas.drawBitmap(time60, time60_x, time60_y, p1);
                    canvas.drawBitmap(time120, time120_x, time120_y, p2);
                }
                if (timeValue == 2) {
                    canvas.drawBitmap(time30, time30_x, time30_y, p2);
                    canvas.drawBitmap(time60, time60_x, time60_y, p2);
                    canvas.drawBitmap(time120, time120_x, time120_y, p1);
                }

                canvas.drawBitmap(applyButton, applyButton_x, applyButton_y, p1);
                canvas.drawBitmap(closeButton, closeButton_x, closeButton_y, p1);
            }

            //도움말 나오기
            if (menuOk == 2) {
                canvas.drawRect(0, 0, Width, Height, pp);
                canvas.drawText("스폰지밥을 움직여서", Width / 20, Height / 20, p3);
                canvas.drawText("정답 게살버거를 받는 게임", Width / 20, Height / 9, p3);
                canvas.drawText("만든이 : 서채연", Width / 20, Height / 4, p4);
                canvas.drawBitmap(balloonimg, Width / 2, Height / 2, p1);
                canvas.drawBitmap(basket, Width / 3, Height * 3 / 4, p1);
                canvas.drawBitmap(closeButton, closeButton_x - button_width, closeButton_y + button_width * 1 / 3, p1);
            }

            //정답 풍선을 받았을 경우 30점 표시하기
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

            if (operator == 0) answer = number1 + number2;

            if (operator == 1)
                if (number1 < number2) {
                    int tmp = number1;
                    number1 = number2;
                    number2 = tmp;
                }
            answer = number1 - number2; //여기 위치 맞겠지?

            if (operator == 2) {
                r1 = new Random();
                x = r1.nextInt(8) + 1; //두자리 수 곱하기 한 자리 수 하려고!
                number2 = x;
                answer = number1 * number2;
            }

            //오답 풍선에 들어갈 숫자
            if (operator == 2) //곱셈문제일 경우
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
                        balloon.get(i).y + balloonHeight > basket_y + basketWidth) {
                    balloon.remove(i);
                    score -= 10;
                    xNumber += 1;
                    sPool.play(taeng, 1, 1, 9, 0, 1);
                }
            }
            //바구니와 정답 풍선이 접촉했는지 체크
            if (answerBalloon.x + balloonWidth / 2 > basket_x &&
                    answerBalloon.x + balloonWidth / 2 < basket_x + balloonWidth &&
                    answerBalloon.y + balloonHeight > basket_y &&
                    answerBalloon.y + balloonHeight > basket_y + basketWidth) {
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
                scoreImage_y = basket_y = button_width / 2;
                sPool.play(dingdongdaeng, 1, 1, 9, 0, 1);
            }
        }

        public void run() {
            Canvas canvas = null;
            while (true) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        drawEverything(canvas);
                    } //sync
                } finally {
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

        //환경설정 활성화 : 덧셈, 뺄셈, 곱셈 문제 선택
        if (menuOk == 1) {
            if ((x > plusicon_x) && (x < plusicon_x + button_width) && (y > plusicon_y) && (y < plusicon_y + button_width)) {
                operator = 0;
                mThread.makeQuestion();
            }
            if ((x > minusicon_x) && (x < minusicon_x + button_width) && (y > minusicon_y) && (y < minusicon_y + button_width)) {
                operator = 1;
                mThread.makeQuestion();
            }
            if ((x > multiicon_x) && (x < multiicon_x + button_width) && (y > multiicon_y) && (y < multiicon_y + button_width)) {
                operator = 1;
                mThread.makeQuestion();
            }
        }

        //환경설정 활성화 : 시간 선택
        if (menuOk == 1) {
            if ((x > time30_x) && (x < time30_x + button_width) && (y > time30_y) && (y < time30_y + button_width)) {
                //gHandler.sendEmptyMessageDelayed(0,20); 에서 준 값 20에 50을 곱하면 대략 1초가 된다.
                count = 50 * 30;
                timeValue = 0;
            }
            if ((x > time60_x) && (x < time60_x + button_width) && (y > time60_y) && (y < time60_y + button_width)) {
                //gHandler.sendEmptyMessageDelayed(0,20); 에서 준 값 20에 50을 곱하면 대략 1초가 된다.
                count = 50 * 30;
                timeValue = 1;
            }
            if ((x > time120_x) && (x < time120_x + button_width) && (y > time120_y) && (y < time120_y + button_width)) {
                //gHandler.sendEmptyMessageDelayed(0,20); 에서 준 값 20에 50을 곱하면 대략 1초가 된다.
                count = 50 * 30;
                timeValue = 2;
            }
        }

        //환경설정 활성화 : 난이도 선택
        if (menuOk == 1) {
            if ((x > level1_x) && (x < level1_x + button_width) && (y > level1_y) && (y < level1_y + button_width)) {
                balloon_speed = Width / 200;
                level = 0;
                oNumber = 0;
                xNumber = 0;
                mThread.makeQuestion();
            }
            if ((x > level2_x) && (x < level2_x + button_width) && (y > level2_y) && (y < level2_y + button_width)) {
                balloon_speed = Width / 140;
                level = 1;
                oNumber = 0;
                xNumber = 0;
                mThread.makeQuestion();
            }
            if ((x > level3_x) && (x < level3_x + button_width) && (y > level3_y) && (y < level3_y + button_width)) {
                balloon_speed = Width / 70;
                level = 2;
                oNumber = 0;
                xNumber = 0;
                mThread.makeQuestion();
            }
        }

        //환경설정 활성화 : 적용, 닫기 버튼
        if (menuOk == 1) {
            if ((x > applyButton_x) && (x < applyButton_x + button_width) && (y > applyButton_y) && (y < applyButton_y + button_width)) {
                count = timeValue * 1500 + 1500;
                if (timeValue == 2) count = 6000;
                menuOk = 0;

                oNumber = 0;
                xNumber = 0;
            }
            if ((x > closeButton_x) && (x < closeButton_x + button_width) && (y > closeButton_y) && (y < closeButton_y + button_width)) {
                oNumber = 0;
                xNumber = 0;
                System.exit(0);
            }
        }

        //환경설정 활성화 : 도움말버튼
        if (menuOk == 1) {
            if ((x > helpButton_x) && (x < helpButton_x + button_width) && (y > helpButton_y) && (y < helpButton_y + button_width)) {
                menuOk = 2;
            }
        }

        //환경설정 비활성화, 즉 게임이 이루어지고 있다
        if (menuOk == 0) {
            if ((x > menuButton_x) && (x < menuButton_x + button_width) && (y > menuButton_y) && (y < menuButton_y + button_width)) {
                menuOk = 1;
            }
        }

        //도움말 : 닫기 버튼, 또는 결과보기 창에서 나오는 닫기 버튼
        if (menuOk == 2 || menuOk == 3) {
            if ((x > closeButton_x - button_width) && (x < closeButton_x) && (y > closeButton_y + button_width) && (y < closeButton_y + button_width + button_width)) {
                if (menuOk == 3) {
                    oNumber = 0;
                    xNumber = 0;
                }
                menuOk = 1;
            }
        }

        return returnValue;
        //end of touchEvent
    }


    //-------------------------------------------------------------
    // onKeyDown
    //-------------------------------------------------------------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        synchronized (mHolder) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    break;

                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;

                case KeyEvent.KEYCODE_DPAD_UP:
                    break;

                default:
            }
        }
        return false;
    }
} //SurfaceView
