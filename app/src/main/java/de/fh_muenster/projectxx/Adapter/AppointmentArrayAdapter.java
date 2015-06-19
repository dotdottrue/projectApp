package de.fh_muenster.projectxx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.fh_muenster.projectxx.R;

/**
 * Diese Klasse verwaltet und bereitet die Daten der Listview für die Termine auf.
 * @author Dennis Russ
 * @version 1.0 Erstellt am 19.06.15
 */
public class AppointmentArrayAdapter extends ArrayAdapter<String> {
    /**
     * Es wird der Activity Context zum erzeugen der inflate benötigt
     * Zudem die Datensammlungen welche dargestellt werden sollen
     */
    private final Context context;
    private final ArrayList<String> values;
    private final ArrayList<String> values2;

    /**
     *
     * @param context   aktueller Activitycontext
     * @param values    Appointment Topics
     * @param values2   Appointment Dates
     */
    public AppointmentArrayAdapter(Context context, ArrayList<String> values, ArrayList<String> values2) {
        super(context, R.layout.listitemdetails2, values);
        this.context = context;
        this.values = values;
        this.values2 = values2;
    }

    @Override
    /**
     * Erzeugt den Inflater und weist die Daten den richtigen Ressourcen zu
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listitemdetails2, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.listViewDetailItem2);
        TextView textView2 = (TextView) rowView.findViewById(R.id.listViewDetailItem3);
        textView.setText(values.get(position));
        textView2.setText(values2.get(position));

        return rowView;
    }
}
