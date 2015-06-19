package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.soap.ProjectService;

/**
 * Diese Klasse verwaltet das erzeugen eines neuen Projektes zum Server
 * @author Dennis Russ
 * @version 1.0 Erstellt am 16.05.15
 */
public class NewProjectTask extends AsyncTask<Project,String,String> {
    private Project project;
    private Context context;
    private Application app;
    private String userid;

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param a Aktuelle Application
     * @param p Aktuelles Project
     * @param u eigene Rufnummer
     */
    public NewProjectTask(Context c, Application a, Project p, String u){
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
