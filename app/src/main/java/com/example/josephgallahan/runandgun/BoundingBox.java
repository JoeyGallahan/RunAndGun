package com.example.josephgallahan.runandgun;

import android.util.Log;
import android.widget.TwoLineListItem;

import static java.lang.Math.abs;

/**
 * Created by Joey on 3/2/2017.
 */

public class BoundingBox
{
    private float mWidth, mHeight;
    private Vector2d mLocation;
    private float mLeft, mRight, mTop, mBot;
    private Boolean mCanCollide;

    public BoundingBox(float width, float height, Vector2d loc)
    {
        mWidth = width;
        mHeight = height;

        mLocation = loc;

        mCanCollide = false;

        mLeft =  mLocation.getX();
        mRight = mLocation.getX() + mWidth;
        mTop =   mLocation.getY();
        mBot =   mLocation.getY() + mHeight;
    }

    public Boolean canCollide()
    {
        return mCanCollide;
    }

    public Boolean checkCollision(BoundingBox otherBox)
    {
        /*
        //Log.d("hey", "checking collision");
        if (mCanCollide)
        {
            if(abs(mLeft - otherBox.mLeft) < (mWidth + otherBox.mWidth))
            {
                // Log.d("First Part", String.valueOf(abs(mTop - otherBox.mTop)*2));
                //Log.d("Second part", String.valueOf(mHeight + otherBox.mHeight));
                if (abs(mTop - otherBox.mTop) < (mHeight + otherBox.mHeight))
                {
                    // Log.d("Collision", "Vert");
                    return true;
                }
            }
            return false;
        }
        return false;
        */

        /*
        if (mLeft < otherBox.mRight &&
            mRight > otherBox.mLeft &&
            mTop < otherBox.mBot    &&
            mBot > otherBox.mTop)
        {
            return true;
        }

        return false;
        */

        return !( (mBot < otherBox.mTop) || (mTop > otherBox.mBot) || (mLeft > otherBox.mRight) || (mRight < otherBox.mLeft) );
    }

    public void updateBox(Vector2d loc)
    {
        mLocation = loc;

        mLeft =  mLocation.getX();
        mRight = mLocation.getX() + mWidth;
        mTop =   mLocation.getY();
        mBot =   mLocation.getY() + mHeight;

        if (mLeft <= World.getInstance().getWidth() / 3)
        {
            mCanCollide = true;
        }
        else
        {
            mCanCollide = false;
        }
    }
}
