package com.example.task05.DailyBoxOffice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.task05.GlobalApplication;
import com.example.task05.MainActivity;
import com.example.task05.MovieDetail;
import com.example.task05.MovieInfo.MovieInfoResult;
import com.example.task05.MovieInfo.MovieInfoService;
import com.example.task05.MovieInfo.Result;
import com.example.task05.NaverMovie.Item;
import com.example.task05.NaverMovie.Movie;
import com.example.task05.NaverMovie.MovieAdapter;
import com.example.task05.NaverMovie.NaverMovieService;
import com.example.task05.NaverMovie.ServiceGenerator;
import com.example.task05.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.BoxOfficeViewHolder> {
    List<DailyBoxOfficeList> dailyBoxOfficeLists = new ArrayList<>();
    List<String> movieInfoList = new ArrayList<>();
    Context context;
    MovieInfoService movieInfoService;
    Retrofit retrofit;
    MovieAdapter mMovieAdapter;
    final String BASE_URL = "http://www.kobis.or.kr";
    String API_KEY = "764cfadaa2fbc3be977d0390f58dc668";

    List<String> posterList = new ArrayList<>();



    public BoxOfficeAdapter(List<DailyBoxOfficeList> dailyBoxOfficeLists, List<String> movieInfoList, List<String> posterList, Context context) {
        this.dailyBoxOfficeLists = dailyBoxOfficeLists;
        this.movieInfoList = movieInfoList;
        this.posterList = posterList;
        this.context = context;
    }


    @NonNull
    @Override
    public BoxOfficeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.boxoffice_item, viewGroup, false);
        BoxOfficeViewHolder boxOfficeViewHolder = new BoxOfficeViewHolder(rootView);

        //Retrofit 객체생성
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return boxOfficeViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final BoxOfficeViewHolder boxOfficeViewHolder, final int i) {
        final DailyBoxOfficeList dailyBoxOfficeList = dailyBoxOfficeLists.get(i);
        boxOfficeViewHolder.rankTextView.setText(dailyBoxOfficeList.getRank()+". ");
        boxOfficeViewHolder.movieNameTextView.setText(dailyBoxOfficeList.getMovieNm());

        //Glide.with(context).load(posterList.get(i)).into(boxOfficeViewHolder.posterImageview);

        //boxOfficeViewHolder.count2TextView.setText(dailyBoxOfficeList.getAudiAcc()); //누적관객수

        //String movieName = dailyBoxOfficeList.getMovieNm();
        //getMovies(movieName); //영화를 검색해서 가장 처음에 나오는 아이템의 이미지를 가져오고 싶은데...
        GlobalApplication.movieCd = dailyBoxOfficeList.getMovieCd();

        //영화 상세정보
        movieInfoService = retrofit.create(MovieInfoService.class);
        movieInfoService.getMovieInfo(API_KEY, GlobalApplication.movieCd).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d("movieCd", GlobalApplication.movieCd + "");
                    final com.example.task05.MovieInfo.Result result = response.body();

                    movieInfoList.clear();
                    MovieInfoResult movieInfoResult = result.getMovieInfoResult();
                    movieInfoList.add(movieInfoResult.getMovieInfo().getMovieCd());
                    movieInfoList.add(movieInfoResult.getMovieInfo().getMovieNm());
                    movieInfoList.add(movieInfoResult.getMovieInfo().getShowTm());
                    movieInfoList.add(movieInfoResult.getMovieInfo().getNations().get(0).getNationNm());
                    movieInfoList.add(movieInfoResult.getMovieInfo().getGenres().get(0).getGenreNm());
//                                        movieInfoList.add(movieInfoResult.getMovieInfo().getActors().get(0).getPeopleNm());
//                                        movieInfoList.add(movieInfoResult.getMovieInfo().getActors().get(0).getCast());
                    movieInfoList.add(movieInfoResult.getMovieInfo().getAudits().get(0).getWatchGradeNm());

                    //화면에 표시하기
                    boxOfficeViewHolder.showTmTextview.setText(movieInfoList.get(2)+"분 | ");
                    boxOfficeViewHolder.watchGradeNmTextview.setText(movieInfoList.get(5)+" | ");
                    boxOfficeViewHolder.genreNmTextview.setText(movieInfoList.get(4));


                    boxOfficeViewHolder.detailBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (i != RecyclerView.NO_POSITION) {
                                MovieInfoResult movieInfoResult = result.getMovieInfoResult();
                                Intent intent = new Intent(v.getContext(), MovieDetail.class);
                                intent.putExtra("movie_name", movieInfoResult.getMovieInfo().getMovieNm());
                                //intent.putExtra("rating", Float.parseFloat(item.getUserRating()) / 2);
                                intent.putExtra("pub_date", movieInfoResult.getMovieInfo().getOpenDt());
                                intent.putExtra("director", movieInfoResult.getMovieInfo().getDirectors().get(0).getPeopleNm());
                                //intent.putExtra("actor", (item.getActor()));
                                //intent.putExtra("poster", posterList.get(i)); //poster
                                intent.putExtra("showTm",movieInfoResult.getMovieInfo().getShowTm());
                                intent.putExtra("genre",movieInfoResult.getMovieInfo().getGenres().get(0).getGenreNm());
                                intent.putExtra("audiAcc",dailyBoxOfficeList.getAudiAcc());
                                //intent.putExtra("actor", movieInfoResult.getMovieInfo().getActors()); //java.lang.string[]?

                                context.startActivity(intent);
                            }
                        }
                    });

                    //boxOfficeAdapter = new BoxOfficeAdapter(dailyBoxOfficeLists, movieInfoLists, posterList,MainActivity.this);
                    //boxoffice_recycler.setAdapter(boxOfficeAdapter);
                    Log.d("list", movieInfoList.get(0) + "0");
                    Log.d("list", movieInfoList.get(1) + "1");
                    Log.d("list", movieInfoList.get(2) + "2");
                    Log.d("list", movieInfoList.get(3) + "3");
                    Log.d("list", movieInfoList.get(4) + "4");
                    Log.d("list", movieInfoList.get(5) + "5");
