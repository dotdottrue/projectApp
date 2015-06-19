package de.fh_muenster.projectxx.Tasks;

import android.app.Activity;
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

import org.ksoap2.SoapFault;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.Forum;
import de.fh_muenster.projectxx.ForumPosts;
import de.fh_muenster.projectxx.Interfaces.AsyncResponse;
import de.fh_muenster.projectxx.Project;
import de.fh_muenster.projectxx.ProjectDetail;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.ForumService;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 16.06.15.
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

    public ListDiscussionsTask(Context c, Application a, ProjectTO p, String u, ProjectDetail act){
        this.context = c;
        this.app = a;
        this.project = p;
        this.userid = u;
        this.activity = act;
    }

    protected List<DiscussionTO> doInBackground(ProjectTO... params) {
        try {
            List<DiscussionTO> disList = null;
            System.out.println("Vor Cast");
            disList = (List<DiscussionTO>)ForumService.getForumList(project,userid);

            System.out.println("Nach Cast");
            return disList;
        }
        catch (Exception e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        System.out.println("Es kommt null");
        return null;
    }

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute(final List<DiscussionTO> result) {
        System.out.println("in onPostExecute drinne alter");

        delegate.processFinish(result);
        System.out.println("on Pos - invalidate");
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
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();
                    //start Detail Activity
                    DiscussionTO discussion = result.get(itemPosition);
                    openDiscussion(discussion);
                }
            });


        }





    }

    private void openDiscussion(DiscussionTO forum) {
        Intent intent = new Intent(activity, ForumPosts.class);
        intent.putExtra("forum",forum);
        activity.startActivity(intent);
    }

    private void createDiscussenList(List<DiscussionTO> discs){
        for(DiscussionTO disc : discs) {
            discussionNames.add(disc.getTopic());


        }
        //disc.invalidateViews();
        //listView.invalidateViews();
    }


}
