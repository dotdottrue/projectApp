package de.fh_muenster.projectxx;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.Device.Contact_List;
import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.Interfaces.AsyncResponse;
import de.fh_muenster.projectxx.Tasks.ListDiscussionsTask;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;


public class ProjectDetail extends ActionBarActivity implements AsyncResponse {
    private ArrayList<String> Themenlist = new ArrayList<String>();
    private ArrayList<DiscussionTO> forums = new ArrayList<DiscussionTO>();
    private ListView disc ;
    private ArrayAdapter<String> adapter;
    private ProjectTO project;


    @Override
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_detail, menu);
        return true;
    }

    @Override
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


        private void openNewDesc(){
            Intent intent = new Intent(this, new_disc.class);
            intent.putExtra("project", this.project);
            startActivity(intent);
    }


    private void openForum(DiscussionTO forum) {
        Intent intent = new Intent(this, ForumPosts.class);
        intent.putExtra("forum",forum);
        startActivity(intent);
    }

    private void firstSteps(ProjectTO project){
        String myUserId = DeviceService.getMyPhonenumber(getApplicationContext());
        ListDiscussionsTask task = new ListDiscussionsTask(getApplicationContext(),getApplication(),project,myUserId,this);
        task.delegate = this;
        task.execute(project);




    }

    private void createDiscussenList(List<DiscussionTO> discs){
        for(DiscussionTO disc : discs) {
            Themenlist.add(disc.getTopic());

        }
    }

    private void openContacts(){
        Intent intent = new Intent(this, Contact_List.class);
        startActivity(intent);
    }

    private void openCalendar(){
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(List<DiscussionTO> output) {
        //forums = (ArrayList<DiscussionTO>)output;
        //createDiscussenList(output);
        //disc.invalidateViews();
        ArrayList<String> test = new ArrayList<String>();
        for(DiscussionTO d : output){
            System.out.println("Topic1337: " +d.getTopic());
            test.add(d.getTopic());
        }
        for(String s : test) {
            System.out.println("Themenlist: " + s);
        }
        this.Themenlist = test;

    }

}
