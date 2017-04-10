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

    public Bullet(float x, float y)
    {
        mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.bullet);
        mLocation = new Vector2d(x, y);
        mBoundingBox = new BoundingBox(mImage.getWidth(), mImage.getHeight(), mLocation);
    }

    public float getX()
    {
        return mLocation.getX();
    }


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
    }

    public BoundingBox getBoundingBox()
    {
        return mBoundingBox;
    }
}
