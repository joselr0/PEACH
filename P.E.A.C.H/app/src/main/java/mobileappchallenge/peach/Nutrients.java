package mobileappchallenge.peach;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

import mobileappchallenge.peach.FunctionClass;

public class Nutrients extends AppCompatActivity {
    TextView txt;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        value = databaseAccess.getConsumedCal();
        txt = (TextView) findViewById(R.id.cCal);
        txt.setText(value);

        value = databaseAccess.getTargetCal();
        txt = (TextView) findViewById(R.id.tCal);
        txt.setText(value);

        value = databaseAccess.getConsumedCarb();
        txt = (TextView) findViewById(R.id.cCarb);
        txt.setText(value);

        value = databaseAccess.getTargetCarb();
        txt = (TextView) findViewById(R.id.tCarb);
        txt.setText(value);

        value = databaseAccess.getConsumedPro();
        txt = (TextView) findViewById(R.id.cPro);
        txt.setText(value);

        value = databaseAccess.getTargetPro();
        txt = (TextView) findViewById(R.id.tPro);
        txt.setText(value);

        value = databaseAccess.getConsumedFib();
        txt = (TextView) findViewById(R.id.cFib);
        txt.setText(value);

        value = databaseAccess.getTargetFib();
        txt = (TextView) findViewById(R.id.tFib);
        txt.setText(value);

        value = databaseAccess.getConsumedFat();
        txt = (TextView) findViewById(R.id.cFat);
        txt.setText(value);

        value = databaseAccess.getTargetFat();
        txt = (TextView) findViewById(R.id.tFat);
        txt.setText(value);

        value = databaseAccess.getConsumedSod();
        txt = (TextView) findViewById(R.id.cSod);
        txt.setText(value);

        value = databaseAccess.getTargetSod();
        txt = (TextView) findViewById(R.id.tSod);
        txt.setText(value);




        databaseAccess.close();
    }

        /* Skeleton setup on adding button activity
    public void buttonOnClick(View v)
    {
        // do something when the button is clicked
        Button button=(Button) v;
        ((Button) v).setText("clicked");

    }

*/
}
