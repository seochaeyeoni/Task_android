package com.example.task05;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.task05.NaverMovie.Item;
import com.example.task05.NaverMovie.MovieAdapter;

import java.util.ArrayList;



public class MovieDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);

        //인텐트 값 가져오기
        Intent intent = getIntent();
        String movie_name = getIntent().getStringExtra("movie_name");
        float rating = getIntent().getFloatExtra("rating",0);
        String pub_date = getIntent().getStringExtra("pub_date");
        String director = getIntent().getStringExtra("director");
        String actor = getIntent().getStringExtra("actor");
        String poster = getIntent().getStringExtra("poster");
        String showTm = getIntent().getStringExtra("showTm");
        String genre = getIntent().getStringExtra("genre");
        String audiAcc = getIntent().getStringExtra("audiAcc");
        String actorList = getIntent().getStringExtra("actor");


        //findViewById
        TextView movie_name_d = (TextView) findViewById(R.id.moviedetail_title);
        RatingBar rating_d = (RatingBar) findViewById(R.id.ratingBar);
        TextView score_d = (TextView) findViewById(R.id.moviedetail_score_num);
        TextView pub_date_d = (TextView) findViewById(R.id.moviedetail_date);
        TextView director_d = (TextView) findViewById(R.id.moviedetail_director_name);
        TextView actor_d = (TextView) findViewById(R.id.moviedetail_actor_name);
        ImageView poster_d = (ImageView) findViewById(R.id.moviedetail_profile);
        TextView showTm_d = (TextView) findViewById(R.id.moviedetail_showTm);
        TextView genre_d = (TextView) findViewById(R.id.moviedetail_genre);
        TextView audiAcc_d = (TextView) findViewById(R.id.moviedetail_spectators_num);
        //actorlist 해야함


        //화면에 값 넣어주기
        movie_name_d.setText(movie_name);
        rating_d.setRating(rating);
        String score = (String) (String.valueOf(rating*2)); //평점을 10점 만점으로
        score_d.setText(score);
        pub_date_d.setText(pub_date+" 개봉");
        director_d.setText(director);
        actor_d.setText(actor);
        Glide.with(this).load(poster).into(poster_d);
        showTm_d.setText(showTm);
        genre_d.setText(genre);
        audiAcc_d.setText(audiAcc);




    }
}
