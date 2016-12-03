package mobileappchallenge.peach;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;


public class CalendarActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        if (calendarView != null) {
            calendarView.setOnDateChangeListener(new OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    Intent bookmark = new Intent(CalendarActivity.this, DailyMealsActivity.class);

                    bookmark.putExtra("isCalendar", true);
                    bookmark.putExtra("day",dayOfMonth);
                    bookmark.putExtra("year",year);
                    bookmark.putExtra("month",month);

                    startActivity(bookmark);

                }
            });
        }
    }



}