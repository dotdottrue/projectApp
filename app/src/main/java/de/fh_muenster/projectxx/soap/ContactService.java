package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;
import java.util.ArrayList;

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

    public static ArrayList<UserTO>  compaireContacts(ArrayList<String> contacts) throws SoapFault{
        String method = "compaireUser";
        SoapObject result = (SoapObject)SoapService.executeSoapAction(method,SoapService.URL,contacts);

        //ArrayList zu Projekten Aufbauen
        ArrayList<UserTO> users = new ArrayList<UserTO>();
        for(int i = 1;i< result.getPropertyCount();i++)
        {
            SoapObject object = (SoapObject) result.getProperty(i);
            UserTO user = new UserTO();
            user.setPhoneNumber(object.getProperty("phonenumber").toString());
            users.add(user);
        }

        return users;
    }


}
