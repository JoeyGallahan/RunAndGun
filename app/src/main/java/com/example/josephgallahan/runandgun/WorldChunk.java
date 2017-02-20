package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joseph.gallahan on 2/13/2017.
 */
public class WorldChunk
{
    private GroundBlock mGround;
    private ArrayList<Obstacle> mObstacles;
    private boolean mVisible;

    public WorldChunk(Context context, boolean isVisible)
    {
        mVisible = isVisible;

        mGround = new GroundBlock(context, isVisible);

        mObstacles = new ArrayList<Obstacle>();
        mObstacles.add(new Block(context));
    }

    public void update()
    {
        for (int i = 0; i < mObstacles.size(); i++)
        {
            mObstacles.get(i).update();
        }

        mGround.update();
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mGround.getImage(),
                          mGround.getX(),
                          mGround.getY(),
                          paint);

        for (int i = 0; i < mObstacles.size(); i++)
        {
            mObstacles.get(i).draw(canvas, paint);
        }
    }

}
