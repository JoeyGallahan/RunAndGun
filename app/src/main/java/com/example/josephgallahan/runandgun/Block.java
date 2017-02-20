package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.Random;

/**
 * Created by joseph.gallahan on 2/20/2017.
 */
public class Block extends Obstacle
{
    private int mWidth; //Gonna be a square so width & height is the same

    public Block(Context context)
    {
        setContext(context);
        setImage(R.drawable.obstacle_block);
        mWidth = 50;
        spawn();
    }

    protected void spawn()
    {
        Random r = new Random();

        int x = r.nextInt(((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth() - 120) + 120 ;
        int y = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getHeight() - 230;

        setLocation(new Vector2d(x,y));
    }
    protected void update()
    {
        float x = mLocation.getX() - 50;

        mLocation.setX(x);
    }
}
