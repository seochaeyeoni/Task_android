package com.example.task05.NaverMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NaverMovieService {
    @Headers({"X-Naver-Client-Id:aVHaamAG7r8w4sDyNyRB", "X-Naver-Client-Secret:Xzkz09FkJ7"})
    @GET("movie.json")
    Call<Movie> getMovies(@Query("query") String title,
                          @Query("display") int displaySize,
                          @Query("start") int startPosition);

}
