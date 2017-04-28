package com.example.karan.seniorcitizen;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity
{
    private DataBaseHelper dataBaseHelper;
    String[] names = new String[]{"James","Bjarne","Denish","Mark"};
    int count=0;
    private static final String DB_NAME = "SeniorCitizen.db";  // Name also in Splash.java
    private static final String TABLE_NAME = "InformationDB";
    TextView tv_heading;

    Cursor main_cursor;

  /* Gets the dimensions of the screen size.*/
   /* public void setNumRows()
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dataBaseHelper = DataBaseHelper.getInstance(this, DB_NAME); //database is created.
        // Todo: Enable checkDatabse once application is completed and apply a version system.
        names = new String[getCount()];
        setNames();
       // testing();
        final String[] abc = names;
        CustomAdapter adapter = new CustomAdapter(this,abc);
        setListAdapter(adapter);

        tv_heading = (TextView)findViewById(R.id.heading);

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        //showDB();
        //testing();

        String str = (String) getListAdapter().getItem(position);
        int pos = position;
      //  Toast.makeText(this,str+" clicked at "+pos,Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,Screen2.class);
        setCursor(str);
        i.putExtra("id",pos); // not required
        startActivity(i);
    }

    public void setCursor(String str)
    {
        Cursor c = dataBaseHelper.cursor;
        c.moveToFirst();

        //select * from InformationDB where heading='Concession/facilities for senior citizens on telephone service';
        String query = "SELECT * FROM "+TABLE_NAME+" where heading='"+str+"';";
        Log.i("setCusor","query= "+query);
        c = DataBaseHelper.rawQuery(query);
        c.moveToNext();
        Log.i("setCusor","position of c="+c.getPosition());
    }

    public void testing()
    {
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c1 = DataBaseHelper.rawQuery(query);
        c1.moveToFirst();
        int a = c1.getCount();
        Log.i("Testing called","Number of rows ="+a);
    }

    /* returns the number of rows in the Table of the Database*/
    public int getCount()
    {

        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c1 = DataBaseHelper.rawQuery(query);
        c1.moveToFirst();
        int a = c1.getCount();
        //Log.i("getCount called","Number of rows ="+a);
        return a;
    }

    /* Displays the Table of DB in logcat */
    public void showDB()
    {
        Log.i("DisplayDB","showDB method called");

        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c1 = DataBaseHelper.rawQuery(query);
        c1.moveToFirst();
        while(c1.moveToNext())
        {
            String s1=c1.getString(0);
            String s2=c1.getString(1);
            String s3=c1.getString(2);
            String s4=c1.getString(3);
            String s5=c1.getString(4);
            String fullRecord=s1+","+s2+","+s3+","+s4+","+s5;

            Log.i("DisplayDB",fullRecord);

        }
        /*----------*/
    }

    /* Sets the heading in names[] variable. */
    public void setNames()
    {

        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c1 = DataBaseHelper.rawQuery(query);
        c1.moveToFirst();
        int i=0;
        for(i=0;i<names.length;i++)
        {
            String s1=c1.getString(1);
            Log.i("In setNames Method",i+" "+s1);
            if(s1!=null)
                names[i] = new String(s1);
            c1.moveToNext();
        }
        for(i=0;i<names.length;i++)
            Log.i("Showing Names",names[i]);
        Log.i("In setNames Method","End of setNames()");
    }
}
