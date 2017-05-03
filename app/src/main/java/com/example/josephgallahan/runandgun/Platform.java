package com.example.josephgallahan.runandgun;

import android.content.Context;

import java.util.Random;

/**
 * Created by Joey on 4/10/2017.
 */

public class Platform extends Obstacle
    {
        public Platform(Context context, GroundBlock block)
        {
            setContext(context);
            setImage(R.drawable.obstacle_platform);
            setSpawnZone(block.getLocation());
            spawn();
            mIsGood = true;
        }

    protected void spawn()
    {
        Random r = new Random();

        int x = r.nextInt(World.getInstance().getWidth())+ (int)mSpawnZone.getX();
        float y = World.getInstance().getHeight() / 2 - mImage.getHeight();

        setLocation(new Vector2d(x,y));

        mBoundingBox = new BoundingBox(World.getInstance().getContext(),mImage.getWidth(), mImage.getHeight(), mLocation);
    }
    protected void update()
    {
        mLocation.add(World.getInstance().getSpeed(), 0.0f);
        mBoundingBox.updateBox(mLocation);
    }
}

