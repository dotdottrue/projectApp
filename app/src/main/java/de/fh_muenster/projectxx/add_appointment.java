package de.fh_muenster.projectxx;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import de.fh_muenster.projectxx.Tasks.AddAppointmentTask;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;


public class add_appointment extends ActionBarActivity {
    EditText AppointmentNameTxt, AppointmentDescribeTxt;
    private AppointmentTO ap;
    private ProjectTO project;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        this.ap = (AppointmentTO) i.getSerializableExtra("appointment");
        this.project = (ProjectTO) i.getSerializableExtra("project");
        setContentView(R.layout.activity_add_appointment);


        AppointmentNameTxt = (EditText) findViewById(R.id.AppointmentNameTxt);
        AppointmentDescribeTxt = (EditText) findViewById(R.id.AppointmentDescribeTxt);
        final Button add_Appointment = (Button) findViewById(R.id.add_Appointment);

        AppointmentNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                add_Appointment.setEnabled(!AppointmentNameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add_Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Termin wurde erstellt", Toast.LENGTH_SHORT).show();
                ap.setTopic(AppointmentNameTxt.getText().toString());
                ap.setDescription(AppointmentDescribeTxt.getText().toString());
                addAppointment();

                finish();

            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAppointment(){
        AddAppointmentTask task = new AddAppointmentTask(this.ap,this.project,getApplicationContext(),getApplication());
        task.execute("");

    }
}
