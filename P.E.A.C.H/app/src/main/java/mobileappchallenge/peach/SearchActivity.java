package mobileappchallenge.peach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();

    ListView lv;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        final Button button = (Button) findViewById(R.id.searchBtn);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void searchButtonOnClick(View v) {
        // do something when the button is clicked

        EditText srchFilt = (EditText) findViewById(R.id.searchTxt);
        String filter = srchFilt.getText().toString();
        Spinner spinner1 = (Spinner) findViewById(R.id.Search_NutrientSpinner);
        String nutrient = spinner1.getSelectedItem().toString();
        Spinner spinner2 = (Spinner) findViewById(R.id.Search_AscDescSpinner);
        String ascdesc = spinner2.getSelectedItem().toString();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        final List<SearchObject> items = databaseAccess.getSearchRecipes(filter, nutrient, ascdesc);
        databaseAccess.close();

        lv = (ListView) findViewById(R.id.listViewSearch);
        lv.setAdapter(new CustomSearchAdapter(this, R.layout.search_entry, items));



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String item = items.get(position).name;
                Intent search = new Intent(SearchActivity.this, RecipeActivity.class);

                search.putExtra("name",item);
                search.putExtra("isSearch",true);
                startActivity(search);

            }
        });


    }
}
