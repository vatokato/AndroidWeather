package com.example.vatok.androidweather;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>
{
    public interface OnItemClickListener{
        void onItemClick(CityInfo item, int pos);
    }

    Data data;
    List<CityInfo> items;
    private int layoutResId;
    OnItemClickListener itemClickListener;

    public RVAdapter(Data data, List<CityInfo> items, int layoutResId, OnItemClickListener listener)
    {
        this.items = items;
        this.layoutResId = layoutResId;
        this.itemClickListener = listener;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        ViewHolder vh = new ViewHolder(view);
        //Timber.d("onCreateViewHolder");
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //Timber.d("onBindViewHolder");
        holder.bind(position);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
            titleTextView.setText(item.getTitle());
        }
    }
}
