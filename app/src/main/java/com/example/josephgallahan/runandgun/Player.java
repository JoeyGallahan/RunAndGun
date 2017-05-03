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
    final float mSpeed = -55.0f, mGravity = 10.0f;
    float mVel = 0.0f;
    int mWidth, mHeight;

    //Actions
    Boolean mIsJumping = false, mCanShoot = true, mCanSlide = true, mDrawStyle = false;
    int mSlidingFrames = 0;
    Bullet bullet;

    float styleSize = 200.0f;

    //Other
    int mFrameCounter;
    int mScore;

    boolean collidedWithObstacle = false;
    boolean goodCollision = false;

    //Death stuff
    boolean dead = false;
    int deadFrames = 4;
    int frameLength = 3;
    int framesSinceChange = 0;
    int curFrame = 0;

    public Player(Context context)
    {
        mWidth = 200;
        mHeight = 200;

        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);

        mLocation = new Vector2d(50, World.getInstance().getGround().getY() - mImage.getHeight() - 10);
        mBoundingBox = new BoundingBox(context, mWidth, mHeight, mLocation);
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
        if (!dead && mCanSlide)
        {
            mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_slide);
            mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);
            mCanSlide = false;
        }

    }

    public void shoot()
    {
        if (!dead && mCanShoot)
        {
            bullet = new Bullet(mLocation.getX() + mWidth / 2, mLocation.getY() + mHeight / 2, !mCanSlide);
            mCanShoot = false;
            World.getInstance().bulletsound();
        }
    }

    private void killPlayer()
    {
        if (curFrame < deadFrames)
        {
            if (framesSinceChange >= frameLength)
            {
                curFrame++;
                framesSinceChange = 0;
            }

            if (framesSinceChange == 0)
            {
                switch(curFrame)
                {
                    case 0: mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_dead_1);
                        break;
                    case 1: mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_dead_2);
                        break;
                    case 2: mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_dead_3);
                        break;
                    case 3: mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_dead_4);
                        break;
                }
                mLocation.set( World.getInstance().getWidth() /2 - mImage.getWidth(),  World.getInstance().getHeight()/2 - mImage.getHeight());
            }

            mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);

            framesSinceChange++;
        }
        else
        {
            curFrame = 0;
            framesSinceChange = 0;
        }
    }

    //Gameplay
    public void update()
    {
        if (!dead)
        {
            mFrameCounter++;

            if(mFrameCounter == 60)
            {
                mScore+=10;
                mFrameCounter = 0;
            }

            if (mCanSlide && (mFrameCounter == 20 || mFrameCounter == 40 || mFrameCounter == 60 || mFrameCounter == 0))
            {
                mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player_2);
                mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);
            }

            if (mCanSlide && (mFrameCounter == 10 || mFrameCounter == 30 || mFrameCounter == 50))
            {
                mImage = BitmapFactory.decodeResource(World.getInstance().getContext().getResources(), R.drawable.player);
                mScaledImage = Bitmap.createScaledBitmap(mImage, mWidth, mHeight, true);
            }

            for (int i = 0; i < World.getInstance().getNumVisibleObstacles(); i++)
            {
                if (mBoundingBox.checkCollision(World.getInstance().getVisibleObstacles().get(i).getBoundingBox()))
                {
                    collidedWithObstacle = true;
                    mVel = 0.0f;
                    mIsJumping = false;

                    if (!World.getInstance().getVisibleObstacles().get(i).isGood())
                    {
                        killPlayer();
                        dead = true;
                        World.getInstance().setSpeed(0);
                        World.getInstance().setPlayerDead(true, mScore);
                    }
                }
            }

            if ( !World.getInstance().getEnemies().isEmpty())
            {
                if (mBoundingBox.checkCollision(World.getInstance().getEnemies().get(0).getBoundingBox()))
                {
                    killPlayer();
                    dead = true;
                    World.getInstance().setSpeed(0);
                    World.getInstance().setPlayerDead(true, mScore);
                }
            }

            if (!collidedWithObstacle)
            {
                if((mIsJumping || getY() < World.getInstance().getGround().getY() - mHeight) && !mBoundingBox.checkCollision(World.getInstance().getGround().getBoundingBox()))
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
            }

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
                            mDrawStyle = bullet.wasSlideShot();
                            bullet = null;
                            World.getInstance().getEnemies().remove(0);
                            World.getInstance().enemyHitSound();

                            if (!mDrawStyle)
                                mScore += 50;
                            else
                                mScore += 80;
                        }
                    }
                }
            }

            mBoundingBox.updateBox(mLocation);
        }
        else
        {
            killPlayer();
        }
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(getImage(),
                mLocation.getX(),
                mLocation.getY(),
                paint);

        paint.setTextSize(100.0f);
        canvas.drawText("Score: " + mScore, 50.0f, 100.0f, paint);

        if (!dead)
        {
            if (bullet != null)
                bullet.draw(canvas, paint);

            if (mDrawStyle)
            {
                if (styleSize < 300.0f)
                {
                    paint.setTextSize(styleSize);

                    canvas.drawText("~STYLE POINTS~", World.getInstance().getWidth() / 4.0f - styleSize, World.getInstance().getHeight() / 1.50f, paint);

                    styleSize += 5.0f;
                }
                else
                {
                    mDrawStyle = false;
                    styleSize = 200.0f;
                }
            }

            collidedWithObstacle = false;
            goodCollision = false;
        }
        else
        {
            paint.setTextSize(300.0f);
            canvas.drawText("You Died...", 300.0f, World.getInstance().getHeight() / 2 + 300.0f, paint);
        }
    }
}
