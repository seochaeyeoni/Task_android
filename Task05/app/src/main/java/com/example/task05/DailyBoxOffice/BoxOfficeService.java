package com.example.task05.DailyBoxOffice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoxOfficeService {
    //http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?")
    Call<Result> getBoxOffice(@Query("key") String key,
                              @Query("targetDt") String targetDt);
}
