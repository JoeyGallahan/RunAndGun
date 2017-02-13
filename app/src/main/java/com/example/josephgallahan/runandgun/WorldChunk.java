package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Queue;

/**
 * Created by joseph.gallahan on 2/13/2017.
 */
public class WorldChunk
{
    private GroundBlock mGround;
    private boolean mVisible;

    public WorldChunk(Context context, boolean isVisible)
    {
        mVisible = isVisible;

        mGround = new GroundBlock(context, isVisible);
    }

    public void update()
    {
        mGround.update();
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mGround.getImage(),
                          mGround.getX(),
                          mGround.getY(),
                          paint);
    }

}
