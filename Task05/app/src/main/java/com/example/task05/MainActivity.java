package com.example.task05;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task05.DailyBoxOffice.BoxOfficeAdapter;
import com.example.task05.DailyBoxOffice.BoxOfficeResult;
import com.example.task05.DailyBoxOffice.BoxOfficeService;
import com.example.task05.DailyBoxOffice.DailyBoxOfficeList;
import com.example.task05.DailyBoxOffice.Result;
import com.example.task05.MovieInfo.MovieInfoService;
import com.example.task05.NaverMovie.SearchActicity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final String BASE_URL = "http://www.kobis.or.kr";
    String API_KEY = "764cfadaa2fbc3be977d0390f58dc668";

    Retrofit retrofit;
    BoxOfficeService boxOfficeService;
    MovieInfoService movieInfoService;
    RecyclerView boxoffice_recycler;

    List<DailyBoxOfficeList> dailyBoxOfficeLists = new ArrayList<>();
    List<String> movieInfoList = new ArrayList<>();
    List<String> posterList = new ArrayList<>();
    BoxOfficeAdapter boxOfficeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //-------------------------------------------------------------
        //메인화면의 리사이클러뷰 코드시작!
        //-------------------------------------------------------------
        boxoffice_recycler = findViewById(R.id.boxoffice_recycler);

        //PagerSnapHelper 추가
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(boxoffice_recycler);
        //Indicator 추가하기
        //boxoffice_recycler.addItemDecoration(new LinePagerIndicatorDecoration());

        SnapPagerScrollListener listener = new SnapPagerScrollListener(
                snapHelper,
                SnapPagerScrollListener.ON_SCROLL,
                true,
                new SnapPagerScrollListener.OnChangeListener() {
                    @Override
                    public void onSnapped(int position) {
                        //position 받아서 이벤트 처리
                    }
                }

        );
        boxoffice_recycler.addOnScrollListener(listener);

        //Retrofit 객체생성
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    /*addConverterFactory(GsonConverterFactory.create())은
    Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        //일별 박스오피스
        boxOfficeService = retrofit.create(BoxOfficeService.class);
        boxOfficeService.getBoxOffice(API_KEY, "20201023").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d("retro", 1 + "");
                    Result result = response.body(); //이 부분을 다시 입력하니까 null 오류가 더이상 안뜸
                    BoxOfficeResult boxOfficeResult = result.getBoxOfficeResult();
                    List<DailyBoxOfficeList> dailyBoxOfficeListList = boxOfficeResult.getDailyBoxOfficeList();
                    if (dailyBoxOfficeListList == null) {
                        Log.d("null", 1 + "");
                        if (posterList == null) {
                            //dummy image url
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=187893");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=183866");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=190010");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=189141");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=196828");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=167421");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=195430");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=174082");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=195983");
                            posterList.add("https://movie.naver.com/movie/bi/mi/photoViewPopup.nhn?movieCode=175393");
                        }
                    }
                    try {
                        for (DailyBoxOfficeList dailyBoxOffice : dailyBoxOfficeListList) {
                            dailyBoxOfficeLists.add(dailyBoxOffice);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    boxOfficeAdapter = new BoxOfficeAdapter(dailyBoxOfficeLists, movieInfoList, posterList,MainActivity.this);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
                    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    boxoffice_recycler.setLayoutManager(mLayoutManager);
                    boxoffice_recycler.setAdapter(boxOfficeAdapter);
                } else {
                    Log.d("retro", 2 + "Error");
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });



        for (int i=0; i<10; i++) {


        }


        //-------------------------------------------------------------
        //메인화면의 네비게이션 드로어 코드시작!
        //-------------------------------------------------------------

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent intent = new Intent(getApplicationContext(), SearchActicity.class);
            startActivity(intent);


        } else if ( (id == R.id.nav_gallery)) {

        } else if ( (id == R.id.nav_slideshow)) {

        } else if ( (id == R.id.nav_manage)) {

        } else if ( (id == R.id.nav_share)) {

        } else if ( (id == R.id.nav_send)) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


//뷰페이저에 프래그먼트 여러개 담아서 쓰려고 할 떄
//onCreate 안에서 했음
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setOffscreenPageLimit(3); //뒤에 캐싱하는 게 세 개로 늘어남
//
//        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());
//
//        MainFragment1 fragment1 = new MainFragment1();
//        adapter.addItem(fragment1);
//
//        MainFragment1 fragment2 = new MainFragment1();
//        adapter.addItem(fragment2);
//
//        pager.setAdapter(adapter);


//    class MoviePagerAdapter extends FragmentStatePagerAdapter {
//        ArrayList<Fragment> items = new ArrayList<Fragment>();
//
//        public MoviePagerAdapter(@NonNull FragmentManager fm) {
//            super(fm);
//        }
//
//        public void addItem(Fragment item) {
//            items.add(item);
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//    }