//                                        Log.d("list", movieInfoList.get(6) + "6");
//                                        Log.d("list", movieInfoList.get(7) + "7");

                } else {
                }

            }

            @Override
            public void onFailure(Call<com.example.task05.MovieInfo.Result> call, Throwable t) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return dailyBoxOfficeLists.size();
    }

    public class BoxOfficeViewHolder extends RecyclerView.ViewHolder {
        TextView rankTextView;
        TextView movieNameTextView;
        TextView showTmTextview;
        TextView watchGradeNmTextview;
        TextView genreNmTextview;
        ImageView posterImageview;
        Button detailBtn;
        //TextView count1TextView;
        //TextView count2TextView;


        public BoxOfficeViewHolder(@NonNull View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rank);
            movieNameTextView = itemView.findViewById(R.id.movie_name);
            showTmTextview = itemView.findViewById(R.id.showTm);
            watchGradeNmTextview = itemView.findViewById(R.id.watchGradeNm);
            genreNmTextview = itemView.findViewById(R.id.genreNm);
            posterImageview = itemView.findViewById(R.id.poster);
            detailBtn = itemView.findViewById(R.id.btn_detail);
            //count1TextView = itemView.findViewById(R.id.count1);
            //count2TextView = itemView.findViewById(R.id.count2);
        }
    }

    // 영화 가져오기
    public void getMovies(final String title) {
        NaverMovieService apiInterface = ServiceGenerator.createService(NaverMovieService.class);
        Call<Movie> call = apiInterface.getMovies(title, 100, 1);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    ArrayList<Item> movies = new ArrayList(response.body().getItems());
                    if (movies.size() == 0) {
                        mMovieAdapter.clearItems();
                    } else {
                        mMovieAdapter.clearAndAddItems(movies);
                    }
                }else{
                    //Log.e(TAG, response.message());
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                //Log.e(TAG, t.getMessage());
            }
        });
    }
}
