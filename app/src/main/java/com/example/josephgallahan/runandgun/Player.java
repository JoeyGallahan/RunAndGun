package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.MotionEvent;

/**
 * Created by joseph.gallahan on 2/8/2017.
 */
public class Player
{
    //Dimensions & Collision
    Bitmap mImage;
    Bitmap mScaledImage;

    BoundingBox mBoundingBox;

    //Movement
    Vector2d mLocation;
    final float mSpeed = -50.0f, mGravity = 10.0f;
    float mVel = 0.0f;
    int mWidth, mHeight;

    //Actions
    Boolean mIsJumping = false, mCanShoot = true, mCanSlide = true;
    float mShootCooldown;
    int mSlidingFrames = 0;
    Bullet bullet;

    //Other
    int mFrameCounter;
    int mScore;

    boolean collidedWithObstacle = false;

    public Player(Context context)
    {
        mWidth = 200;
        mHeight = 200;

        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);

        mLocation = new Vector2d(50, World.getInstance().getGround().getY() - mImage.getHeight() - 10);
        mBoundingBox = new BoundingBox(mWidth, mHeight, mLocation);
        mScore = 0;
        mFrameCounter = 0;
    }

    //Accessors
    public Bitmap getImage()
    {
        return mScaledImage;
    }
    public Vector2d getLocation()
    {
        return mLocation;
    }
    public float getX()
    {
        return mLocation.getX();
    }
    public float getY()
    {
        return mLocation.getY();
    }

    //Mutators

    public void startJump()
    {
        if (!mIsJumping && mCanSlide)
        {
            mIsJumping = true;
            Log.d("Jumping", "yay");
            mIsJumping = true;
            mVel = mSpeed * 2;
            mLocation.add(0.0f, mVel);
            mBoundingBox.updateBox(mLocation);
        }
    }

    public void slide()
    {
        if (mCanSlide)
        {
            mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_slide);
            mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);
            mCanSlide = false;
        }

    }

    public void shoot()
    {
        if (mCanShoot)
        {
            bullet = new Bullet(mLocation.getX() + mWidth / 2, mLocation.getY() + mHeight / 2);
            mCanShoot = false;
        }
    }

    //Gameplay
    public void update()
    {
        mFrameCounter++;

        if(mFrameCounter == 60)
        {
            mScore+=10;
            mFrameCounter = 0;
        }

        for (int i = 0; i < World.getInstance().getNumVisibleObstacles(); i++)
        {
            if (mBoundingBox.checkCollision(World.getInstance().getVisibleObstacles().get(i).getBoundingBox()))
            {
                collidedWithObstacle = true;
                //Log.d("Collision", String.valueOf(i));
              //  mVel = 0.0f;
            }
        }

       // if (!collidedWithObstacle)
       // {
            if(mIsJumping && !mBoundingBox.checkCollision(World.getInstance().getGround().getBoundingBox()))
            {
                mVel += mGravity;
                mLocation.add(0.0f, mVel);
                mBoundingBox.updateBox(mLocation);

            }
            else
            {
                mVel = 0.0f;
                mLocation.setY(World.getInstance().getGround().getY() - mHeight);
                mIsJumping = false;
            }
     //   }

        if (mSlidingFrames >= 30)
        {
            mSlidingFrames = 0;
            mCanSlide = true;
            mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player);
            mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);
        }
        if (!mCanSlide)
        {
            mSlidingFrames++;
        }

        if (bullet != null)
        {
            if (bullet.getX() >= World.getInstance().getWidth())
            {
                mCanShoot = true;
                bullet = null;
            }
            else
            {
                bullet.update();
                if (!World.getInstance().getEnemies().isEmpty())
                {
                    if (bullet.checkCollision(World.getInstance().getEnemies().get(0).getBoundingBox()))
                    {
                        mCanShoot = true;
                        bullet = null;
                        World.getInstance().getEnemies().remove(0);
                        mScore += 50;
                    }
                }
            }
        }

        mBoundingBox.updateBox(mLocation);
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(getImage(),
                mLocation.getX(),
                mLocation.getY(),
                paint);

        paint.setTextSize(100.0f);
        canvas.drawText("Score: " + mScore, 50.0f, 100.0f, paint);

        if (!World.getInstance().getEnemies().isEmpty())
        {
            if (mBoundingBox.checkCollision(World.getInstance().getEnemies().get(0).getBoundingBox())) {
                canvas.drawText("R.I.P. Enemy Killed you", World.getInstance().getWidth() / 2.0f, 100.0f, paint);
            }
        }

        if (collidedWithObstacle)
        {
            canvas.drawText("R.I.P. Obstacle killed you", World.getInstance().getWidth() / 2.0f, 200.0f, paint);
            collidedWithObstacle = false;
        }

        if (bullet != null)
            bullet.draw(canvas, paint);
    }
}
