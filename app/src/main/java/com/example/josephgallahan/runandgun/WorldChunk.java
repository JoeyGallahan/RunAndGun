package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private boolean mUnderPlayer;
    private boolean mCanSpawn;

    public WorldChunk(Context context, boolean isVisible)
    {
        mVisible = isVisible;
        mUnderPlayer = false;
        mCanSpawn = !mVisible;

        if(isVisible)
            mUnderPlayer = true;

        mGround = new GroundBlock(context, isVisible);

        mObstacles = new ArrayList<Obstacle>();
    }

    public ArrayList<Obstacle> getObstacles()
    {
        return mObstacles;
    }

    public boolean canSpawn(){return mCanSpawn;}

    public void addObstacles()
    {
        Random r = new Random();
        for (int i = 0; i < 3; i++)
        {
            float yea = r.nextFloat();
            if (yea <= 0.25f)
            {
                mObstacles.add(new Spike(World.getInstance().getContext(), getGround()));
            }
            else if (yea <= 0.50f)
            {
                mObstacles.add(new Block(World.getInstance().getContext(), getGround()));
            }
            else if (yea <= 0.75f)
            {
                mObstacles.add(new Lava(World.getInstance().getContext(), getGround()));
            }
            else
            {
                mObstacles.add(new Platform(World.getInstance().getContext(), getGround()));
            }
        }
    }

    public boolean isVisible()
    {
        return mVisible;
    }

    public boolean isUnderPlayer(){return mUnderPlayer;}

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
        if (mGround.getX() + mGround.getImage().getWidth() <= -10)
        {
            mVisible = false;
            setX(mGround.getImage().getWidth() * 2);
            mCanSpawn = true;
            mObstacles.clear();
        }

        if (mGround.getX() <= 50)
        {
            mUnderPlayer = true;
        }
        if (mGround.getX() + mGround.getImage().getWidth() < 50)
        {
            mUnderPlayer = false;
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

        mGround.getBoundingBox().draw(canvas, paint);
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
