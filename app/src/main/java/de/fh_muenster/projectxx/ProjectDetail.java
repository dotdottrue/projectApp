package de.fh_muenster.projectxx;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import de.fh_muenster.projectxx.Device.Contact_List;
import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.Interfaces.AsyncResponse;
import de.fh_muenster.projectxx.Tasks.ListAppointmentsTask;
import de.fh_muenster.projectxx.Tasks.ListDiscussionsTask;
import de.fh_muenster.projectxx.Tasks.RemoveAppointmentTask;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese klasse verwaltet und erzeugt die Activity ProjectDetail
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 04.05.15
 */
public class ProjectDetail extends ActionBarActivity implements AsyncResponse {
    private ArrayList<String> Themenlist = new ArrayList<String>();
    private ArrayList<DiscussionTO> forums = new ArrayList<DiscussionTO>();
    private ListView disc ;
    private ArrayAdapter<String> adapter;
    private ProjectTO project;
    private ListAppointmentsTask task2;

    @Override
    /**
     * Die Methode erzeugt alle relevanten instanzen zur Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        this.project = (ProjectTO)i.getSerializableExtra("project");
        setContentView(R.layout.activity_project_detail);
        firstSteps(project);
        TextView textViewToChange = (TextView) findViewById(R.id.txt_describe);
        textViewToChange.setText(project.getDescription());
    }


    @Override
    /**
     * Erzeugt das Activitymenü
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_detail, menu);
        return true;
    }

    @Override
    /**
     * Reagiert wenn ein Menüpunkt ausgewählt wird
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_newDesc:
                openNewDesc();
                return true;
            case R.id.addUser:
                openContacts();
                return true;
            case R.id.addEvent:
                openCalendar();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * öffnet die Activity zum neue Diskussion erstellen
     */
        private void openNewDesc(){
            Intent intent = new Intent(this, new_disc.class);
            intent.putExtra("project", this.project);
            startActivity(intent);
    }


    /**
     * öffnet eine Diskussion
     * @param forum
     */
    private void openForum(DiscussionTO forum) {
        Intent intent = new Intent(this, ForumPosts.class);
        intent.putExtra("forum",forum);
        startActivity(intent);
    }

    /**
     * Benutzerdifinierte Instanz erzeugung beim start der Activity
     * Baut unteranderem die Server verbindung auf
     * @param project
     */
    private void firstSteps(ProjectTO project){
        String myUserId = DeviceService.myPhoneNumber;
        ListDiscussionsTask task = new ListDiscussionsTask(getApplicationContext(),getApplication(),project,myUserId,this);
        task.delegate = this;
        task.execute(project);
        //Appointments abrufen
        task2 = new ListAppointmentsTask(this.project,getApplicationContext(),getApplication(),this);
        task2.execute("");
    }

    /**
     * Erzeugt eine Liste aller Diskussionen
     * @param discs
     */
    private void createDiscussenList(List<DiscussionTO> discs){
        for(DiscussionTO disc : discs) {
            Themenlist.add(disc.getTopic());
        }
    }

    /**
     * Öffnet die Kontakte um diese dem Projekt hizuzufügen
     */
    private void openContacts(){
        Intent intent = new Intent(this, Contact_List.class);
        intent.putExtra("project",this.project);
        startActivity(intent);

    }

    /**
     * öffnet den Kalender
     */
    private void openCalendar(){
        Intent intent = new Intent(this, Calendar.class);
        intent.putExtra("project",this.project);
        startActivity(intent);
    }

    @Override
    /**
     * Erstellt die Diskussionslist
     */
    public void processFinish(List<DiscussionTO> output) {
        ArrayList<String> test = new ArrayList<String>();
        for(DiscussionTO d : output){
            test.add(d.getTopic());
        }
        this.Themenlist = test;
    }

    @Override
    /**
     * Wird die Activity wieder aktiv muss diese vorher aktualisiert werden
     */
    public void onResume(){
        firstSteps(this.project);
        super.onResume();
    }

    @Override
    /**
     * Erstellt ein Context Menü zur Listview
     */
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu_projectdetail, menu);
    }

    @Override
    /**
     * Reagiert wenn das contextmenü zu einem Item aufgerufen wird
     */
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {

            case R.id.action_removeAppointment:
                deleteAppointment(this.task2.getAppointmentlist().get(info.position));

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * löscht den ausgewählten Termin
     * @param appointment
     */
    private void deleteAppointment(AppointmentTO appointment){
        RemoveAppointmentTask task3 = new RemoveAppointmentTask(getApplicationContext(),getApplication(),this.project,appointment);
        task3.execute(this.project);
        firstSteps(this.project);

    }

}
