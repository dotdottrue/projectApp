package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.ksoap2.SoapFault;
import de.fh_muenster.projectxx.soap.AppointmentService;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse verwaltet das löschen von Terminen zum Server
 * @author Dennis Russ
 * @version 1.0 Erstellt am 19.06.15
 */
public class RemoveAppointmentTask extends AsyncTask<ProjectTO,String,String> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private AppointmentTO appointment;

    /**
     * Default Konstruktor
     * @param c Aktueller Context
     * @param a Aktuelle Application
     * @param p Aktuelles Projekt
     * @param ap    Aktuelle Activity
     */
    public RemoveAppointmentTask(Context c, Application a, ProjectTO p, AppointmentTO ap){
        this.context = c;
        this.app = a;
        this.project = p;
        this.appointment = ap;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params
     * @return
     */
    protected String doInBackground(ProjectTO... params) {
        try {
            AppointmentService.deleteAppointment(this.project, this.appointment);
            return "Appointment wurde gelöscht";
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
