package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import de.fh_muenster.projectxx.soap.ProjectService;

/**
 * Dieser Taskmanager wird zum Useradden genutzt und erzeugt hierzu einen weiteren Thread
 * @author Dennis Russ
 * @version 1.0 Erstellt am 18.06.15
 */
public class AddUserToProjectTask extends AsyncTask<String,String,String> {
    /**
     * Dazu werden benötigt:
     * Die Telefonnummer des Kontakts
     * Die Projektid
     * Sowie der Activity Context und die Application an sich
     */
    private Context context;
    private Application app;
    private String phonenumber;
    private long projectid;

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param a Aktuelle Applikation
     * @param phonenumber
     * @param projectid
     */
    public AddUserToProjectTask(Context c, Application a, String phonenumber, long projectid){
        this.context = c;
        this.app = a;
        this.projectid = projectid;
        this.phonenumber = phonenumber;
    }

    /**
     * Erzeugt ein neues Thread und bereitet die Daten auf und stellt die Server verbindung her
     * @param params
     * @return
     */
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

    /**
     * Verwaltet änderungen während des Jobs
     * @param values
     */
    protected void onProgessUpdate(Integer... values) {
    }

    /**
     * Im Anschluss der Servicemethoden werden hier die Ergebnisse aufbereitet
     */
    protected void onPostExecute() {

    }
}
