package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import de.fh_muenster.projectxx.soap.ForumService;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse verwaltet erzeugen einer neues Disksussion zum Server
 * @author Dennis Russ
 * @version 1.0 Erstellt am 17.06.15
 */
public class NewDiscussionTask extends AsyncTask<DiscussionTO,String,String> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private String userid;

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param a Aktuelle Applikation
     * @param p Aktuelles Projekt
     * @param u Eigene Telefonnummer
     */
    public NewDiscussionTask(Context c, Application a, ProjectTO p, String u){
        this.context = c;
        this.app = a;
        this.project = p;
        this.userid = u;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected String doInBackground(DiscussionTO... params) {
        try {
            ForumService.addForum(params[0], this.userid,this.project);
            return "Discussion hinzugefügt!";
        }
        catch (SoapFault e) {
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
     * Verarbeitet die Resultate nach dem Job
     */
    protected void onPostExecute() {

    }
}
