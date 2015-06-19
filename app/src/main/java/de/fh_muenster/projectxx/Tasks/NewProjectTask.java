package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.soap.ContactService;
import de.fh_muenster.projectxx.soap.ProjectService;

/**
 * Created by user on 16.06.15.
 */
public class NewProjectTask extends AsyncTask<Project,String,String> {
    private Project project;
    private Context context;
    private Application app;
    private String userid;

    public NewProjectTask(Context c, Application a, Project p, String u){
        this.context = c;
        this.app = a;
        this.project = p;
        this.userid = u;
    }

    protected String doInBackground(Project... params) {
        try {
            ProjectService.addProject(this.project,this.userid);
            return "Projekt hinzugefügt!";
        }
        catch (SoapFault e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute() {


    }
}
