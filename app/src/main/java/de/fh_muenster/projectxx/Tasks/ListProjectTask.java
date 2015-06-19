package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import java.util.ArrayList;
import java.util.List;
import de.fh_muenster.projectxx.Adapter.ProjectListAdapter;
import de.fh_muenster.projectxx.List_Projects;
import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.ProjectDetail;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse verwaltet das abrufen der Projekte des Users
 * @author Dennis Russ
 * @version 1.0 Erstellt am 16.06.15
 */
public class ListProjectTask extends AsyncTask<String,String,List<ProjectTO>> {
    private String userid;
    private Context context;
    private Application app;
    private List_Projects activity;
    private ArrayList<Project> projectList = new ArrayList<Project>();
    private ArrayList<String> projectListNames = new ArrayList<String>();
    private ListView listView;
    public ProjectTO selectedProject;
    private List<ProjectTO> projectTOList = new ArrayList<ProjectTO>();
    private ArrayList<String> projectStatus = new ArrayList<String>();

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param app   Aktuelle Applikation
     * @param u Eigene Telefonnummer
     * @param act   Aktuelle Activity
     */
    public ListProjectTask(Context c, Application app, String u, List_Projects act){
        this.context = c;
        this.app = app;
        this.userid = u;
        this.activity = act;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected List<ProjectTO> doInBackground(String... params) {
        try {
            List<ProjectTO> projects = null;
            projects = (List<ProjectTO>)ProjectService.getProjectList(this.userid);
            return projects;
        }
        catch (SoapFault e){//SoapFault e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * Verwaltet änderungen während des Jobs
     * @param values
     */
    protected void onProgessUpdate(Integer... values) {
    }

    /**
     * Im Anschluss der Servicemethoden werden hier die Ergebnisse aufbereitet
     * Im Anschluss werden die Listen für das ListView erzeugt
     * Danach wird das Listview dynamisch gefüllt.
     * @param result
     */
    protected void onPostExecute(final List<ProjectTO> result) {
        this.projectTOList = result;
        if(result == null) {
            //Prüfung ob die Liste <Friendship> bereits gefüllt ist, ansonsten Ausgabe des Toasts
            Toast.makeText(app.getApplicationContext(), "Du hast derzeit keine Projekte", Toast.LENGTH_SHORT).show();
        }
        else {
            createProjectList(result);
            //Layout anhand der ID suchen und in Variable speichern
            RelativeLayout ll = (RelativeLayout) this.activity.findViewById(R.id.List_Projects);
            //Listview setzen
            listView = (ListView)this.activity.findViewById(R.id.listView);
            activity.registerForContextMenu(listView);
            ProjectListAdapter adapter = new ProjectListAdapter(this.activity,this.projectListNames,this.projectStatus);
            // Assign adapter to ListView
            listView.setAdapter(adapter);
            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) listView.getItemAtPosition(position).toString();

                    // Show Alert
                    Toast.makeText(context,
                            "Projekt wird geöffnet", Toast.LENGTH_LONG)
                            .show();
                    //start Detail Activity
                    selectedProject = result.get(itemPosition);
                    ProjectTO project = result.get(itemPosition);
                    openProject(project);
                }
            });
        }
    }

    /**
     * Ruft das ausgewählte Projekt auf
     * @param project
     */
    private void openProject(ProjectTO project) {
        Intent intent = new Intent(this.app, ProjectDetail.class);
        intent.putExtra("project", project);
        activity.startActivity(intent);
    }

    /**
     * Erzeugt eine Liste mit allen Projektnamen
     * @param projects
     */
    private void createProjectList(List<ProjectTO> projects){
        for(ProjectTO project : projects) {
            projectListNames.add(project.getProjectName());
            projectStatus.add("Status: " + project.getProjectStatus().toString());
        }
    }

    /**
     * Gibt die Projecte an das Contextmenu zurück
     * @return
     */
    public List<ProjectTO> getProjectTOList(){
        return this.projectTOList;
    }

    /**
     * Aktuallisiert die Projectlist vom Server
     */
    public void giveProjects(){
        try{
            //Prjektliste abfragen
            ListProjectTask task3 = new ListProjectTask(activity.getApplicationContext(),activity.getApplication(),this.userid,this.activity);
            task3.execute(this.userid);
        }
        catch (NullPointerException e){
            System.out.println("Keine Projects da");
        }
    }
}
