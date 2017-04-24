package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Joey on 4/10/2017.
 */

public class MenuActivity extends AppCompatActivity
{
    private Button mPlayButton, mHighScoresButton, mQuitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.playButton);
        mHighScoresButton = (Button) findViewById(R.id.highScoresButton);
        mQuitButton = (Button) findViewById(R.id.quitButton);

        mPlayButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = MainActivity.newIntent(MenuActivity.this);
                startActivity(i);
            }
        });

        mHighScoresButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent i = QuizActivity.newIntent(MenuActivity.this, 2);
                //startActivity(i);
            }
        });

        mQuitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent i = QuizActivity.newIntent(MenuActivity.this, 3);
                //startActivity(i);
            }
        });
    }
}
