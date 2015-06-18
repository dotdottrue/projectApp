package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.soap.NoteService;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.NoteTO;
import de.project.dto.discussion.DiscussionTO;

/**
 * Created by user on 18.06.15.
 */
public class AddUserToProjectTask extends AsyncTask<String,String,String> {
    private Context context;
    private Application app;
    private String phonenumber;
    private long projectid;


    public AddUserToProjectTask(Context c, Application a, String phonenumber, long projectid){
        this.context = c;
        this.app = a;
        this.projectid = projectid;
        this.phonenumber = phonenumber;
    }

    protected String doInBackground(String... params) {
        try {
            ProjectService.addUserToProject(this.phonenumber,this.projectid);
            return "User hinzugefügt!";
        }
        catch (Exception e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute() {

    }
}
