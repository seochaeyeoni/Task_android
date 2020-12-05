package com.example.listview_ex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SampleData> sample;

    public MyAdapter(Context context, ArrayList<SampleData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SampleData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.item_message, null);

        ImageView image = (ImageView) view.findViewById(R.id.image_message);
        TextView name = (TextView)view.findViewById(R.id.name_message);
        TextView grade = (TextView)view.findViewById(R.id.grade_message);
        TextView content = (TextView)view.findViewById(R.id.content_message);

        image.setImageResource(sample.get(position).getProfile());
        name.setText(sample.get(position).getName());
        grade.setText(sample.get(position).getGrade());
        content.setText(sample.get(position).getContent());

        return view;
    }
}