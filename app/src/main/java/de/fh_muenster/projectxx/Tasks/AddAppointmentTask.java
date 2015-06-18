package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import de.fh_muenster.projectxx.soap.AppointmentService;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 18.06.15.
 */
public class AddAppointmentTask extends AsyncTask<String,String,String> {
    private AppointmentTO ap;
    private ProjectTO project;
    private Context context;
    private Application app;

    public  AddAppointmentTask (AppointmentTO app, ProjectTO project, Context con, Application ap){
        this.ap = app;
        this.project = project;
        this.context = con;
        this.app = ap;

    }

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

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute() {

    }
}
