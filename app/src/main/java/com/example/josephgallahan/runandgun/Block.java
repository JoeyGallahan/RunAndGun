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
    public Block(Context context)
    {
        setContext(context);
        setImage(R.drawable.obstacle_block);
        spawn();
    }

    protected void spawn()
    {
        Random r = new Random();

        int x = r.nextInt(((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth() - 120) + 120 ;
        float y = World.getInstance().getGround().getY() - mImage.getHeight();

        setLocation(new Vector2d(x,y));

        mBoundingBox = new BoundingBox(mImage.getWidth(), mImage.getHeight(), mLocation);
    }
    protected void update()
    {
        mLocation.add(World.getInstance().getSpeed(), 0.0f);
        mBoundingBox.updateBox(mLocation);
    }
}