package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.List_Projects;
import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.ProjectDetail;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 16.06.15.
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


    public ListProjectTask(Context c, Application app, String u, List_Projects act){
        this.context = c;
        this.app = app;
        this.userid = u;
        this.activity = act;
    }

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

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute(final List<ProjectTO> result) {
        this.projectTOList = result;
        System.out.println("Project onPostExecute running");
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_list_item_1, android.R.id.text1, projectListNames);

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
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();
                    //start Detail Activity
                    selectedProject = result.get(itemPosition);
                    ProjectTO project = result.get(itemPosition);
                    openProject(project);
                   
                }
            });

        }


    }

    private void openProject(ProjectTO project) {

        Intent intent = new Intent(this.app, ProjectDetail.class);
        intent.putExtra("project", project);
        activity.startActivity(intent);


    }

    private void createProjectList(List<ProjectTO> projects){
        for(ProjectTO project : projects) {
            projectListNames.add(project.getProjectName());


        }
        //listView.invalidateViews();
    }

    public List<ProjectTO> getProjectTOList(){
        return this.projectTOList;
    }

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
