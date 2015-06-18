package de.fh_muenster.projectxx;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import java.util.Date;

import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;


public class Calendar extends ActionBarActivity {

    private CalendarView calendar;
    private AppointmentTO ap;
    private ProjectTO project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent i = getIntent();
        this.project = (ProjectTO)i.getSerializableExtra("project");
        initializeCalendar();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.action_settings:

                return true;
            case R.id.action_newEventCalendar:
                addAppointment();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(21)
    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                Date date = new Date(year-1900,month,day);
                System.out.println("Jahr: " +year);
                System.out.println("Was ein Termin! "+date.toString());
                ap = new AppointmentTO();
                ap.setAppointmentDate(date.getTime());



            }
        });
    }


    public void addAppointment(){
        Intent intent = new Intent(this, add_appointment.class);
        intent.putExtra("appointment", this.ap);
        intent.putExtra("project",this.project);
        startActivity(intent);

    }


}

