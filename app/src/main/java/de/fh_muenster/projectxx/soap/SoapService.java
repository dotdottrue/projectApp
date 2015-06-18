package de.fh_muenster.projectxx.soap;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 08.06.15.
 */
public class SoapService {
    /**
     * Namespace is the targetNamespace in the WSDL.
     */
    public static final String NAMESPACE = "http://integration.project.de/";//"http://integration.project.de/";
    /**
     * The WSDL URL. Its value is the location attribute of the soap:address element for a port
     * element in a WSDL. Unless the web service is also hosted on the Android device, the hostname
     * should not be specified as localhost, because the application runs on the Android device while
     * the web service is hosted on the localhost server. Specify hostname as the IP address of the
     * server hosting the web service (or "10.0.2.15 instead of 'localhost' when running in the emulator).
     */

    public static final String URL = "http://192.168.1.105:8080/project/UserIntegration?wsdl";
    public static final String URL2 = "http://192.168.1.105:8080/project/ProjectIntegration?wsdl";
    public static final Date formatInputToDate(String date) throws ParseException {
        SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return output.parse(date);
    }

    /**
     * Diese Methode delegiert einen Methodenaufruf an den hinterlegten WebService.
     * @param methodName
     * @return
     */
    public static SoapObject executeSoapAction(String methodName, String url, Object... args) throws SoapFault {
        Object result = null;
        /* Create a org.ksoap2.serialization.SoapObject object to build a SOAP request. Specify the namespace of the SOAP object and method
        * name to be invoked in the SoapObject constructor.
        */
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        /* The array of arguments is copied into properties of the SOAP request using the addProperty method. */
        int argCounter = 0;
        for (int i=0; i<args.length; i++) {
            //PropertyInfo propInfo = new PropertyInfo();
            //propInfo.name = "arg" + i;
            //propInfo.type = PropertyInfo.STRING_CLASS;
           //request.addProperty(propInfo);

            if(args[i] instanceof ArrayList) {
                ArrayList<String> contacts = (ArrayList<String>) args[i];
                for(String s : contacts){

                    request.addProperty("arg"+argCounter,s);
                }

            }
            else
            {
                request.addProperty("arg"+argCounter,args[i].toString());
            }
            argCounter++;



        }
        /* Next create a SOAP envelop. Use the SoapSerializationEnvelope class, which extends the SoapEnvelop class, with support for SOAP
        * Serialization format, which represents the structure of a SOAP serialized message. The main advantage of SOAP serialization is portability.
        * The constant SoapEnvelope.VER11 indicates SOAP Version 1.1, which is default for a JAX-WS webservice endpoint under JBoss.
        */
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.implicitTypes = true;

        /* Assign the SoapObject request object to the envelop as the outbound message for the SOAP method call. */
        envelope.setOutputSoapObject(request);
        /* Create a org.ksoap2.transport.HttpTransportSE object that represents a J2SE based HttpTransport layer. HttpTransportSE extends
        * the org.ksoap2.transport.Transport class, which encapsulates the serialization and deserialization of SOAP messages.
        */
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
        System.out.println("kurz vor call");
        try {
        /* Make the soap call using the SOAP_ACTION and the soap envelop. */
            List<HeaderProperty> reqHeaders = null;
            @SuppressWarnings({"unused", "unchecked"})
            //List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE + methodName, envelope, reqHeaders);
            //fuehrt zu CXF-Fehler! neue Version ohne SOAP-Action funktioniert:
            List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);
            //List<HeaderProperty> respHeaders = androidHttpTransport.call(NAMESPACE+methodName, envelope,reqHeaders);
            System.out.println("nach call");
            /* Get the web service response using the getResponse method of the SoapSerializationEnvelope object.
            * The result has to be cast to SoapPrimitive, the class used to encapsulate primitive types, or to SoapObject.
            */

             //SoapPrimitive resultsString = (SoapPrimitive)envelope.getResponse();
             result = envelope.getResponse();
             //result = resultsString.toString();

            //result = envelope.getResponse();
            if (result instanceof SoapFault) {
                throw (SoapFault) result;
            }
        }
        catch (SoapFault e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return (SoapObject) result;
    }


}
