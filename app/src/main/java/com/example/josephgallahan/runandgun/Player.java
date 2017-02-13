package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by joseph.gallahan on 2/8/2017.
 */
public class Player
{
    //Dimensions & Collision
    float mWidth, mHeight;
    Bitmap mImage;

   // BoundingBox mBoundingBox;

    //Movement
    Vector2d mLocation;
    float mSpeedY, mGravity;

    //Actions
    Boolean mCanJump, mCanShoot, mCanSlide;
    float mShootCooldown;

    //Other
    int mScore;

    public Player(Context context)
    {
        mLocation = new Vector2d(50,50);

        mImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
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

    //Gameplay
    public void update()
    {

    }
}
