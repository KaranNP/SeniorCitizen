package com.example.karan.seniorcitizen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Screen2 extends AppCompatActivity
{
    private static final String TABLE_NAME = "InformationDB";

    DataBaseHelper dbh;
    Cursor c;

    TextView tv1,tv2;
    ImageView img_v;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        Intent i = getIntent();
        int pos = i.getIntExtra("id",-1);
        //showDB();

        tv1 = (TextView)findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView2);
        img_v = (ImageView)findViewById(R.id.imageView);

        c = dbh.cursor;
        setData();
        setImg();
    }

    /* Sets the heading and Content */
    public void setData()
    {
        String str1 = c.getString(1);
        String str2 = c.getString(4);

        tv1.setText(str1);
        tv2.setText(str2);
    }

    public void setImg()
    {
        int i = c.getInt(0);
        String imageName = "simage"+i;
        int resID = getResources().getIdentifier(imageName, "drawable", "com.example.karan.seniorcitizen");

        img_v.setImageResource(resID);
    }

    public void showDB()
    {
        Log.i("DisplayDB","showDB method called");

        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c1 = c;
        c1.moveToFirst();
        while(c1.moveToNext())
        {
            String s1=c1.getString(0);
            String s2=c1.getString(1);
            String s3=c1.getString(2);
            String s4=c1.getString(3);
            String s5=c1.getString(4);
            String fullRecord=s1+","+s2+","+s3+","+s4+","+s5;

            Log.i("Screen2DisplayDB",fullRecord);

        }
        /*----------*/
    }
}
