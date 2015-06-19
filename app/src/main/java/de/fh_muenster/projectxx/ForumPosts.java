package de.fh_muenster.projectxx;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.Tasks.ListNotesTask;
import de.fh_muenster.projectxx.Tasks.NewNoteTask;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.note.NoteTO;

/**
 * Diese klasse verwaltet und erzeugt die Activity ForumPosts
 * @author Niclas Christ
 * @author Dennis Russ
 * @version 1.0 Erstellt am 28.05.15
 */
public class ForumPosts extends ActionBarActivity {

    private ArrayList<String> sPosts = new ArrayList<String>();
    private ArrayList<Post> posts = new ArrayList<Post>();
    private ListView lvPost ;
    private EditText postMsg;
    private DiscussionTO forum;

    @Override
    /**
     * Die Methode erzeugt alle relevanten instanzen zur Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii = getIntent();
        this.forum = (DiscussionTO)ii.getSerializableExtra("forum");
        listNotes();
        setContentView(R.layout.activity_forum_posts);
        //Frage als überschrift drucken.
        TextView textViewToChange = (TextView) findViewById(R.id.Forumtitle);
        textViewToChange.setText(forum.getTopic());
        //Button hinzufügen und Logik hinterlegen
        postMsg = (EditText) findViewById(R.id.edtPostForum);
        final Button addBtn = (Button) findViewById(R.id.btnposten);
        postMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!postMsg.getText().toString().trim().isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Post wurde gespeichert", Toast.LENGTH_SHORT).show();
                createNote(postMsg.getText().toString());
                postMsg.setText("");
                invalidateNotes();

            }
        });
    }


    @Override
    /**
     * Erzeugt das Activitymenü
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum_posts, menu);
        return true;
    }

    @Override
    /**
     * Reagiert wenn ein Menüpunkt ausgewählt wird
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Aktualisiert die Posts nachdem eines gesendet wurde
     */
    private void invalidateNotes(){
        String userid = DeviceService.getMyPhonenumber(getApplicationContext());
        //NoteTask erzeugen und starten
        ListNotesTask task = new ListNotesTask(getApplicationContext(),getApplication(),this.forum,userid,this);
        task.execute(this.forum);
    }

    /**
     * Erzeugt ein neuen Post
     * @param post
     */
    private void createNote(String post){
        String userid = DeviceService.getMyPhonenumber(getApplicationContext());
        NoteTO note = new NoteTO();
        note.setNote(post);
        //NoteTask erzeugen und starten
        NewNoteTask task = new NewNoteTask(getApplicationContext(),getApplication(),this.forum,userid);
        task.execute(note);
        listNotes();
    }

    /**
     * Listet alle Posts zur Diskussion auf
     */
    private void listNotes(){
        String myUserId = DeviceService.getMyPhonenumber(getApplicationContext());
        ListNotesTask task = new ListNotesTask(getApplicationContext(),getApplication(),this.forum,myUserId,this);
        task.execute(this.forum);
    }
}
