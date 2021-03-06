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
import de.fh_muenster.projectxx.Tasks.NewDiscussionTask;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;


public class new_disc extends ActionBarActivity {
    EditText titleTxt, describeTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_disc);
        Intent i = getIntent();
        final ProjectTO project = (ProjectTO)i.getSerializableExtra("project");

        titleTxt = (EditText) findViewById(R.id.edtQuest);
        describeTxt = (EditText) findViewById(R.id.edtPost);
        final Button addBtn = (Button) findViewById(R.id.btnAddDisc);

        titleTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!titleTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Diskussion wurde erstellt", Toast.LENGTH_SHORT).show();
                createDescussion(project);
                finish();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_disc, menu);
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

    private void createDescussion(ProjectTO project){
        String myUserId = DeviceService.getMyPhonenumber(getApplicationContext());
        NewDiscussionTask task = new NewDiscussionTask(getApplicationContext(),getApplication(),project,myUserId);
        DiscussionTO disc = new DiscussionTO();
        disc.setTopic(titleTxt.getText().toString());
        task.execute(disc);
    }

}
