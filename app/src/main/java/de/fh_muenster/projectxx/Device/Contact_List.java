package de.fh_muenster.projectxx.Device;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import de.fh_muenster.projectxx.R;
import de.fh_muenster.projectxx.Tasks.ListContactsTask;
import de.project.dto.project.ProjectTO;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Diese Klasse verwaltet und baut die Contact_List Activity auf
 * @author Dennis Russ
 * @version 1.0 Erstellt am 08.06.15
 */
public class Contact_List extends ActionBarActivity {

    /**
     * Es werden diverse Attribute benötigt um zustände zu speichern
     * contactPhonenumbers:     Enthält alle Telefonnummern aus dem eigenen Adressbuch
     * contacts:                Enthält alle Telefonummern als Key sowie alle Kontaktnamen als value
     * project:                 Enthält das aktuelle Projekt
     */
    public TextView outputText;
    private ArrayList<String> contactPhonenumbers;
    private HashMap<String,String> contacts;
    private ProjectTO project;

    @Override
    /**
     * Baut die Activity auf
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__list);
        Intent i = getIntent();
        project = (ProjectTO)i.getSerializableExtra("project");
        firststeps();
    }

    @Override
    /**
     * Erzeugt das App Menü
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact__list, menu);
        return true;
    }

    @Override
    /**
     * Regestriert wenn ein Menüeintrag aktiviert wird und reagiert entsprechent
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Verwaltet alle Fnktionen welche beim start der Activity benutzerdefiniert stattfinden müssen
     * Erzeugt z.B. die Async Task zum abrufen von Daten beim Server
     */
    private void firststeps(){
        ListContactsTask task = new ListContactsTask(getApplicationContext(),getApplication(),this,this.project);
        task.execute();
    }

}
