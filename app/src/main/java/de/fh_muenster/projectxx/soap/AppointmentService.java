package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Date;

import de.fh_muenster.projectxx.Appointment;
import de.project.dto.appointment.AppointmentTO;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;

/**
 * Created by user on 18.06.15.
 */
public class AppointmentService {

    public static ArrayList<AppointmentTO> listAppointments(ProjectTO project) throws SoapFault {
        long projectID = project.getId();
        String method = "getAppointmentsByProject";

        ArrayList<AppointmentTO> appointmentList = new ArrayList<AppointmentTO>();

        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,project.getId());

        //ArrayList zu Appointments aufbauen
        for(int i = 1;i< result.getPropertyCount();i++)
        {
            SoapObject object = (SoapObject) result.getProperty(i);
            AppointmentTO app = new AppointmentTO();
            app.setTopic(object.getProperty("topic").toString());
            app.setDescription(object.getProperty("description").toString());
            String date = object.getProperty("appointmentDate").toString();
            long d = Long.parseLong(date);
            Date dateT = new Date();
            dateT.setTime(d);

            app.setAppointmentDate(dateT.getTime());
            appointmentList.add(app);


        }

        return appointmentList;

    }

    public static void addAppointment(AppointmentTO app, ProjectTO project) throws SoapFault {
        String method = "addAppointmentToProject";

        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,project.getId(),app.getTopic(),app.getDescription(),app.getAppointmentDate());
    }

    public static void updateAppointment(){

    }

    public static  void deleteAppointment(){

    }
}
