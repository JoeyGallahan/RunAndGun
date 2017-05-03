package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.Random;

/**
 * Created by joseph.gallahan on 2/20/2017.
 */
public class Block extends Obstacle
{
    public Block(Context context, GroundBlock block)
    {
        setContext(context);
        setImage(R.drawable.obstacle_block);
        setSpawnZone(block.getLocation());
        spawn();
        mIsGood = true;
    }

    protected void update()
    {
        mLocation.add(World.getInstance().getSpeed(), 0.0f);
        mBoundingBox.updateBox(mLocation);
    }
}
