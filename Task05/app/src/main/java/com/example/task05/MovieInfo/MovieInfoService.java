package com.example.task05.MovieInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInfoService {
    //http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.xml
    @GET("/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?")
    Call<Result> getMovieInfo(@Query("key") String key,
                              @Query("movieCd") String movieCd);

}
