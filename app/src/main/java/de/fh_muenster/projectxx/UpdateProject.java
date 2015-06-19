package de.fh_muenster.projectxx;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import de.fh_muenster.projectxx.Tasks.UpdateProjectTask;
import de.project.dto.project.ProjectTO;
import de.project.enumerations.ProjectStatus;

/**
 * Diese klasse verwaltet und erzeugt die Activity UpdateProject
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 19.06.15
 */
public class UpdateProject extends ActionBarActivity {
    private ProjectTO project;
    private TextView title;
    private TextView desc;
    private RadioButton b1;
    private RadioButton b2;
    private RadioButton b3;
    private RadioButton b4;

    @Override
    /**
     * Die Methode erzeugt alle relevanten instanzen zur Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        project = (ProjectTO)i.getSerializableExtra("project");
        setContentView(R.layout.activity_update_project);
        title = (TextView)findViewById(R.id.UpdatePnameTxt);
        desc = (TextView)findViewById(R.id.UpdatePdescribeTxt);
        title.setText(project.getProjectName());
        desc.setText(project.getDescription());
        b1 = (RadioButton)findViewById(R.id.radioButton);
        b2 = (RadioButton)findViewById(R.id.radioButton2);
        b3 = (RadioButton)findViewById(R.id.radioButton3);
        b4 = (RadioButton)findViewById(R.id.radioButton4);
        switch(project.getProjectStatus().toString()){
            case "INTIME":
                b2.setChecked(true);
                break;
            case "DELAYED":
                b1.setChecked(true);
                break;
            case "OUTOFTIME":
                b3.setChecked(true);
                break;
            default:
                b4.setChecked(true);
        }
        final Button addBtn = (Button) findViewById(R.id.btnUpdate);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Projekt wurde geändert", Toast.LENGTH_SHORT).show();
                updateProject();
            }
        });
    }


    @Override
    /**
     * Erzeugt das Activitymenü
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_project, menu);
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
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Bereitet die Daten auf um das Projekt zu aktualisieren
     */
    private void updateProject(){
        this.project.setProjectName(this.title.getText().toString());
        this.project.setDescription(this.desc.getText().toString());
        if(b1.isChecked()) this.project.setProjectStatus(ProjectStatus.DELAYED);
        if(b2.isChecked()) this.project.setProjectStatus(ProjectStatus.INTIME);
        if(b3.isChecked()) this.project.setProjectStatus(ProjectStatus.OUTOFTIME);
        if(b4.isChecked()) this.project.setProjectStatus(ProjectStatus.FINISHED);
        UpdateProjectTask task = new UpdateProjectTask(getApplicationContext(),getApplication(),this.project);
        task.execute();
        Intent ii = new Intent(this,List_Projects.class);
        startActivity(ii);
        finish();
    }
}
