package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by joseph.gallahan on 2/13/2017.
 */

public class World
{
    private WorldChunk[] mWorldChunks;
    private static World mInstace = null;
    private Context mContext;

    private int mSpeed = -10;

    public static synchronized World getInstance()
    {
        if (mInstace == null)
        {
            mInstace = new World();
        }
        return mInstace;
    }

    private World()
    {
    }

    public void setContext(Context context)
    {
        mContext = context;
    }

    public Context getContext()
    {
        return mContext;
    }

    public int getWidth()
    {
        return ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
    }
    public int getHeight()
    {
        return ((Activity)mContext).getWindowManager().getDefaultDisplay().getHeight();
    }

    public void init()
    {
        mWorldChunks = new WorldChunk[3];
        boolean visible = true;

        for (int i = 0; i < 3; i++)
        {
            mWorldChunks[i] = new WorldChunk(mContext, visible);

            if (visible)
            {
                visible = false;
            }
            else
            {
                mWorldChunks[i].setX(mWorldChunks[i-1].getGround().getX() + mWorldChunks[i-1].getGround().getImage().getWidth());
            }

            mWorldChunks[i].addObstacles();
        }
    }

    public void update()
    {
        for (int i = 0; i < 3; i++)
        {
            mWorldChunks[i].update();
        }
    }

    public void draw(Canvas canvas, Paint paint)
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isVisible())
            {
                mWorldChunks[i].draw(canvas, paint);
            }

        }
    }

    public GroundBlock getGround()
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isVisible())
            {
                return mWorldChunks[i].getGround();
            }
        }
        return mWorldChunks[0].getGround();
    }

    public int getSpeed()
    {
        return mSpeed;
    }

    public int getNumVisibleObstacles()
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isVisible())
               return mWorldChunks[i].getObstacles().size();
        }
        return 0;
    }

    public ArrayList<Obstacle> getVisibleObstacles()
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isVisible())
                return mWorldChunks[i].getObstacles();
        }
        return null;
    }
}
