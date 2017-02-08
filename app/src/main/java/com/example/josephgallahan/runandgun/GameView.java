package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by joseph.gallahan on 2/8/2017.
 */
public class GameView extends SurfaceView implements Runnable
{
    boolean mPlaying;
    private Thread mGameThread = null;

    private Player mPlayer;

    //Drawing
    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    public GameView(Context context)
    {
        super(context);

        //Init player
        mPlayer = new Player(context);

        //Init drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
    }

    @Override
    public void run()
    {
        while (mPlaying)
        {
            update();
            draw();
            control();
        }
    }

    //Update the frame
    private void update()
    {
        mPlayer.update();
    }

    //Draw the frame
    private void draw()
    {
        //First check to make sure the surface to draw on
        //actually exists
        if (mSurfaceHolder.getSurface().isValid())
        {
            //Lock the canvas for drawing
            mCanvas = mSurfaceHolder.lockCanvas();
            //Draw Background color
            mCanvas.drawColor(Color.GRAY);
            //Draw the player
            mCanvas.drawBitmap
                    (mPlayer.getImage(),
                     mPlayer.getX(),
                     mPlayer.getY(),
                     mPaint);
            //Unlock the canvas
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    //Control the thread
    private void control()
    {
        try
        {
            mGameThread.sleep(17);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //Pause the game. Eventually bring up a menu
    public void pause()
    {
        mPlaying = false;

        try
        {
            mGameThread.join();
        }
        catch(InterruptedException e){}
    }

    //Resume the game
    public void resume()
    {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }
}
