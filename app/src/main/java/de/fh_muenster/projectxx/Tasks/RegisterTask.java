package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import de.fh_muenster.projectxx.soap.ContactService;

/**
 * Diese Klasse verwaltet das Regestrieren zum Server
 * @author Dennis Russ
 * @version 1.0 Erstellt am 16.06.15
 */
public class RegisterTask extends AsyncTask<String,String,String> {
    private Context context;
    private String userid;
    private Application app;

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param uid   Eigene Rufnummer
     * @param a Aktuelle Application
     */
    public RegisterTask(Context c, String uid, Application a){
        this.context = c;
        this.userid = uid;
        this.app = a;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected String doInBackground(String... params) {
        try {
            SoapObject so = (SoapObject)ContactService.register(this.userid);
            return "Regestriert!";
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
