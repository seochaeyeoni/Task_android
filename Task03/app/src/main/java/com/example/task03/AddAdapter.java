package com.example.task03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.AddViewHolders> {


    private ArrayList<Dict> mList;

    public AddAdapter(ArrayList<Dict> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public AddAdapter.AddViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_box, parent, false);
        AddViewHolders holders = new AddViewHolders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddAdapter.AddViewHolders holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.grade.setText(mList.get(position).getGrade());
        holder.memo.setText(mList.get(position).getMemo());




        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.name.getText().toString();
                Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public void remove(int position) {
        try {
            mList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class AddViewHolders extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView grade;
        protected TextView memo;

        public AddViewHolders(@NonNull View itemView) {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.name_add);
            this.grade = (TextView) itemView.findViewById(R.id.grade_add);
            this.memo = (TextView) itemView.findViewById(R.id.memo_add);
        }
    }
}
