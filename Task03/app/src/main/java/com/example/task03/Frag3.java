package com.example.task03;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class Frag3 extends Fragment {

    private ArrayList<ClassData> arrayList;
    private ClassAdapter classAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<Dict> mList;
    private AddAdapter addAdapter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(true); //일정한 크기 사용
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        //mList = new ArrayList<>();

        classAdapter = new ClassAdapter(arrayList);
        //addAdapter = new AddAdapter(mList);
        recyclerView.setAdapter(classAdapter);

        for (int i=0;i<10;i++)
        {
            arrayList.add(new ClassData(R.drawable.boysimple,"행주97833", "n수생 이과", "물리학과 자소서 첨삭/단기"));
            arrayList.add(new ClassData(R.drawable.girlsimple,"쏘오오오오", "고1 이과", "수학내신/수 3-5시/투썸"));
            arrayList.add(new ClassData(R.drawable.girlsimple,"구름겸둥이", "학부모", "수학수능/월,목 6-8시/더샾"));
        }


        ImageButton buttonAdd = (ImageButton)view.findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            //1.화면 위쪽에 있는 데이터 추가 버튼을 클릭하면
            @Override
            public void onClick(View v) {
                //2. 레이아웃 파일 add_box.xml을 불러와서 화면에 다이얼로그를 보여준다.
                Log.d("test", "lll");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.add_box, null, false);
                builder.setView(view);

                final  Button ButtonSubmit = (Button) view.findViewById(R.id.btn_submit);
                final  Button ButtonBack = (Button) view.findViewById(R.id.btn_back);
                final RadioButton Boy = (RadioButton) view.findViewById(R.id.boy);
                final RadioButton Girl = (RadioButton) view.findViewById(R.id.girl);
                final EditText addName = (EditText) view.findViewById(R.id.name_add);
                final EditText addGrade = (EditText) view.findViewById(R.id.grade_add);
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
                        String strGrade = addGrade.getText().toString();
                        String strMemo = addMemo.getText().toString();

                        //5. ArrayList에 추가하고
                        if (Boy.isChecked()) {
                            ClassData dict = new ClassData(R.drawable.boysimple, strName, strGrade, strMemo);
                            arrayList.add(0, dict); //첫번째 줄에 삽입됨, 0 안쓰면 마지막 줄에 삽입됨
                        }
                        if (Girl.isChecked()) {
                            ClassData dict = new ClassData(R.drawable.girlsimple, strName, strGrade, strMemo);
                            arrayList.add(0, dict); //첫번째 줄에 삽입됨, 0 안쓰면 마지막 줄에 삽입됨
                        }

                        //6. 어댑터에서 RecyclerView에 반영하도록 한다.
                        classAdapter.notifyDataSetChanged();

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
        });

        Button buttonEdit = (Button)view.findViewById(R.id.btn_edit);


        return view;
    }
}
