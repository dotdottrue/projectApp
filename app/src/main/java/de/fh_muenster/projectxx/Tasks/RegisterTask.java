package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;

import java.util.List;

import de.fh_muenster.projectxx.soap.ContactService;

/**
 * Created by user on 16.06.15.
 */
public class RegisterTask extends AsyncTask<String,String,String> {
    private Context context;
    private String userid;
    private Application app;

    public RegisterTask(Context c, String uid, Application a){
        this.context = c;
        this.userid = uid;
        this.app = a;
    }

    protected String doInBackground(String... params) {
        try {
            SoapObject so = (SoapObject)ContactService.register(this.userid);
            return "Registert!";
        }
        catch (SoapFault e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute() {

    }
}
