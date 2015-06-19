package de.fh_muenster.projectxx.Tasks;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.ArrayRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fh_muenster.projectxx.Device.Contact_List;
import de.fh_muenster.projectxx.Device.DeviceService;
import de.fh_muenster.projectxx.Interfaces.AsyncResponse;
import de.fh_muenster.projectxx.ProjectDetail;
import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.soap.ContactService;
import de.fh_muenster.projectxx.soap.ForumService;
import de.project.dto.discussion.DiscussionTO;
import de.project.dto.project.ProjectTO;
import de.project.dto.user.UserTO;

/**
 * Created by user on 17.06.15.
 */
public class ListContactsTask extends AsyncTask<ProjectTO,String,List<UserTO>> {
    private ProjectTO project;
    private Context context;
    private Application app;
    private String userid;
    private ArrayList<String> contactPhoneNumbersServer = new ArrayList<String>();
    private ArrayList<String> contactPhoneNumbers = new ArrayList<String>();
    private ArrayList<String> contactNames = new ArrayList<String>();
    private Contact_List activity;
    private ListView contacts;
    public AsyncResponse delegate = null;

    public ListContactsTask(Context c, Application a, Contact_List act, ProjectTO project){
        this.context = c;
        this.app = a;
        this.activity = act;
        this.project = project;
    }

    protected List<UserTO> doInBackground(ProjectTO... params) {
        try {
            List<UserTO> userList = null;
            ArrayList<String> contactPhonenumbers = DeviceService.getMyContactsPhonenumbers(this.app.getContentResolver());
            userList = (List<UserTO>) ContactService.compaireContacts(this.project, contactPhonenumbers);
            return userList;
        }
        catch (Exception e) {
            Toast.makeText(app.getApplicationContext(), "Serververbindung konnte nicht erfolgreich aufgebaut werden!", Toast.LENGTH_SHORT).show();
        }
        System.out.println("Es kommt null");
        return null;
    }

    protected void onProgessUpdate(Integer... values) {
    }

    protected void onPostExecute(final List<UserTO> result) {


        contacts = (ListView)this.activity.findViewById(R.id.contactlist);
        //this.contacts.invalidateViews();

        if(result == null) {
            //Prüfung ob die Liste <Friendship> bereits gefüllt ist, ansonsten Ausgabe des Toasts
            Toast.makeText(app.getApplicationContext(), "Du hast derzeit keine Diskussionen", Toast.LENGTH_SHORT).show();


        }
        else {

            createPhonenumberListServer(result);

            createContactList();
            //Layout anhand der ID suchen und in Variable speichern
            RelativeLayout ll = (RelativeLayout) this.activity.findViewById(R.id.activity_ContactList);
            //Listview setzen
            this.contacts = (ListView)this.activity.findViewById(R.id.contactlist);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                    R.layout.listview, R.id.listTextView, this.contactNames);

            // Assign adapter to ListView
            this.contacts.setAdapter(adapter);

            // ListView Item Click Listener
            this.contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    String itemValue = (String) contacts.getItemAtPosition(position).toString();

                    // Show Alert
                    Toast.makeText(context,
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();
                    //start Detail Activity

                    addUser(contactPhoneNumbers.get(itemPosition));
                    activity.finish();
                }
            });



        }

    }

    private void createPhonenumberListServer(List<UserTO> users){
        for(UserTO user : users){
            this.contactPhoneNumbersServer.add(user.getPhoneNumber());
        }

    }

    private void createContactList(){
        HashMap<String,String> phonenumbersContactNames = DeviceService.myContacts;
        for(String phonenumber : this.contactPhoneNumbersServer){
            String myContact = phonenumbersContactNames.get(phonenumber);
            if(myContact != null){
                this.contactPhoneNumbers.add(phonenumber);
                this.contactNames.add(myContact);
            }
        }
    }

    private void addUser(String phonenumber){
        AddUserToProjectTask task = new AddUserToProjectTask(this.context,this.app,phonenumber,this.project.getId());
        task.execute(phonenumber);

    }

}
