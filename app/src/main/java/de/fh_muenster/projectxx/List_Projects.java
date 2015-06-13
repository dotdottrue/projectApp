package de.fh_muenster.projectxx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_muenster.projectxx.soap.ContactService;


public class List_Projects extends ActionBarActivity {

    private ArrayList<Project> projectList = new ArrayList<Project>();
    private ArrayList<String> projectListNames = new ArrayList<String>();

    private ListView listView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list__projects);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, projectListNames);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position).toString();

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
                //start Detail Activity
                Project project = projectList.get(itemPosition);
                openProject(project);


            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list__projects, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_addProject:
                openNewProject();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    private void openNewProject(){


        Intent intent = new Intent(this, Add_Project.class);
        startActivityForResult(intent,1);



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String pName=data.getStringExtra("pName");
                String pDesc=data.getStringExtra("pDesc");
                Project project = new Project(pName, pDesc);
                projectList.add(project);
                projectListNames.add(project.getProjectname());
                listView.invalidateViews();


            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        final ListView listBox =
                (ListView) findViewById(R.id.listView);
        CharSequence userText = listBox.getContentDescription();
        outState.putCharSequence("savedText", userText);

    }

    protected void onRestoreInstanceState(Bundle savedState) {


        final ListView textBox =
                (ListView) findViewById(R.id.listView);

        CharSequence userText =
                savedState.getCharSequence("savedText");

        textBox.setContentDescription(userText);
    }

    private void openProject(Project project) {

        Intent intent = new Intent(this, ProjectDetail.class);
        intent.putExtra("project", project);
        startActivity(intent);
    }

    public Project getMyProject(int id) {
        return projectList.get(id);
    }

    private void firstSteps(){

    }

}
