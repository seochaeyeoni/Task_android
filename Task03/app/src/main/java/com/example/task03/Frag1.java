package com.example.task03;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class Frag1 extends Fragment {

    private ArrayList<StudentData> arrayList;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SearchView searchView;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true); //일정한 크기 사용
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        gridLayoutManager = new GridLayoutManager(getContext(),2); //LinearLayoutManager 는 리스트뷰(가로/세로)
        recyclerView.setLayoutManager(gridLayoutManager);

        arrayList = new ArrayList<>();

        studentAdapter = new StudentAdapter(arrayList);
        recyclerView.setAdapter(studentAdapter);

        for (int i=0;i<10;i++)
        {
            arrayList.add(new StudentData(R.drawable.girlsimple,"아라리30774", "고2 문과 여자", "수학, 영어", "서울 성동구"));
            arrayList.add(new StudentData(R.drawable.girlsimple,"죠즈94646", "고2 이과 여자", "수학, 영어", "서울 성동구"));
            arrayList.add(new StudentData(R.drawable.boysimple,"서성한 탈출 기원", "n수생 문과 남자", "수학, 미대입시", "서울 성동구"));
            arrayList.add(new StudentData(R.drawable.girlsimple,"라이즈38315", "중2 중학생 여자", "수학, 중등수학", "서울 성동구"));
            arrayList.add(new StudentData(R.drawable.boysimple,"이효리51123", "고1 이과 남자", "수학, 과학", "서울 성동구"));
            arrayList.add(new StudentData(R.drawable.girlsimple,"지은 시안", "초1 초등학생 여자", "국어, 수학", "서울 성동구"));

        }



        searchView = (SearchView) view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                studentAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return view;
    }


}
