package com.example.vatok.androidweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class DetailTempBehavior extends CoordinatorLayout.Behavior<TextView>
{

    Float appbarInitialY = null;
    Float appbarWidth = null;
    Float appbarHeight = null;
    Toolbar toolbar;

    int chInitialH;
    float chInitialY;
    float chFinishY;
    Float chDistanseY;

    int chInitialW;
    float chInitialX;
    float chFinishX;
    Float chDistanseX;


    public DetailTempBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency)
    {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency)
    {
        if(appbarInitialY == null)
        {
            toolbar = parent.findViewById(R.id.toolbar);
            appbarInitialY = dependency.getY();
            appbarWidth = (float) dependency.getWidth();
            appbarHeight = (float) dependency.getHeight() -  toolbar.getHeight();

            chInitialH = child.getHeight();
            chInitialY = (appbarHeight/2 + toolbar.getHeight())-(chInitialH/2);
            chFinishY = 4-(chInitialH*0f);
            chDistanseY = chInitialY - chFinishY;

            chInitialW = child.getWidth();
            chInitialX = (appbarWidth/2)-(chInitialW/2);
            chFinishX = appbarWidth-chInitialW - 4 - (chInitialW*0f);
            chDistanseX = chFinishX - chInitialX;
        }

        float onePercent = (appbarHeight / 100);
        float currentDiff = appbarInitialY - dependency.getY();
        float currentPercent = 100 - currentDiff / onePercent;

        child.setY(chInitialY - ((100-currentPercent)/100 * chDistanseY));
        child.setX(chInitialX + ((100-currentPercent)/100 * chDistanseX));
//        child.setScaleY(1 - ((100-currentPercent)/100 * 0.5f));
//        child.setScaleX(1 - ((100-currentPercent)/100 * 0.5f));
        return false;
    }
}
