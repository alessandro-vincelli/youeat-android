package it.av.youeat.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class YouEat extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String result = RESTUtils
                .queryRESTurl("http://www.youeat.org/rest/findCloseRistoranti/37.331689/-122.030731/900000000/5");

        try {
            JSONObject json = new JSONObject(result);

            JSONArray root = json.getJSONArray("ristorantePositionAndDistanceList");
            List<String> risto = new ArrayList<String>(0);
            for (int i = 0; i < root.length(); i++) {
                JSONObject ristoA = root.getJSONObject(i);
                risto.add(ristoA.getJSONObject("ristorante").getString("name"));
            }

            setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, risto));

        } catch (JSONException e) {
            Log.e("JSON", "There was an error parsing the JSON", e);
        }

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}