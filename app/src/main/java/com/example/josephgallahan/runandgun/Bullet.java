package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.text.DateFormat;

/**
 * Created by Joey on 3/4/2017.
 */

public class Bullet
{
    private Vector2d mLocation;
    private Bitmap mImage;
    private BoundingBox mBoundingBox;
    private float mSpeed = 100.0f;
    private boolean mSlideShot;

    public Bullet(float x, float y, boolean slideShot)
    {
        mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.bullet);
        mLocation = new Vector2d(x, y);
        mBoundingBox = new BoundingBox(World.getInstance().getContext(), mImage.getWidth(), mImage.getHeight(), mLocation);
        mSlideShot = slideShot;
    }

    public float getX()
    {
        return mLocation.getX();
    }

    public boolean wasSlideShot(){return mSlideShot;}


    public void update()
    {
        mLocation.add(mSpeed, 0.0f);
        mBoundingBox.updateBox(mLocation);
    }

    public Boolean checkCollision(BoundingBox box)
    {
        return mBoundingBox.checkCollision(box);
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mImage,
                mLocation.getX(),
                mLocation.getY(),
                paint);
        mBoundingBox.draw(canvas, paint);
    }

    public BoundingBox getBoundingBox()
    {
        return mBoundingBox;
    }
}
