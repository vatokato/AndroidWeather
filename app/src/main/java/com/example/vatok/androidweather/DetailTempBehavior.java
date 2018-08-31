package com.example.vatok.androidweather;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    int chInitialW;
    int chInitialH;
    float chDiffScale;
    float chFinishScale;

    float chInitialX;
    float chFinishX;
    Float chDiffX;

    float chInitialY;
    float chFinishY;
    Float chDiffY;


    Drawable bg;
    int chInitialA;
    int chFinishA;
    int chDiffA;

    Drawable overlay;
    int chInitialOverlay;
    int chFinishOverlay;
    int chDiffOverlay;


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
            toolbar = parent.findViewById(R.id.toolbar_details);
            appbarInitialY = dependency.getY();
            appbarWidth = (float) dependency.getWidth();
            appbarHeight = (float) dependency.getHeight() -  toolbar.getHeight();

            chInitialW = child.getWidth();
            chInitialH = child.getHeight();

            chFinishScale = 0.4f;
            chDiffScale = 1 - chFinishScale;

            chInitialX = (appbarWidth/2)-(chInitialW/2);
            chFinishX = appbarWidth-chInitialW + (chInitialW*chDiffScale/2);
            chDiffX = chFinishX - chInitialX;

            chInitialY = (appbarHeight/2 + toolbar.getHeight())-(chInitialH/2);
            chFinishY = -(chInitialW*chDiffScale/2);
            chDiffY = chInitialY - chFinishY;


            bg = child.getBackground();
            chInitialA = 150;
            chFinishA = 0;
            chDiffA = chFinishA - chInitialA;

            overlay = parent.findViewById(R.id.v_overlay).getBackground();
            chInitialOverlay = 20;
            chFinishOverlay = 120;
            chDiffOverlay = chFinishOverlay - chInitialOverlay;
        }

        float onePercent = (appbarHeight / 100);
        float currentPercent = (appbarInitialY - dependency.getY())/ onePercent; // 0 - открыто, 100 - закрыто

        child.setY(chInitialY - (currentPercent/100 * chDiffY));
        child.setX(chInitialX + (currentPercent/100 * chDiffX));
        child.setScaleX(1-currentPercent/100*chDiffScale);
        child.setScaleY(1-currentPercent/100*chDiffScale);
        bg.setAlpha( (int) (chInitialA + ((int)((currentPercent/100)*chDiffA))) );
        overlay.setAlpha( (int) (chInitialOverlay + ((int)((currentPercent/100)*chDiffOverlay))) );

        return false;
    }
}
