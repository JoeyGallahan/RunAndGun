package com.example.josephgallahan.runandgun;

import android.content.Context;

import java.util.Random;

/**
 * Created by Joey on 4/9/2017.
 */

public class Lava extends Obstacle
{
    public Lava(Context context, GroundBlock block)
    {
        setContext(context);
        setImage(R.drawable.lava);
        setSpawnZone(block.getLocation());
        spawn();
    }

    protected void spawn()
    {
        Random r = new Random();

        int x = r.nextInt(World.getInstance().getWidth())+ (int)mSpawnZone.getX() + 50;
        float y = World.getInstance().getGround().getY();

        setLocation(new Vector2d(x,y));

        mBoundingBox = new BoundingBox(mImage.getWidth(), mImage.getHeight(), mLocation);
    }
    protected void update()
    {
        mLocation.add(World.getInstance().getSpeed(), 0.0f);
        mBoundingBox.updateBox(mLocation);
    }
}
