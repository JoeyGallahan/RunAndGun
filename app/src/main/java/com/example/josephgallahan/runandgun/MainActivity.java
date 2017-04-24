package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private GameView mGameView;

    public static Intent newIntent(Context packageContext)
    {
        Intent i = new Intent(packageContext, MainActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mGameView = new GameView(this);
        setContentView(mGameView);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mGameView.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mGameView.resume();
    }
}
