package de.fh_muenster.projectxx.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;

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


}
