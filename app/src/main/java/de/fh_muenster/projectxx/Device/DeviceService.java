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
 * Created by user on 13.06.15.
 */
public class DeviceService {

    public static String getMyPhonenumber(Context context){
        TelephonyManager phn_mngr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return phn_mngr.getLine1Number().replaceAll("\\+","");
    }

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
        StringBuffer output = new StringBuffer();

        //ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        // Loop for every contact in the phone

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {

                    output.append("\n First Name:" + name);

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
                        output.append("\n Phone number:" + phoneNumber);
                        myContacts.put(phoneNumber.trim(),name);

                    }

                    phoneCursor.close();



                }

            }

        }
        return myContacts;
    }


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
