package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import static java.lang.Math.abs;

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
    }

    public ArrayList<Obstacle> getObstacles()
    {
        return mObstacles;
    }

    public void addObstacles()
    {
        Random r = new Random();
        for (int i = 0; i < 3; i++)
        {
            float yea = r.nextFloat();
            if (yea <= 0.3333f)
            {
                mObstacles.add(new Spike(World.getInstance().getContext(), getGround()));
            }
            else if (yea <= 0.6666f)
            {
                mObstacles.add(new Block(World.getInstance().getContext(), getGround()));
            }
            else
            {
                mObstacles.add(new Lava(World.getInstance().getContext(), getGround()));
            }
        }
    }

    public boolean isVisible()
    {
        return mVisible;
    }

    public void update()
    {
        for (int i = 0; i < mObstacles.size(); i++)
        {
            mObstacles.get(i).update();
        }

        mGround.update();

        if (abs(mGround.getX() - mGround.getImage().getWidth()) <= 10 && !mVisible)
        {
            mVisible = true;
            addObstacles();
        }
        if (mGround.getX() + mGround.getImage().getWidth() <= 0)
        {
            mVisible = false;
            setX(mGround.getImage().getWidth() * 2);
            mObstacles.clear();
        }
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

    public GroundBlock getGround()
    {
        return mGround;
    }

    public void setX(float x)
    {
        mGround.setX(x);
    }

}
