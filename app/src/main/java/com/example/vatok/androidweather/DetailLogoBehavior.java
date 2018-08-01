package com.example.vatok.androidweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class DetailLogoBehavior extends CoordinatorLayout.Behavior<ImageView>
{

    Float appbarInitialY = null;
    Float appbarHeight = null;
    Toolbar toolbar;

    int ivInitialH;
    float ivInitialY;
    float ivFinishY;
    Float ivDistanseY;

    int ivInitialW;
    float ivInitialX;
    float ivFinishX;
    Float ivDistanseX;


    public DetailLogoBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency)
    {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency)
    {
        if(appbarInitialY == null)
        {
            toolbar = parent.findViewById(R.id.toolbar);
            appbarInitialY = dependency.getY();
            appbarHeight = (float) dependency.getHeight() -  toolbar.getHeight();

            ivInitialH = child.getHeight();
            ivInitialY = toolbar.getHeight()+15;
            ivFinishY = 6-(ivInitialH*0.25f);
            ivDistanseY = ivInitialY - ivFinishY;

            ivInitialW = child.getWidth();
            ivInitialX = 0;
            ivFinishX = 10 - (ivInitialW*0.25f);
            ivDistanseX = ivFinishX - ivInitialX;
        }

        float onePercent = (appbarHeight / 100);
        float currentDiff = appbarInitialY - dependency.getY();
        float currentPercent = 100 - currentDiff / onePercent;

        child.setY(ivInitialY - ((100-currentPercent)/100 * ivDistanseY));
        child.setX(ivInitialX + ((100-currentPercent)/100 * ivDistanseX));
        child.setScaleY(1 - ((100-currentPercent)/100 * 0.5f));
        child.setScaleX(1 - ((100-currentPercent)/100 * 0.5f));

        return false;
    }
}
