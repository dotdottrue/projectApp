package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import de.fh_muenster.projectxx.soap.NoteService;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.note.NoteTO;


/**
 * Diese Klasse verwaltet das erzeugen eines Posts zu einer Disksussion zum Server
 * @author Dennis Russ
 * @version 1.0 Erstellt am 17.06.15
 */
public class NewNoteTask extends AsyncTask<NoteTO,String,String> {
    private DiscussionTO discussion;
    private Context context;
    private Application app;
    private String userid;


    /**
     * Default konstruktor
     * @param c Aktueller Context
     * @param a Aktuelle Application
     * @param p Aktuelle Diskussion
     * @param u eigene Telefonnummer
     */
    public NewNoteTask(Context c, Application a, DiscussionTO p, String u){
        this.context = c;
        this.app = a;
        this.discussion = p;
        this.userid = u;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected String doInBackground(NoteTO... params) {
        try {
            NoteService.addPost(this.discussion,params[0],this.userid);
            return "Note hinzugefügt!";
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
