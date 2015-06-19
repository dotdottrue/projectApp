package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import java.util.List;

import de.fh_muenster.projectxx.soap.AppointmentService;
import de.fh_muenster.projectxx.soap.ProjectService;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 19.06.15.
 */
public class RemoveAppointmentTask extends AsyncTask<ProjectTO,String,String> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private AppointmentTO appointment;

    public RemoveAppointmentTask(Context c, Application a, ProjectTO p, AppointmentTO ap){
        this.context = c;
        this.app = a;
        this.project = p;
        this.appointment = ap;
    }

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

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute() {

    }


}
