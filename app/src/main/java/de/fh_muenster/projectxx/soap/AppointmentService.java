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
 * Diese Klasse stellt alle Service zum Thema Termine bereit und bereitet die Soap verbindung vor
 * @author Dennis Russ8.06.15
 * @version 1.0 Erstellt am
 */
public class AppointmentService {

    /**
     * Diese Methode ruft alle Termine eines Projectes vom Server ab und bereitet diese auf
     * @param project   Das aktuelle Project
     * @return  Eine Liste mit AppointmentTo objecten
     * @throws SoapFault Wirft Soap Exceptions
     */
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
            //Appointment Topbic holen
            app.setTopic(object.getProperty("topic").toString());
            //Appointment Description holen
            app.setDescription(object.getProperty("description").toString());
            String date = object.getProperty("appointmentDate").toString();
            long d = Long.parseLong(date);
            Date dateT = new Date();
            dateT.setTime(d);
            app.setAppointmentDate(dateT.getTime());
            //Appointment ID holen
            String id = object.getProperty("id").toString();
            Long lid = Long.valueOf(id).longValue();
            long appid = lid;
            app.setId(appid);
            appointmentList.add(app);
        }
        return appointmentList;
    }

    /**
     * Diese Methode sendet einen neuen Termin an den Server
     * @param app   Aktuelle Application
     * @param project   Aktuelles Projekt
     * @throws SoapFault    Wirft soap Exceptions
     */
    public static void addAppointment(AppointmentTO app, ProjectTO project) throws SoapFault {
        String method = "addAppointmentToProject";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL2,project.getId(),app.getTopic(),app.getDescription(),app.getAppointmentDate());
    }

    /**
     * Diese Methode löscht einen Termin auf dem Server
     * @param project   aktuelles Project
     * @param appointment   aktueller Termin
     * @throws SoapFault    Wirft soap Exceptions
     */
    public static  void deleteAppointment(ProjectTO project, AppointmentTO appointment) throws SoapFault {
        String method = "removeProjectAppointment";
        long projectid = project.getId();
        long appointmentID = appointment.getId();
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,projectid,appointmentID);
    }
}
