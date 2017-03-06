package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by joseph.gallahan on 2/13/2017.
 */
public class GroundBlock
{
    int mWidth, mHeight;
    Bitmap mImage, mScaledBitmap;
    Vector2d mLocation;
    boolean mIsVisible;

    BoundingBox mBoundingBox;

    public GroundBlock(Context context, boolean isVisible)
    {
        mIsVisible = isVisible;

        mHeight = 100;

        int x = 0, y = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight() - mHeight;

        mLocation = new Vector2d(x, y);

        mWidth = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth() - 20;

        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground_block);
        mScaledBitmap = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);

        mBoundingBox = new BoundingBox(mWidth, mHeight, mLocation);
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
    public BoundingBox getBoundingBox(){return mBoundingBox;}

    //Mutators
    public void setX(float x)
    {
        mLocation.setX(x);
    }

    //Gameplay
    public void update()
    {
        mLocation.add(World.getInstance().getSpeed(), 0.0f);
        mBoundingBox.updateBox(mLocation);
    }
}
