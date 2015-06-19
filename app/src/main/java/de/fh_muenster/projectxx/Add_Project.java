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

import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.Tasks.NewProjectTask;
import de.project.dto.project.ProjectTO;


public class Add_Project extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "cde.fh_muenster.projectxx.MESSAGE";
    private EditText nameTxt, describeTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__project);

        nameTxt = (EditText) findViewById(R.id.PnameTxt);
        describeTxt = (EditText) findViewById(R.id.PdescribeTxt);
        final Button addBtn = (Button) findViewById(R.id.btnAdd);

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Projekt wurde erstellt", Toast.LENGTH_SHORT).show();
                Project project = new Project(nameTxt.getText().toString(), describeTxt.getText().toString());
                createProject(project);

                finish();


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__project, menu);
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

    private void createProject(Project p){
        NewProjectTask task = new NewProjectTask(getApplicationContext(),getApplication(),p, DeviceService.getMyPhonenumber(getApplicationContext()));
        task.execute(p);
        Intent i = new Intent(this,List_Projects.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed()
    {

        Intent iii = new Intent(this,List_Projects.class);
        startActivity(iii);
        super.onBackPressed();

    }


}
