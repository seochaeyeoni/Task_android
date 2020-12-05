package com.example.task03;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.CustomViewHolders> {

    private ArrayList<ClassData> arrayList;

    Context context;
    ClassAdapter classAdapter;



    public ClassAdapter(ArrayList<ClassData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ClassAdapter.CustomViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        CustomViewHolders holders = new CustomViewHolders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassAdapter.CustomViewHolders holder, int position) {
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_grade.setText(arrayList.get(position).getTv_grade());
        holder.tv_memo.setText(arrayList.get(position).getTv_memo());



        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }


    public class CustomViewHolders extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView tv_name;
        protected TextView tv_grade;
        protected TextView tv_memo;


        public CustomViewHolders(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.profile_class);
            this.tv_name = (TextView) itemView.findViewById(R.id.name_class);
            this.tv_grade = (TextView) itemView.findViewById(R.id.grade_class);
            this.tv_memo = (TextView) itemView.findViewById(R.id.memo_class);

        }
    }
}
