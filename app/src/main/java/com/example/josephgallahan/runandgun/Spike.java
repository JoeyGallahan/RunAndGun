package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;

import java.util.Random;

/**
 * Created by Joey on 3/4/2017.
 */

public class Spike extends Obstacle
{
    public Spike(Context context, GroundBlock block)
    {
        setContext(context);
        setImage(R.drawable.obstacle_spike);
        setSpawnZone(block.getLocation());
        spawn();
        mIsGood = false;
    }

    protected void spawn()
    {
        Random r = new Random();

        int x = r.nextInt(World.getInstance().getWidth())+ (int)mSpawnZone.getX() + 50;
        float y = World.getInstance().getGround().getY() - mImage.getHeight();

        setLocation(new Vector2d(x,y));

        mBoundingBox = new BoundingBox(mContext, mImage.getWidth(), mImage.getHeight(), mLocation);
    }
    protected void update()
    {
        mLocation.add(World.getInstance().getSpeed(), 0.0f);
        mBoundingBox.updateBox(mLocation);
    }
}
