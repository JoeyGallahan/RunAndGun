package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

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
    Bitmap mImage;
    Bitmap mScaledImage;

    public BoundingBox(Context context, float width, float height, Vector2d loc)
    {
        mWidth = width;
        mHeight = height;

        mLocation = loc;

        mCanCollide = false;

        mLeft =  mLocation.getX();
        mRight = mLocation.getX() + mWidth;
        mTop =   mLocation.getY();
        mBot =   mLocation.getY() + mHeight;

        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bounding_box);
        mScaledImage = Bitmap.createScaledBitmap(mImage, (int)mWidth, (int)mHeight, true);
    }

    public Boolean canCollide()
    {
        return mCanCollide;
    }

    public Boolean checkCollision(BoundingBox otherBox)
    {
        return !( (mBot < otherBox.mTop) || (mTop > otherBox.mBot) || (mLeft > otherBox.mRight) || (mRight < otherBox.mLeft) );
    }

    public Boolean checkCollision(ArrayList<Obstacle> obstacles)
    {
        for (int i = 0; i < obstacles.size(); i++)
        {
            BoundingBox otherBox = obstacles.get(i).getBoundingBox();

            if (!( (mBot < otherBox.mTop) || (mTop > otherBox.mBot) || (mLeft > otherBox.mRight) || (mRight < otherBox.mLeft) ))
            {
                return true;
            }
        }
        return false;
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
    public void draw(Canvas canvas, Paint paint)
    {
       /* canvas.drawBitmap(mScaledImage,
                mLocation.getX(),
                mLocation.getY(),
                paint);*/
    }
}
