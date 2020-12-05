package com.example.task03;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class Frag2 extends Fragment {

    //로그 필터 변수
    public static final String TAG = "Alert_Dialog";

    View view;
    ListView listView;
    MyAdapter myAdapter;
    ArrayList<ListViewItem> DataList;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        DataList = new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            DataList.add(new ListViewItem(R.drawable.girlsimple, "유라79551", "문과 고2", "안녕하세요~ 앱알림이 늦게 떠서 못봤었네요"));
            DataList.add(new ListViewItem(R.drawable.boysimple, "타시기59901", "학부오", "안녕하세요~ 현재 수능때까지 과외 학생을 받고 있지 않습니다"));
            DataList.add(new ListViewItem(R.drawable.girlsimple, "고오스82778", "학부모", "네~ 지금은 과외하는 학생들이 많아서, 단기과외만 하고 있습니다"));
        }



        listView = view.findViewById(R.id.listview_message);
        myAdapter = new MyAdapter(getContext(), DataList);
        listView.setAdapter(myAdapter);



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Toast.makeText(getActivity(),myAdapter.getItem(position).getName(), Toast.LENGTH_LONG).show();
//                DataList.remove(position);
//                myAdapter.notifyDataSetChanged();
//            }
//        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //PopupMenu 객체 생성
                PopupMenu popup = new PopupMenu(getActivity(), view);//view는 오래 눌러진 뷰를 의미
                //PopupMenu에 들어갈 Menuitem 추가
                getActivity().getMenuInflater().inflate(R.menu.menu_listview, popup.getMenu());

                //Popup menu의 메뉴아이템을 눌렀을 때 보여질 ListView 항목의 위치
                //Listener 안에서 사용해야 하기에 final 선언
                final int index = position;

                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 설정
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //선택된 Popup Menu의 아이템아이디를 구별하여 원하는 작업 수행

                        switch (item.getItemId()) {
                            case R.id.modify_name:
                                //Toast.makeText(MainActivity.this, DataList.get(index)+" Modify", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                ad.setTitle("이름 수정");
                                ad.setMessage("변경할 이름을 입력하세요");

                                //EditText 삽입하기
                                final EditText et = new EditText(getActivity());
                                ad.setView(et);

                                //확인 버튼 설정
                                ad.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.v(TAG, "이름이 수정되었습니다");

                                        //Text 값 받아서 로그 남기기
                                        String value = et.getText().toString();
                                        Log.v(TAG, value);

                                        //(추가) 받은 Text 값으로 이름 수정하기
                                        DataList.get(position).setName(value);
                                        myAdapter.notifyDataSetChanged();

                                        dialog.dismiss(); //닫기
                                    }
                                });

                                //취소 버튼 설정
                                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.v(TAG, "취소되었습니다");
                                        dialog.dismiss();
                                    }
                                });

                                ad.show(); //창 띄우기
                                break;
                            case R.id.delete_message:
                                //Toast.makeText(MainActivity.this, DataList.get(index)+" Delete", Toast.LENGTH_SHORT).show();
                                DataList.remove(position);
                                myAdapter.notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();//Popup Menu 보이기
                return false;
            }
        });

        return view; //리턴뷰를 맨 마지막에 해줘야 빨간 줄이 안 뜨던데,, 이유?
    }


//    public void InitializeMessageData() {
//        DataList = new ArrayList<SampleData>();
//
//        for (int i = 0; i < 5; i++) {
//
//            DataList.add(new SampleData(R.drawable.girlsimple, "유라79551", "문과 고2", "안녕하세요~ 앱알림이 늦게 떠서 못봤었네요"));
//            DataList.add(new SampleData(R.drawable.boysimple, "타시기59901", "학부오", "안녕하세요~ 현재 수능때까지 과외 학생을 받고 있지 않습니다"));
//            DataList.add(new SampleData(R.drawable.girlsimple, "고오스82778", "학부모", "네~ 지금은 과외하는 학생들이 많아서, 단기과외만 하고 있습니다"));
//        }
//    }




}
