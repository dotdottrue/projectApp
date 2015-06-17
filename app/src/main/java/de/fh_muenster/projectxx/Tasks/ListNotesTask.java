package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.ForumPosts;
import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.NoteService;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.NoteTO;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 17.06.15.
 */
public class ListNotesTask extends AsyncTask<DiscussionTO,String,List<NoteTO>> {

    private DiscussionTO discussion;
    private Context context;
    private Application app;
    private String userid;
    private ForumPosts activity;

    public ListNotesTask(Context c, Application a, DiscussionTO disc, String userid, ForumPosts activity){
        this.context = c;
        this.app = a;
        this.discussion = disc;
        this.userid = userid;
        this.activity = activity;
    }

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

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute(final List<NoteTO> result) {
        if(result == null) {
            //Prüfung ob die Liste <Friendship> bereits gefüllt ist, ansonsten Ausgabe des Toasts
            Toast.makeText(app.getApplicationContext(), "Du hast derzeit keine Notes", Toast.LENGTH_SHORT).show();

        }
        else {
            //Notes zur Anzeige auslesen
            ArrayList<String> noteNames = new ArrayList<String>();
            for(NoteTO note : result){
                noteNames.add(note.getNote());
            }

            // Get ListView object from xml
            ListView lvPost = (ListView) activity.findViewById(R.id.lvPosts);

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.activity,
                    android.R.layout.simple_list_item_1, android.R.id.text1, noteNames);

            // Assign adapter to ListView
            lvPost.setAdapter(adapter);

        }

    }
}
