package com.example.task05.NaverMovie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task05.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRvMovies;
    private RecyclerView.LayoutManager mLayoutManager;

    private MovieAdapter mMovieAdapter;

    private EditText mEtKeyword;
    private Button mBtnSearch;

    private InputMethodManager mInputMethodManager;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_movie, container,
                false);
        setupRecyclerView(root);
        setupSearchView(root);
        return root;
    }

    private void setupRecyclerView(View view) {
        mRvMovies = view.findViewById(R.id.rv_movies);
        mRvMovies.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvMovies.setLayoutManager(mLayoutManager);

        // 어댑터 설정
        ArrayList<Item> movies = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(getContext(), movies);
        mRvMovies.setAdapter(mMovieAdapter);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        mRvMovies.addItemDecoration(dividerItemDecoration);
    }

    private void setupSearchView(View view) {
        mEtKeyword = view.findViewById(R.id.et_keyword);
        mBtnSearch = view.findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);
        mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                hideKeyboard();
                startSearch(mEtKeyword.getText().toString());
                break;
        }
    }

    public void hideKeyboard() {
        mInputMethodManager.hideSoftInputFromWindow(mRvMovies.getWindowToken(), 0);
    }

    public void showEmptyFieldMessage() {
        Toast.makeText(getContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
    }

    public void showNotFoundMessage(String keyword) {
        Toast.makeText(getContext(), "\'" + keyword + "\' 를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
    }

    // 검색어가 입력되었는지 확인 후 영화 가져오기
    public void startSearch(String title) {
        if (title.isEmpty()) {
            showEmptyFieldMessage();
        } else {
            mLayoutManager.scrollToPosition(0);
            getMovies(title);
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
                        showNotFoundMessage(title);
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
