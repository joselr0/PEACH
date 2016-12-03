package mobileappchallenge.peach;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


import mobileappchallenge.peach.FunctionClass;

public class ShoppingListActivity extends AppCompatActivity {
    ListView lv;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.IngrlistView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<IngrObject> quotes = databaseAccess.getQuotes();
        databaseAccess.close();

        lv.setAdapter(new CustomIngrAdapter(this, R.layout.ingredient_entry, quotes));
    }

}

