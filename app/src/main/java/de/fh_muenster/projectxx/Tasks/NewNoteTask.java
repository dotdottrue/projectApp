package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import de.fh_muenster.projectxx.soap.ForumService;
import de.fh_muenster.projectxx.soap.NoteService;

import de.project.dto.discussion.DiscussionTO;
import de.project.dto.note.NoteTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 17.06.15.
 */
public class NewNoteTask extends AsyncTask<NoteTO,String,String> {
    private DiscussionTO discussion;
    private Context context;
    private Application app;
    private String userid;


    public NewNoteTask(Context c, Application a, DiscussionTO p, String u){
        this.context = c;
        this.app = a;
        this.discussion = p;
        this.userid = u;
    }

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

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute() {

    }
}
