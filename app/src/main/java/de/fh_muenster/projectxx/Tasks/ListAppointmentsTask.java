package de.fh_muenster.projectxx.Tasks;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.AppointmentService;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 18.06.15.
 */
public class ListAppointmentsTask extends AsyncTask<String,String,List<AppointmentTO>> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private ListView appList;
    private Activity activity;
    private ArrayList<String> appointmentTopicList = new ArrayList<String>();

    public  ListAppointmentsTask (ProjectTO project, Context con, Application ap,Activity activity){
        this.project = project;
        this.context = con;
        this.app = ap;
        this.activity = activity;

    }

    protected List<AppointmentTO> doInBackground(String... params) {
        try {
            List<AppointmentTO> appointments = null;
            appointments = (List<AppointmentTO>) AppointmentService.listAppointments(this.project);
            return appointments;
        }
        catch (Exception e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute(List<AppointmentTO> result) {

        appList = (ListView)this.activity.findViewById(R.id.lv_disc);
        if(result == null) {
            //Prüfung ob die Liste <Friendship> bereits gefüllt ist, ansonsten Ausgabe des Toasts
            Toast.makeText(app.getApplicationContext(), "Du hast derzeit keine Diskussionen", Toast.LENGTH_SHORT).show();

        }
        else {

            createAppointmentList(result);

            //Listview setzen
            appList = (ListView)this.activity.findViewById(R.id.lv_appointment);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_list_item_1, android.R.id.text1, appointmentTopicList);

            // Assign adapter to ListView
            appList.setAdapter(adapter);

            // ListView Item Click Listener
            appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) appList.getItemAtPosition(position).toString();

                    // Show Alert
                    Toast.makeText(context,
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();
                    //start Detail Activity

                }
            });


        }


    }

    private void createAppointmentList(List<AppointmentTO> result){
        for(AppointmentTO a : result){
            this.appointmentTopicList.add(a.getTopic());
        }

    }
}
