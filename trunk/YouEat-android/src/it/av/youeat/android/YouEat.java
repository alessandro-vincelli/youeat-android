package it.av.youeat.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
	private final String TAG = "tag";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String result = queryRESTurl("http://www.youeat.org/rest/findCloseRistoranti/37.331689/-122.030731/900000000/5");

		try {
			JSONObject json = new JSONObject(result);
			
			JSONArray root = json.getJSONArray("ristorantePositionAndDistanceList");
			//ristorante
			
			List<String> risto = new ArrayList<String>(0); 
			for (int i = 0; i < root.length(); i++) {
				JSONObject ristoA = root.getJSONObject(i);
				risto.add(ristoA.getJSONObject("ristorante").getString("name"));
			}

			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
					risto));

			
//			for (int i = 0; i < valArray.length(); i++) {
//				Log.i(TAG, "<jsonname" + i + ">\n" + nameArray.getString(i)
//						+ "\n</jsonname" + i + ">\n" + "<jsonvalue" + i + ">\n"
//						+ valArray.getString(i) + "\n</jsonvalue" + i + ">");
//			}
		} catch (JSONException e) {
			Log.e("JSON", "There was an error parsing the JSON", e);
		}


		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	public String queryRESTurl(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		String result = ""; 
		try {
			response = httpclient.execute(httpget);
			Log.i(TAG, "Status:[" + response.getStatusLine().toString() + "]");
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {

				InputStream instream = entity.getContent();
				// String result = RestClient.convertStreamToString(instream);
				StringBuilder sb = new StringBuilder();
				try {

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream, "UTF-8"));
					String line = "";
					while ((line = reader.readLine()) != null) {
						sb.append(line).append("\n");
					}
					result = sb.toString();
				} finally {
					instream.close();
				}
				Log.i(TAG, "Result of converstion: [" + result + "]");
			}
		} catch (ClientProtocolException e) {
			Log.e("REST", "There was a protocol based error", e);
		} catch (IOException e) {
			Log.e("REST", "There was an IO Stream related error", e);
		}

		return result;
	}
}