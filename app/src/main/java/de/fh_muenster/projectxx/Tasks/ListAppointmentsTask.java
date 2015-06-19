package de.fh_muenster.projectxx.Tasks;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.fh_muenster.projectxx.Adapter.AppointmentArrayAdapter;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.AppointmentService;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse verwaltet das abrufen der Termine eines Projektes
 * @author Dennis Russ
 * @version 1.0 Erstellt am 18.06.15
 */
public class ListAppointmentsTask extends AsyncTask<String,String,List<AppointmentTO>> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private ListView appList;
    private Activity activity;
    private ArrayList<String> appointmentTopicList = new ArrayList<String>();
    private ArrayList<String> appointmentDates = new ArrayList<String>();
    private List<AppointmentTO> appointments = new ArrayList<AppointmentTO>();
    static final long ONE_HOUR = 60 * 60 * 1000L;
    public AppointmentTO selectedAppointment;

    /**
     * Default Konstruktor
     * @param project   aktuelles Projekt
     * @param con   Aktueller Context
     * @param ap    Aktuelle Applikation
     * @param activity Aktuelle Activity
     */
    public  ListAppointmentsTask (ProjectTO project, Context con, Application ap,Activity activity){
        this.project = project;
        this.context = con;
        this.app = ap;
        this.activity = activity;

    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
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
     * @param result    List mit Appointments
     */
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
            AppointmentArrayAdapter adapter = new AppointmentArrayAdapter(this.activity,this.appointmentTopicList,this.appointmentDates);
            activity.registerForContextMenu(appList);
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
                            appointments.get(itemPosition).getDescription() , Toast.LENGTH_LONG)
                            .show();
                    selectedAppointment = appointments.get(itemPosition);
                }
            });
        }
    }

    /**
     * Diese Methode erzeugt die Listen mit den Termintopics sowie eine Liste mit dem Fälligkeitsdatum
     * @param result
     */
    private void createAppointmentList(List<AppointmentTO> result){
        this.appointments = result;
        for(AppointmentTO a : result){
            this.appointmentTopicList.add(a.getTopic());
            Date appointmentDate = new Date();
            appointmentDate.setTime(a.getAppointmentDate());
            Date today = new Date();
            long days = daysBetween(today,appointmentDate);
            if(days <= 0)
            {
                this.appointmentDates.add("Heute fällig");
            }
            else if(days == 1)
            {
                this.appointmentDates.add("Morgen fällig");
            }
            else
            {
                this.appointmentDates.add("Fällig in: " + days + " Tagen");
            }
        }
    }

    /**
     * Berechnet die differenz zwischen zwei Datums angaben
     * @param d1 Heutiges Datum
     * @param d2    Termin Datum
     * @return
     */
    public long daysBetween(Date d1, Date d2){
        return ( (d2.getTime() - d1.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
    }

    /**
     * Um ein Context Menü auf ein Listview item aufzurufen muss die Appointment Liste an das Contextmenu geleitet
     * werden
     * @return
     */
    public List<AppointmentTO> getAppointmentlist(){
        return this.appointments;
    }
}
