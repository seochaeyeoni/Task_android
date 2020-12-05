package com.example.task03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.CustomViewHolders> implements Filterable {

    private ArrayList<StudentData> arrayList;
    private ArrayList<StudentData> arrayListFull;

    public StudentAdapter(ArrayList<StudentData> arrayList) {

        this.arrayList = arrayList;
        arrayListFull = new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public StudentAdapter.CustomViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        CustomViewHolders holders = new CustomViewHolders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.CustomViewHolders holder, int position) {
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_grade.setText(arrayList.get(position).getTv_grade());
        holder.tv_subject.setText(arrayList.get(position).getTv_subject());
        holder.tv_place.setText(arrayList.get(position).getTv_place());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_name.getText().toString();
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
        protected TextView tv_subject;
        protected TextView tv_place;

        public CustomViewHolders(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.profile_student);
            this.tv_name = (TextView) itemView.findViewById(R.id.name_student);
            this.tv_grade = (TextView) itemView.findViewById(R.id.grade_student);
            this.tv_subject = (TextView) itemView.findViewById(R.id.subject_student);
            this.tv_place = (TextView) itemView.findViewById(R.id.place_student);
        }
    }

    @Override
    public Filter getFilter() {
        return NameFilter;
    }

    private Filter NameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudentData> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for ( StudentData item : arrayListFull) {
                    if (item.getTv_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
