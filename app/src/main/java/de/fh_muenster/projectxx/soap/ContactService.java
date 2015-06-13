package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

/**
 * Created by user on 13.06.15.
 */
public class ContactService {

    public static void register(String userid) throws SoapFault {
        String method = "";
        SoapObject result = SoapService.executeSoapAction(method,SoapService.URL,userid);
    }
}
