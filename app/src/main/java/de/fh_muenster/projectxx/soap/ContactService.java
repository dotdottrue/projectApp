package de.fh_muenster.projectxx.soap;


import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;
import de.project.dto.project.ProjectTO;
import de.project.dto.user.UserTO;

/**
 * Diese Klasse stellt alle Service zum Thema Kontakte bereit und bereitet die Soap verbindung vor
 * @author Dennis Russ
 * @version 1.0 Erstellt am 13.06.15
 */
public class ContactService {

    /**
     * Diese Methode regestriert das Gerät beim Server
     * @param userid    Die eigene Telefonnummer
     * @return  SoapObject
     * @throws SoapFault Wirft soap Exceptions
     */
    public static SoapObject register(String userid) throws SoapFault {
        String method = "registerUser";
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL,userid);
        return  result;
    }

    /**
     * Diese Methode sendet die eigenen Kontakte zum Server um zu prüfen welche der Kontakte die App ebenfalls nutzen
     * @param project   Aktuelle Projekt
     * @param contacts  Alle telefonnummer der eigenen Kontakte
     * @return  List<UserTO>
     * @throws SoapFault wirft soap Exceptions
     */
    public static ArrayList<UserTO>  compaireContacts(ProjectTO project, ArrayList<String> contacts) throws SoapFault{
        String method = "comparePhonebook";
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,project.getId(),contacts);
        //ArrayList zu Projekten Aufbauen
        ArrayList<UserTO> users = new ArrayList<UserTO>();
        for(int i = 1;i< result.getPropertyCount();i++)
        {
            SoapObject object = (SoapObject) result.getProperty(i);
            UserTO user = new UserTO();
            user.setPhoneNumber(object.getProperty("phoneNumber").toString());
            users.add(user);
        }
        return users;
    }
}
