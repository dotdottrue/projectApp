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
import android.widget.ListView;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import java.util.List;
import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.Tasks.ListProjectTask;
import de.fh_muenster.projectxx.Tasks.RegisterTask;
import de.fh_muenster.projectxx.Tasks.RemoveMemberTask;
import de.project.dto.project.ProjectTO;

/**
 * Diese klasse verwaltet und erzeugt die Activity ListProjects
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 08.05.15
 */
public class List_Projects extends ActionBarActivity {
    private ArrayList<Project> projectList = new ArrayList<Project>();
    private ArrayList<String> projectListNames = new ArrayList<String>();
    private SoapObject test;
    private ListView listView ;
    private String myUserId;
    private ProjectTO selectedProject;
    private ListProjectTask task2;

    @Override
    /**
     * Die Methode erzeugt alle relevanten instanzen zur Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceService.getMyPhonenumber(getApplicationContext());
        DeviceService.myContacts = DeviceService.getMyContacts(getContentResolver());
        setContentView(R.layout.activity_list__projects);
        listView = (ListView) findViewById(R.id.listView);
        try{
            firstSteps();
        }
        catch (Exception e) {

        }
    }

    @Override
    /**
     * Erzeugt das Activitymenü
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list__projects, menu);
        return super.onCreateOptionsMenu(menu);
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
        switch (item.getItemId()) {
            case R.id.action_addProject:
                openNewProject();
                this.finish();
                return true;
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Öffnet die Activity zum neuen Projekt erstellen
     */
    private void openNewProject(){
        Intent intent = new Intent(this, Add_Project.class);
        startActivity(intent);
    }

    /**
     * Speichert den aktuellen status der Activity
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final ListView listBox =
                (ListView) findViewById(R.id.listView);
        CharSequence userText = listBox.getContentDescription();
        outState.putCharSequence("savedText", userText);
    }

    /**
     * Stellt den Zustand der Activity wieder her
     * @param savedState
     */
    protected void onRestoreInstanceState(Bundle savedState) {
        final ListView textBox =
                (ListView) findViewById(R.id.listView);
        CharSequence userText =
                savedState.getCharSequence("savedText");
        textBox.setContentDescription(userText);
    }

    /**
     * Öffnet das ausgewählte Projekt
     * @param project
     */
    private void openProject(Project project) {
        Intent intent = new Intent(this, ProjectDetail.class);
        intent.putExtra("project", project);
        startActivity(intent);
    }

    /**
     * Gibt das gesuchte Projekt zurück
     * @param id
     * @return
     */
    public Project getMyProject(int id) {
        return projectList.get(id);
    }

    /**
     * Erzeugt benutzer definierte Instanzen beim start der Activity
     * Und holt die Daten vom Server ab
     * @throws SoapFault
     */
    private void firstSteps() throws SoapFault {
        this.myUserId = DeviceService.getMyPhonenumber(getApplicationContext());
        RegisterTask task = new RegisterTask(getApplicationContext(),myUserId,this.getApplication());
        String result = "";
        task.execute(myUserId,result);
        try{
            //Prjektliste abfragen
            this.task2 = new ListProjectTask(getApplicationContext(),getApplication(),myUserId,this);
            List<ProjectTO> projects = (List<ProjectTO>)task2.execute(myUserId);
            createProjectList(projects);
        }
        catch (NullPointerException e){
            System.out.println("Du hast derzeit keine Projekte");
        }
    }

    /**
     * Erzeugt eine Projektliste für die Listview
     * @param projects
     */
    private void createProjectList(List<ProjectTO> projects){
        for(ProjectTO project : projects) {
            projectListNames.add(project.getProjectName());
        }
        listView.invalidateViews();
    }

    @Override
    /**
     * Erzeugt ein Contextmenü
     */
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu_list_projects, menu);
    }

    @Override
    /**
     * Ruft das Contextmenü zum Listview Item auf
     */
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_updateProject:
                openUpdateProject(task2.getProjectTOList().get(info.position));
                return true;
            case R.id.action_removeMember:
                try {
                    removeMember(task2.getProjectTOList().get(info.position));
                } catch (SoapFault soapFault) {
                    soapFault.printStackTrace();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Dient zum austreten aus dem Projekt
     * @param project
     * @throws SoapFault
     */
    private void removeMember(ProjectTO project) throws SoapFault {
        RemoveMemberTask task = new RemoveMemberTask(getApplicationContext(),getApplication(),project,this.myUserId);
        task.execute(project);
        giveProjects();
    }

    /**
     * Holt sich die Projekte vom Server
     */
    public void giveProjects(){
        try{
            //Prjektliste abfragen
            ListProjectTask task3 = new ListProjectTask(getApplicationContext(),getApplication(),myUserId,this);
            task3.execute(myUserId);
        }
        catch (NullPointerException e){
            System.out.println("Du hast derzeit keine Projekte");
        }
    }

    /**
     * Öffnet die Activity zum Projekt ändern
     * @param project
     */
    private void openUpdateProject(ProjectTO project){
        Intent intent = new Intent(this, UpdateProject.class);
        intent.putExtra("project", project);
        startActivity(intent);
        finish();
    }
}
