package com.example.vatok.androidweather;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import timber.log.Timber;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>
{
    public interface OnItemClickListener{
        void onItemClick(CityInfo item, int pos);
        void onItemButtonClick(CityInfo item, int pos);
    }

    List<CityInfo> items;
    private int layoutResId;
    OnItemClickListener itemClickListener;

    public RVAdapter(List<CityInfo> items, int layoutResId, OnItemClickListener listener)
    {
        this.items = items;
        this.layoutResId = layoutResId;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        ViewHolder vh = new ViewHolder(view);
        Timber.d("onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Timber.d("onBindViewHolder");
        holder.bind(position);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView favoriteImageView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            favoriteImageView = itemView.findViewById(R.id.iv_favor);
        }

        public void bind(final int position)
        {
            final CityInfo item = items.get(position);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    itemClickListener.onItemClick(item, position);
                }
            });

            if(item.isFavorite()) {
                favoriteImageView.setImageResource(R.drawable.star);
            }
            else {
                favoriteImageView.setImageResource(R.drawable.star_empty);
            }

            if(item.isActive()) {
                titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.BOLD);
            }
            else {
                titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.NORMAL);
            }
            titleTextView.setText(item.getTitle());

            favoriteImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    itemClickListener.onItemButtonClick(item, position);
                }
            });


        }
    }
}
