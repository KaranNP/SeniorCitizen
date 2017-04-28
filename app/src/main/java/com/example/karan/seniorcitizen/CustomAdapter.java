package com.example.karan.seniorcitizen;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter
{
    private final Context context;
    private final String[] values;
    private int i=1;

    public CustomAdapter (Context context, String[] values)
    {
        super(context, R.layout.activity_main, values);
        this.context = context;
        this.values = values;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.i("CA->getView","getView called");
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.heading);
        ImageView imageView = (ImageView) view.findViewById(R.id.image1);
        i=position+1;


        String s = values[position];
        textView.setText(s);
        Log.i("CA->getView","before image name");


        String imageName = "image"+i;
        i++;



        int resID = context.getResources().getIdentifier(imageName, "drawable", "com.example.karan.seniorcitizen");

        imageView.setImageResource(resID);
        imageView.setVisibility(View.VISIBLE);

        Log.i("CA->getView",""+s+":"+position); // Position starts from 0.


       /* if (s.equalsIgnoreCase("james"))
        {
            imageView.setImageResource(R.drawable.james);
        }
        else if (s.equalsIgnoreCase("bjarne"))
        {
            imageView.setImageResource(R.drawable.bjarne);
        }
        else if (s.equalsIgnoreCase("denish"))
        {
            imageView.setImageResource(R.drawable.denish);
        }
        else
        {
            imageView.setImageResource(R.drawable.mark);
        }
*/
        return view;
    }
}
