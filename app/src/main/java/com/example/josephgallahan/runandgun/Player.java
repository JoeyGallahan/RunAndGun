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

    BoundingBox mBoundingBox;


    //Movement
    Vector2d mLocation;
    final float mSpeed = -50.0f, mGravity = 15.0f;
    float mVel = 0.0f;

    //Actions
    Boolean mIsJumping = false, mCanShoot = true, mCanSlide = true;
    float mShootCooldown;
    int mSlidingFrames = 0;
    Bullet bullet;

    //Other
    int mScore;

    public Player(Context context)
    {
        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        mLocation = new Vector2d(50, World.getInstance().getGround().getY() - mImage.getHeight());
        mBoundingBox = new BoundingBox(mImage.getWidth(), mImage.getHeight(), mLocation);
        //
    }

    //Accessors
    public Bitmap getImage()
    {
        return mImage;
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
            mCanSlide = false;
        }

    }

    public void shoot()
    {
        if (mCanShoot)
        {
            bullet = new Bullet(mLocation.getX() + getImage().getWidth() / 2, mLocation.getY() + getImage().getHeight() / 2);
            mCanShoot = false;
        }
    }

    //Gameplay
    public void update()
    {
        boolean collidedWithObstacle = false;

        for (int i = 0; i < World.getInstance().getNumVisibleObstacles(); i++)
        {
            if (mBoundingBox.checkCollision(World.getInstance().getVisibleObstacles().get(i).getBoundingBox()))
            {
                //collidedWithObstacle = true;
                //Log.d("Collision", String.valueOf(i));
                //mVel = 0.0f;
            }
        }
        if (!collidedWithObstacle)
        {
            if (!mBoundingBox.checkCollision(World.getInstance().getGround().getBoundingBox()))
            {
                mVel += mGravity;
                mLocation.add(0.0f, mVel);
                mBoundingBox.updateBox(mLocation);
                mIsJumping = true;
            }
            else
            {
                mVel = 0.0f;
                mLocation.setY(World.getInstance().getGround().getY() - mImage.getHeight());
                mIsJumping = false;
            }
        }

        if (mSlidingFrames >= 30)
        {
            mSlidingFrames = 0;
            mCanSlide = true;
            mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player);
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
            }
            else
            {
                bullet.update();
            }
        }

        mBoundingBox.updateBox(mLocation);
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(mImage,
                mLocation.getX(),
                mLocation.getY(),
                paint);

        if (bullet != null)
            bullet.draw(canvas, paint);
    }
}
