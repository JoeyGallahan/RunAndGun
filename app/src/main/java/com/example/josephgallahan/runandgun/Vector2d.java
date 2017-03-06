package com.example.josephgallahan.runandgun;

import java.util.Vector;

/**
 * Created by joseph.gallahan on 2/8/2017.
 */
public class Vector2d
{
    private float mX, mY;

    public Vector2d(float x, float y)
    {
        mX = x;
        mY = y;
    }

    //Accessors
    public float getX()
    {
        return mX;
    }

    public float getY()
    {
        return mY;
    }

    //Mutators
    public float setX(float x)
    {
        mX = x;
        return mX;
    }

    public float setY(float y)
    {
        mY = y;
        return mY;
    }

    public void set(float x, float y)
    {
        mX = x;
        mY = y;
    }

    public Vector2d add(float x, float y)
    {
        mX += x;
        mY += y;

        return this;
    }

    public Vector2d sub(float x, float y)
    {
        mX -= x;
        mY -= y;

        return this;
    }
}
