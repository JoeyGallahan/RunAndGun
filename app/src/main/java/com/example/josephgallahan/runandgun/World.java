package com.example.josephgallahan.runandgun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
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
    private ArrayList<Enemy> mEnemies;
    private boolean playerDead = false;

    private int mSpeed = -50;

    private int mScore = 0;

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;

    int soundIds[] = new int[3];

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
    public int getHeight() {
        return ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
    }

    public void init()
    {
        mWorldChunks = new WorldChunk[3];
        mEnemies = new ArrayList<>();

        boolean visible = true;

        for (int i = 0; i < 3; i++)
        {
            mWorldChunks[i] = new WorldChunk(mContext, true);

            if (visible)
            {
                visible = false;
            }
            else
            {
                mWorldChunks[i].setX(mWorldChunks[i-1].getGround().getX() + mWorldChunks[i-1].getGround().getImage().getWidth());
            }

            if (mWorldChunks[i].canSpawn())
            {
                mWorldChunks[i].addObstacles();
            }
        }

        mEnemies.add(new Enemy(mContext));

        mediaPlayer = MediaPlayer.create(mContext.getApplicationContext(), R.raw.background_music);
        mediaPlayer.setLooping(true);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        soundIds[0] = soundPool.load(mContext, R.raw.laser_blast, 1);
        soundIds[1] = soundPool.load(mContext, R.raw.enemy_hit, 1);
        soundIds[2] = soundPool.load(mContext, R.raw.death_sound, 1);
    }

    public void startMusic()
    {
        mediaPlayer.start();
    }

    public void stopMusic()
    {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(mContext.getApplicationContext(), R.raw.background_music);
    }

    public void bulletsound()
    {
        soundPool.play(soundIds[0],1,1,1,0,1.0f);
    }

    public void enemyHitSound()
    {
        soundPool.play(soundIds[1],1,1,1,0,1.0f);
    }

    public void setSpeed(int speed)
    {
        mSpeed = speed;
    }

    public void setPlayerDead(boolean dead, int score)
    {
        playerDead = dead;
        mScore = score;
        stopMusic();
        soundPool.play(soundIds[2],1,1,1,0,1.0f);
    }

    public void update()
    {
        for (int i = 0; i < 3; i++)
        {
            mWorldChunks[i].update();
        }
        if (!mEnemies.isEmpty())
        {
            mEnemies.get(0).update();
            if (mEnemies.get(0).getX() <= 0)
            {
                mEnemies.remove(0);
            }
        }
        else
        {
            mEnemies.add(new Enemy(mContext));
        }
    }

    public void draw(Canvas canvas, Paint paint)
    {
        if (playerDead && paint.getAlpha() > 0)
        {
            paint.setAlpha(paint.getAlpha() - 2);
           // Log.d("Paint ALpha", Integer.toString(paint.getAlpha()));

            if (paint.getAlpha() <= 2)
            {
                Intent i = GameOverActivity.newIntent(getContext(), mScore);
                setPlayerDead(false, 0);
                mSpeed = -30;
                soundPool.release();
                getContext().startActivity(i);
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isVisible())
            {
                mWorldChunks[i].draw(canvas, paint);
            }
        }
        if (!mEnemies.isEmpty())
        {
            mEnemies.get(0).draw(canvas, paint);
        }
    }

    public GroundBlock getGround()
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isUnderPlayer())
            {
                Log.d("Visible ground", String.valueOf(i));
                return mWorldChunks[i].getGround();
            }
        }
        return mWorldChunks[0].getGround();
    }

    public ArrayList<Enemy> getEnemies(){return mEnemies;}

    public int getSpeed()
    {
        return mSpeed;
    }

    public int getNumVisibleObstacles()
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isUnderPlayer())
               return mWorldChunks[i].getObstacles().size();
        }
        return 0;
    }

    public ArrayList<Obstacle> getVisibleObstacles()
    {
        for (int i = 0; i < 3; i++)
        {
            if (mWorldChunks[i].isUnderPlayer())
                return mWorldChunks[i].getObstacles();
        }
        return null;
    }
}
