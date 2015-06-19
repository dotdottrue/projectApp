package de.fh_muenster.projectxx.Device;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Diese Klasse stellt alle Services bereit zum Abfragen der Device Schnittstellen
 * @author Dennis Russ
 * @version 1.0 Erstellt am 13.06.15
 */
public class DeviceService {
    /**
     * Beim Start der App werden Objekte global gefüllt welche lange Rechenleistung benötigen
     * myContacts:      Enthält alle Telefonnumern als Key und alle Contactnamen als value des Adressbuches
     * myPhoneNumber:   Enthält die eigene Rufnummer des Gerätes
     */
    public static HashMap<String,String> myContacts;
    public static String myPhoneNumber;

    /**
     * Diese Methode ruft die eigene Rufnummer des Gerätes ab und speichert diese in myPhoneNumber
     * @param context Aktueller Activitycontext
     * @return Gibt zusätzlich die eigene Rufnummer direkt an dne Aufrufer zurück
     */
    public static String getMyPhonenumber(Context context){
        TelephonyManager phn_mngr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        //Sonderzeichen aus der Rufnummer entfernen
        myPhoneNumber = phn_mngr.getLine1Number().replaceAll("\\+","");
        if(myPhoneNumber.startsWith("0")){
            String s1 = myPhoneNumber.substring(1);
            myPhoneNumber = "49"+s1;
        }
        myPhoneNumber = myPhoneNumber.replaceAll(" ", "");
        myPhoneNumber = myPhoneNumber.replaceAll("\\+", "");
        myPhoneNumber = myPhoneNumber.replaceAll("/", "");

        return phn_mngr.getLine1Number().replaceAll("\\+","");
    }

    /**
     * Diese Methode ruft alle Telefonnummer und Contactnamen aus dem Telefonbuch des Gerätes ab
     * @param contentResolver enthält den Activity Contentresolver
     * @return Gibt eine gefüllte HashMap zurück mit Telefonnummer als Key und dem Namen als value
     */
    public static HashMap<String,String> getMyContacts(ContentResolver contentResolver){
        String phoneNumber = null;
        String email = null;
        HashMap<String,String> myContacts = new HashMap<String,String>();

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;


        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {

                    // Query and loop for every phone number of the contact

                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                    while (phoneCursor.moveToNext()) {

                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        if(phoneNumber.startsWith("0")){
                            String s1 = phoneNumber.substring(1);
                            phoneNumber = "49"+s1;
                        }
                        phoneNumber = phoneNumber.replaceAll(" ", "");
                        phoneNumber = phoneNumber.replaceAll("\\+", "");
                        phoneNumber = phoneNumber.replaceAll("/", "");
                        myContacts.put(phoneNumber.trim(),name);

                    }
                    myContacts.put(DeviceService.myPhoneNumber,"Du");
                    phoneCursor.close();
                }

            }

        }
        return myContacts;
    }


    /**
     * Diese Methode ruft alle Telefonnummer des Adressbuches ab
     * @param contentResolver enthält den Activity Contentresolver
     * @return  Gibt eine ArrayList<String> zurück mit allen Telefonnummern
     */
    public static ArrayList<String> getMyContactsPhonenumbers(ContentResolver contentResolver){
        String phoneNumber = null;
        ArrayList<String> myContacts = new ArrayList<String>();

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                    while (phoneCursor.moveToNext()) {

                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        if(phoneNumber.startsWith("0")){
                            String s1 = phoneNumber.substring(1);
                            phoneNumber = "49"+s1;
                        }
                        phoneNumber = phoneNumber.replaceAll(" ", "");
                        phoneNumber = phoneNumber.replaceAll("\\+", "");
                        phoneNumber = phoneNumber.replaceAll("/", "");
                        myContacts.add(phoneNumber.trim());
                    }
                    phoneCursor.close();
                }

            }

        }
        return myContacts;
    }
}
