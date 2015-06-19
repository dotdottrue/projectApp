package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import de.fh_muenster.projectxx.soap.AppointmentService;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;

/**
 * Diese Klasse verwaltet das hinzufügen eines Termins
 * @author Dennis Russ
 * @version 1.0 Erstellt am 18.06.15
 */
public class AddAppointmentTask extends AsyncTask<String,String,String> {
    /**
     * Dazu werden benötigt:
     * Aktuelle AppointmentTo
     * Das zugehörige Project
     * Sowie der Activity Context und die Application an sich
     */
    private AppointmentTO ap;
    private ProjectTO project;
    private Context context;
    private Application app;

    /**
     * Default Konstruktor
     * @param app   Das erzeugt AppointmentTo
     * @param project   das Aktuelle Project
     * @param con   Aktueller Activity context
     * @param ap    Aktuelle Applikation
     */
    public  AddAppointmentTask (AppointmentTO app, ProjectTO project, Context con, Application ap){
        this.ap = app;
        this.project = project;
        this.context = con;
        this.app = ap;
    }

    /**
     * Diese Methode erzeugt einen neuen Thread zum abarbeiten der Datenaufbereitung sowie Serververbindung
     * @param params    Kann String parameter entgegen nehmen
     * @return
     */
    protected String doInBackground(String... params) {
        try {
            AppointmentService.addAppointment(this.ap,this.project);
            return "Appointment hinzugefügt!";
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
     */
    protected void onPostExecute() {

    }
}
