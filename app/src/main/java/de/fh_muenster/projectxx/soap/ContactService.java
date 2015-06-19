package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;
import java.util.ArrayList;

import de.fh_muenster.projectxx.Device.DeviceService;
import de.project.dto.project.ProjectTO;
import de.project.dto.user.UserTO;

/**
 * Created by user on 13.06.15.
 */
public class ContactService {

    public static SoapObject register(String userid) throws SoapFault {
        String method = "registerUser";
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL,userid);
        //String test = result.getPrimitivePropertyAsString("returnCode");
        return  result;
    }

    public static ArrayList<UserTO>  compaireContacts(ProjectTO project, ArrayList<String> contacts) throws SoapFault{
        String method = "comparePhonebook";
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL2,project.getId(),contacts);
        System.out.println("ZUrück im Service");
        //ArrayList zu Projekten Aufbauen
        ArrayList<UserTO> users = new ArrayList<UserTO>();
        System.out.println(result.getPropertyCount());
        for(int i = 1;i< result.getPropertyCount();i++)
        {

            SoapObject object = (SoapObject) result.getProperty(i);
            UserTO user = new UserTO();
            user.setPhoneNumber(object.getProperty("phoneNumber").toString());
            System.out.println(user.getPhoneNumber());
            users.add(user);
        }
        System.out.println("VErlasse Contact Service");
        return users;
    }


}
