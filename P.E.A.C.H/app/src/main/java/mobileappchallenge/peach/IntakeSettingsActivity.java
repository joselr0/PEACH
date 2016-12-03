package mobileappchallenge.peach;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IntakeSettingsActivity extends AppCompatActivity {


    EditText Carb,Fat,Protein,Sodium,Fiber;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mText = (TextView)findViewById(R.id.textView50);
        mText.setText(String.valueOf(""));

        Button gotoSetup = (Button) findViewById(R.id.savebutton);
        assert gotoSetup != null;
        gotoSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Carb   = (EditText)findViewById(R.id.Ecarb);
                Carb.getText().toString();
                int carb1 = Integer.parseInt(Carb.getText().toString());
                String Scarb = (String.valueOf(carb1));


                Fat   = (EditText)findViewById(R.id.Efat);
                Fat.getText().toString();
                int fat1 = Integer.parseInt(Fat.getText().toString());
                String Sfat = (String.valueOf(fat1));


                Protein   = (EditText)findViewById(R.id.Eprotein);
                Protein.getText().toString();
                int pro1 = Integer.parseInt(Protein.getText().toString());
                String Spro = (String.valueOf(pro1));

                Sodium   = (EditText)findViewById(R.id.Esodium);
                Sodium.getText().toString();
                int sod1 = Integer.parseInt(Sodium.getText().toString());
                String Ssod = (String.valueOf(sod1));

                Fiber   = (EditText)findViewById(R.id.Efiber);
                Fiber.getText().toString();
                int fib1 = Integer.parseInt(Fiber.getText().toString());
                String Sfib = (String.valueOf(fib1));

                int calories = (carb1*(4)) + (fat1*(9))+(pro1*(4));
                String C = (String.valueOf(calories));


                mText = (TextView)findViewById(R.id.textView50);
                mText.setText(String.valueOf(calories));



                buttonclick(C,Scarb,Ssod,Sfat, Spro,Sfib);

            }
        });



    }

    void buttonclick(String cal,String carb,String sodium,String fat,String protein, String fiber)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
       databaseAccess.UpdateIntake(cal, carb, sodium, fat, protein, fiber);
        databaseAccess.close();


    }


}
