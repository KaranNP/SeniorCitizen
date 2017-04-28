package com.example.karan.seniorcitizen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

/* Not really a Splash Activity Anymore */
/* This page will only execute when you run it for the first time */
public class Splash extends AppCompatActivity
{
    SharedPreferences prefs = null;
    private static final String DB_NAME = "SeniorCitizen.db";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i("Splash","In onCreate method");

        prefs = getSharedPreferences("com.example.karan.seniorcitizen", MODE_PRIVATE);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i("Splash","In onResume method");
        if (prefs.getBoolean("firstrun", true))
        {
            Log.i("Splash","In firstrun block");
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            firstRun();

            prefs.edit().putBoolean("firstrun", false).commit();

        }
        else
        {
            Log.i("Splash","If firstrun = false");
            /* Apply this code if you don't want to see the Entry message every time the user opens the app.*/
            /*Intent i = new Intent(this,MainActivity.class);
            startActivity(i);*/
        }
    }

    public void firstRun()
    {
        deleteDB();
    }

    public void deleteDB()
    {
        Log.i("Splash","In deleteDB() method");
        File f = new File("/data/data/" + this.getPackageName()+ "/databases/"+DB_NAME);
        String str = "/data/data/" + this.getPackageName()+ "/databases/"+DB_NAME;
        Log.i("Splash","File = "+str);
        if (f.exists())
        {
            f.delete();
            Log.i("Splash","File = "+str+" deleted");
        }

    }

    public void onPress(View v)
    {
        Toast.makeText(this,"On press method called",Toast.LENGTH_SHORT);
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}