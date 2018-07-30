package com.example.vatok.androidweather;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration
{
    int spacing;

    public SpacesItemDecoration(int spacing)
    {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        int position = parent.getChildAdapterPosition(view);
        //int position / columCount;
        if(position != 0)
        {
            outRect.top = spacing;
        }
    }
}
