package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Joey on 4/9/2017.
 */

public class Enemy
{
    //Dimensions & Collision
    Bitmap mImage;
    BoundingBox mBoundingBox;

    //Movement
    Vector2d mLocation;
    final float mSpeed;

    public Enemy(Context context)
    {
        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        mLocation = new Vector2d(World.getInstance().getWidth(), World.getInstance().getGround().getY() - mImage.getHeight());
        mBoundingBox = new BoundingBox(context, mImage.getWidth(), mImage.getHeight(), mLocation);

       mSpeed = World.getInstance().getSpeed() + 10.0f;
    }

    //Accessors
    public Bitmap getImage()
    {
        return mImage;
    }
    public Vector2d getLocation()
    {
        return mLocation;
    }
    public BoundingBox getBoundingBox(){return mBoundingBox;}
    public float getX()
    {
        return mLocation.getX();
    }
    public float getY()
    {
        return mLocation.getY();
    }

    //Gameplay
    public void update()
    {
        mLocation.add(mSpeed, 0.0f);
        mBoundingBox.updateBox(mLocation);
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mImage,
                mLocation.getX(),
                mLocation.getY(),
                paint);

        mBoundingBox.draw(canvas, paint);
    }
}
