package com.example.task03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ListViewItem> DataList;

    public MyAdapter(Context context, ArrayList<com.example.task03.ListViewItem> data) {
        mContext = context;
        DataList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public com.example.task03.ListViewItem getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_message, null);

       // Boolean btn = (Boolean) view.findViewById(R.id.alarm_message);
        ImageView image = (ImageView) view.findViewById(R.id.image_message);
        TextView name = (TextView)view.findViewById(R.id.name_message);
        TextView grade = (TextView)view.findViewById(R.id.grade_message);
        TextView content = (TextView)view.findViewById(R.id.content_message);

        ListViewItem listViewItem = DataList.get(position);

        image.setImageResource(listViewItem.getProfile());
        name.setText(listViewItem.getName());
        grade.setText(listViewItem.getGrade());
        content.setText(listViewItem.getContent());

        return view;
    }
}