package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 19.06.15.
 */
public class UpdateProjectTask extends AsyncTask<ProjectTO,String,String> {
    private ProjectTO project;
    private Context context;
    private Application app;


    public UpdateProjectTask(Context c, Application a, ProjectTO p){
        this.context = c;
        this.app = a;
        this.project = p;

    }

    protected String doInBackground(ProjectTO... params) {
        try {
            ProjectService.updateProject(this.project);
            return "Das Projekt wurde geändert";
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
