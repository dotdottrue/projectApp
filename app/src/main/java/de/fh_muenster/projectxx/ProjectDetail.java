package de.fh_muenster.projectxx;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         Intent i = getIntent();
        ProjectTO project = (ProjectTO)i.getSerializableExtra("project");


        setContentView(R.layout.activity_project_detail);
        firstSteps(project);
        Themenlist.add("Das ist ein Test");
        TextView textViewToChange = (TextView) findViewById(R.id.txt_describe);
        textViewToChange.setText(project.getDescription());



        // Get ListView object from xml
        disc = (ListView) findViewById(R.id.lv_disc);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        /*
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Themenlist);

        // Assign adapter to ListView
        disc.setAdapter(adapter);

        // ListView Item Click Listener
        disc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String theme = (String) disc.getItemAtPosition(position).toString();
                String itemValue = theme;

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
                //start Detail Activity
                DiscussionTO forum = forums.get(itemPosition);
                openForum(forum);


            }

        });
        **/






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
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


        private void openNewDesc(){
        Intent intent = new Intent(this, new_disc.class);
        startActivityForResult(intent, 1);
    }
    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String fTitle=data.getStringExtra("fTitle");
                String fDesc=data.getStringExtra("fDesc");
                Forum forum = new Forum(fTitle, fDesc);
                Themenlist.add(forum.getTitle());
                forums.add(forum);
                disc.invalidateViews();



            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    **/

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
            System.out.println("createDiscussionList: " + disc.getTopic());


        }

        //listView.invalidateViews();
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
