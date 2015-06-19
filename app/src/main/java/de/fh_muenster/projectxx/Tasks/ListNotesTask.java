package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.fh_muenster.projectxx.Adapter.PostArrayAdapter;
import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.ForumPosts;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.NoteService;
import de.project.dto.note.NoteTO;
import de.project.dto.discussion.DiscussionTO;

/**
 * Diese Klasse verwaltet das abrufen der Posts einer Diskussion
 * @author Dennis Russ
 * @version 1.0 Erstellt am 17.06.15
 */
public class ListNotesTask extends AsyncTask<DiscussionTO,String,List<NoteTO>> {

    private DiscussionTO discussion;
    private Context context;
    private Application app;
    private String userid;
    private ForumPosts activity;
    private HashMap<String,String> contacts;

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param a Aktuelle Application
     * @param disc  Aktuelle Diskussion
     * @param userid    eigene Telefonnummer
     * @param activity  Aktuelle Activity
     */
    public ListNotesTask(Context c, Application a, DiscussionTO disc, String userid, ForumPosts activity){
        this.context = c;
        this.app = a;
        this.discussion = disc;
        this.userid = userid;
        this.activity = activity;
        this.contacts = DeviceService.myContacts;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected List<NoteTO> doInBackground(DiscussionTO... params) {
        try {
            List<NoteTO> notes = null;
            notes = (List<NoteTO>)NoteService.getPosts(this.discussion,this.userid);
            return notes;
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
     * Im Anschluss der Servicemethoden werden hier die Ergebnisse aufbereitet
     * Im Anschluss werden die Listen für das ListView erzeugt
     * Danach wird das Listview dynamisch gefüllt.
     * @param result
     */
    protected void onPostExecute(final List<NoteTO> result) {
        if(result == null) {
            //Prüfung ob die Liste <Friendship> bereits gefüllt ist, ansonsten Ausgabe des Toasts
            Toast.makeText(app.getApplicationContext(), "Du hast derzeit keine Notes", Toast.LENGTH_SHORT).show();
        }
        else {
            //Notes zur Anzeige auslesen
            ArrayList<String> noteNames = new ArrayList<String>();
            ArrayList<String> noteUsers = new ArrayList<String>();
            for(NoteTO note : result){
                noteNames.add(note.getNote());
                noteUsers.add(this.contacts.get(note.getUser().trim()));
            }
            // Get ListView object from xml
            ListView lvPost = (ListView) activity.findViewById(R.id.lvPosts);
            PostArrayAdapter adapter = new PostArrayAdapter(this.activity,noteNames,noteUsers);
            // Assign adapter to ListView
            lvPost.setAdapter(adapter);
        }
    }
}
