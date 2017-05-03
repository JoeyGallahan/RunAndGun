package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Joey on 4/24/2017.
 */

public class GameOverActivity extends AppCompatActivity {
    private Button mHomeButton;
    private TextView mFinalScore;

    private static final String SCORE = "com.example.josephgallahan.runandgun";
    private int mScore = 0;

    private String mFileName = "highscores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        mHomeButton = (Button) findViewById(R.id.home_button);
        mFinalScore = (TextView) findViewById(R.id.gameover_score);

        mHomeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = MenuActivity.newIntent(GameOverActivity.this);
                startActivity(i);
                finish();
            }
        });

        mScore = getIntent().getIntExtra(SCORE,1);

        mFinalScore.setText(Integer.toString(mScore));

        saveScore();
    }

    public static Intent newIntent(Context packageContext, int score)
    {
        Intent i = new Intent(packageContext, GameOverActivity.class);

        i.putExtra(SCORE, score);

        return i;
    }

    private void saveScore()
    {
        /*
         * File output taken from http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
         * Modified by Joey Gallahan on 04/24/17
         */

        try
        {
            OutputStreamWriter outputStream = new OutputStreamWriter(openFileOutput(mFileName, MODE_APPEND));
            String temp = String.valueOf(mScore) + ",";
            outputStream.append(temp);
            outputStream.close();

            sortHighscores();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void sortHighscores()
    {
        String output = "";

        /*
         * File input taken from http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
         * Modified by Joey Gallahan on 04/24/17
         */
        try
        {
            InputStream inputStream = openFileInput(mFileName);

            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                output = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("Exception", "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("Exception", "File read failed: " + e.toString());
        }

        String tempScore = "";
        ArrayList<Integer> highscores = new ArrayList<>();
        for (int i = 0; i < output.length(); i++)
        {
            if (output.charAt(i) != ',')
            {
                tempScore += output.charAt(i);
            }
            else
            {
                highscores.add(Integer.parseInt(tempScore));
                tempScore = "";
            }
        }

        Collections.sort(highscores);

        try
        {
            OutputStreamWriter outputStream = new OutputStreamWriter(openFileOutput(mFileName, 0));
            outputStream.flush();
            String temp = "";

            int size = 3;
            if (highscores.size() < 3)
            {
                size = highscores.size();
            }

            for (int i = 0; i < size; i++)
            {
                temp = highscores.get(i) + ",";
                outputStream.append(temp);
            }

            outputStream.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}