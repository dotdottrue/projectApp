package de.fh_muenster.projectxx.Tasks;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import de.fh_muenster.projectxx.ForumPosts;
import de.fh_muenster.projectxx.Interfaces.AsyncResponse;
import de.fh_muenster.projectxx.ProjectDetail;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.ForumService;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse verwaltet das abrufen der Diskussionen eines Projektes
 * @author Dennis Russ
 * @version 1.0 Erstellt am 16.06.15
 */
public class ListDiscussionsTask extends AsyncTask<ProjectTO,String,List<DiscussionTO>> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private String userid;
    private ArrayList<String> discussionNames = new ArrayList<String>();
    private ProjectDetail activity;
    private ListView disc;
    public AsyncResponse delegate = null;

    /**
     * Default konstruktor
     * @param c aktueller Context
     * @param a aktuelle Application
     * @param p aktuelles Project
     * @param u meine Telefonnummer
     * @param act   aktuelle activity
     */
    public ListDiscussionsTask(Context c, Application a, ProjectTO p, String u, ProjectDetail act){
        this.context = c;
        this.app = a;
        this.project = p;
        this.userid = u;
        this.activity = act;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected List<DiscussionTO> doInBackground(ProjectTO... params) {
        try {
            List<DiscussionTO> disList = null;
            disList = (List<DiscussionTO>)ForumService.getForumList(project,userid);
            return disList;
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
     * Im Anschluss werden die Listen für das ListView erzeugt
     * Danach wird das Listview dynamisch gefüllt.
     * @param result
     */
    protected void onPostExecute(final List<DiscussionTO> result) {
        delegate.processFinish(result);
        disc = (ListView)this.activity.findViewById(R.id.lv_disc);
        this.disc.invalidateViews();

        if(result == null) {
            //Prüfung ob die Liste <Friendship> bereits gefüllt ist, ansonsten Ausgabe des Toasts
            Toast.makeText(app.getApplicationContext(), "Du hast derzeit keine Diskussionen", Toast.LENGTH_SHORT).show();
        }
        else {
            createDiscussenList(result);
            //Layout anhand der ID suchen und in Variable speichern
            RelativeLayout ll = (RelativeLayout) this.activity.findViewById(R.id.ProjectDetail);
            //Listview setzen
            disc = (ListView)this.activity.findViewById(R.id.lv_disc);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    R.layout.listitemdetails, R.id.listViewDetailItem, discussionNames);
            // Assign adapter to ListView
            disc.setAdapter(adapter);
            // ListView Item Click Listener
            disc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // ListView Clicked item index
                    int itemPosition = position;
                    // ListView Clicked item value
                    String itemValue = (String) disc.getItemAtPosition(position).toString();
                    // Show Alert
                    Toast.makeText(context,
                            "Diskussion wird geöffnet", Toast.LENGTH_LONG)
                            .show();
                    //start Detail Activity
                    DiscussionTO discussion = result.get(itemPosition);
                    openDiscussion(discussion);
                }
            });
        }
    }

    /**
     * Diese Methode öffnet eine ausgewählte Diskussion
     * @param forum
     */
    private void openDiscussion(DiscussionTO forum) {
        Intent intent = new Intent(activity, ForumPosts.class);
        intent.putExtra("forum",forum);
        activity.startActivity(intent);
    }

    /**
     * Diese Methode erzeugt eine ArrayList mit den Topics aller Diskussionen
     * @param discs
     */
    private void createDiscussenList(List<DiscussionTO> discs){
        for(DiscussionTO disc : discs) {
            discussionNames.add(disc.getTopic());
        }
    }
}
