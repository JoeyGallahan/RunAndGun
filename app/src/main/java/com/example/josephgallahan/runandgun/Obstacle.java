package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;

/**
 * Created by joseph.gallahan on 2/20/2017.
 */
public abstract class Obstacle
{
    protected Vector2d mLocation;
    protected Bitmap mImage;
    protected Context mContext;
    protected BoundingBox mBoundingBox;
    protected Vector2d mSpawnZone;

    public Obstacle(){}

    protected Context getContext(){return mContext;}
    protected void setContext(Context context){mContext = context;}
    protected void setImage(int drawableID)
    {
        mImage = BitmapFactory.decodeResource(mContext.getResources(), drawableID);
    }
    public void setLocation(Vector2d loc) {mLocation = loc;}
    public void setSpawnZone(Vector2d loc) {mSpawnZone = loc;}

    protected abstract void spawn();
    protected abstract void update();

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
