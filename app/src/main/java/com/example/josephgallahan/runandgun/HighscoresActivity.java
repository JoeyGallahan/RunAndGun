package com.example.josephgallahan.runandgun;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;

/**
 * Created by Joey on 4/24/2017.
 */

public class HighscoresActivity extends AppCompatActivity
{
    private Button mBackButton;
    private TextView mFirstPlace, mSecondPlace, mThirdPlace;

    private String mFileName = "highscores.txt";
    private String mAllScores = "";

    private ArrayList<Integer> mScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        mBackButton = (Button) findViewById(R.id.back_button);

        mBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = MenuActivity.newIntent(HighscoresActivity.this);
                startActivity(i);
            }
        });

        mFirstPlace = (TextView) findViewById(R.id.first_place);
        mSecondPlace = (TextView) findViewById(R.id.second_place);
        mThirdPlace = (TextView) findViewById(R.id.third_place);

        mScores = new ArrayList<>();

        loadScores();
    }

    public static Intent newIntent(Context packageContext)
    {
        Intent i = new Intent(packageContext, HighscoresActivity.class);
        return i;
    }

    private void loadScores()
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

        mAllScores = output;
    }
}