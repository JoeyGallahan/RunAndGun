package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by joseph.gallahan on 2/13/2017.
 */
public class GroundBlock
{
    int mWidth, mHeight;
    Bitmap mImage, mScaledBitmap;
    float mSpeedX;
    Vector2d mLocation;
    boolean mIsVisible;

    public GroundBlock(Context context, boolean isVisible)
    {
        mIsVisible = isVisible;

        mHeight = 100;

        int x, y = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight() - mHeight;;

        if (isVisible)
            x = 10;
        else
            x = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();

        mLocation = new Vector2d(x, y);

        mSpeedX = -50;

        mWidth = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() - 20;

        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground_block);
        mScaledBitmap = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);
    }

    //Accessors
    public Bitmap getImage()
    {
        return mScaledBitmap;
    }
    public Vector2d getLocation()
    {
        return mLocation;
    }
    public float getX()
    {
        return mLocation.getX();
    }
    public float getY()
    {
        return mLocation.getY();
    }

    //Mutators

    //Gameplay
    public void update()
    {
        float x = mLocation.getX() + mSpeedX;

        mLocation.setX(x);
    }
}
