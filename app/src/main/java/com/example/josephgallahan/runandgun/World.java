package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Queue;

/**
 * Created by joseph.gallahan on 2/13/2017.
 */

public class World
{
    private WorldChunk[] mWorldChunks;

    public World(Context context)
    {
        mWorldChunks = new WorldChunk[3];
        boolean visible = true;

        for (int i = 0; i < 3; i++)
        {
            mWorldChunks[i] = new WorldChunk(context, visible);

            if (visible)
                visible = false;
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
        mWorldChunks[0].draw(canvas, paint);
    }

}
