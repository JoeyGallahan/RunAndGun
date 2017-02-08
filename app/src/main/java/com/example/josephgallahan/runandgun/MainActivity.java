package com.example.josephgallahan.runandgun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private GameView mGameView;

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
