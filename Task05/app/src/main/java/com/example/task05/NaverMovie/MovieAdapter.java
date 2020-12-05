package com.example.task05.NaverMovie;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.task05.MovieDetail;
import com.example.task05.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    ArrayList<Item> mMovieInfoArrayList;

    public MovieAdapter(Context context, ArrayList<Item> movieInfoArrayList) {
        mContext = context;
        mMovieInfoArrayList = movieInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

        Item item = mMovieInfoArrayList.get(position);
        movieViewHolder.mTvTitle.setText((item.getTitle())); //Html.fromHtml 왜 썼을까? 안 써도 잘 되는데?
        movieViewHolder.mRbUserRating.setRating(Float.parseFloat(item.getUserRating()) / 2);
        movieViewHolder.mTvPubDate.setText(item.getPubDate());
        movieViewHolder.mTvDirector.setText((item.getDirector()));
        movieViewHolder.mTvActor.setText((item.getActor()));


        Glide.with(mContext)
                .load(item.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieViewHolder.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != RecyclerView.NO_POSITION) {
                    Item item = mMovieInfoArrayList.get(position);
                    Intent intent = new Intent(v.getContext(), MovieDetail.class);
                    intent.putExtra("movie_name", (item.getTitle()));
                    intent.putExtra("rating", Float.parseFloat(item.getUserRating()) / 2);
                    intent.putExtra("pub_date", item.getPubDate());
                    intent.putExtra("director", (item.getDirector()));
                    intent.putExtra("actor", (item.getActor()));
                    intent.putExtra("poster", item.getImage());
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieInfoArrayList.size();
    }

    public void clearItems() {
        mMovieInfoArrayList.clear();
        notifyDataSetChanged();
    }

    public void clearAndAddItems(ArrayList<Item> items) {
        mMovieInfoArrayList.clear();
        mMovieInfoArrayList.addAll(items);
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPoster;
        private TextView mTvTitle;
        private RatingBar mRbUserRating;
        private TextView mTvPubDate;
        private TextView mTvDirector;
        private TextView mTvActor;


        MovieViewHolder(final View view) {
            super(view);
            mIvPoster = view.findViewById(R.id.iv_poster);
            mTvTitle = view.findViewById(R.id.tv_title);
            mRbUserRating = view.findViewById(R.id.rb_user_rating);
            mTvPubDate = view.findViewById(R.id.tv_pub_date);
            mTvDirector = view.findViewById(R.id.tv_director);
            mTvActor = view.findViewById(R.id.tv_actor);


        }

        public ImageView getImage() {
            return mIvPoster;
        }



    }


}
