package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 18.06.15.
 */
public class RemoveMemberTask extends AsyncTask<ProjectTO,String,String> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private String userid;

    public RemoveMemberTask(Context c, Application a, ProjectTO p, String u){
        this.context = c;
        this.app = a;
        this.project = p;
        this.userid = u;
    }

    protected String doInBackground(ProjectTO... params) {
        try {
            ProjectService.removeMember(this.project,userid);
            return "Du bist raus!!";
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
