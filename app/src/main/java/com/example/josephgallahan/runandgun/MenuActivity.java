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
                recreate();
                startActivity(i);
            }
        });

        mHighScoresButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = HighscoresActivity.newIntent(MenuActivity.this);
                startActivity(i);
            }
        });

        mQuitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.exit(0);
            }
        });
    }

    public static Intent newIntent(Context packageContext)
    {
        Intent i = new Intent(packageContext, MenuActivity.class);
        return i;
    }
}
