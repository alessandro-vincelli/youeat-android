package it.av.youeat.android;

import it.av.youeat.ocm.model.Ristorante;
import it.av.youeat.ocm.model.combined.RistoranteAndDistance;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class RESTUtils {

    private static final String TAG = "tag";
    private static Location location;
    ObjectMapper mapper = new ObjectMapper();
    private Iterator<JsonNode> ristoIterator;

    public final List<RistoranteAndDistance> findRistoOnCurrentPosition(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        if (!providers.isEmpty()) {
            location = locationManager.getLastKnownLocation(providers.get(0));
        }

        List<RistoranteAndDistance> results = new ArrayList<RistoranteAndDistance>(20);
        if (location != null) {
            ristoIterator = restCall("http://www.youeat.org/rest/findCloseRistoranti/" + location.getLatitude() + "/"
                    + location.getLongitude() + "/900000000/20", "ristorantePositionAndDistanceList");
        } else {
            ristoIterator = restCall(
                    "http://www.youeat.org/rest/findCloseRistoranti/37.331689/-122.030731/900000000/20",
                    "ristorantePositionAndDistanceList");
        }
        while (ristoIterator.hasNext()) {
            JsonNode jsonRistoNode = ristoIterator.next();
            Ristorante risto = mapper.convertValue(jsonRistoNode.get("ristorante"), Ristorante.class);
            Long distance = mapper.convertValue(jsonRistoNode.get("distanceInMeters"), Long.class);
            RistoranteAndDistance ristoranteAndDistance = new RistoranteAndDistance(risto, distance);
            results.add(ristoranteAndDistance);
        }
        return results;
    }

    public final List<Ristorante> findRisto(String query) {
        List<Ristorante> results = new ArrayList<Ristorante>(20);
        ristoIterator = restCall("http://www.youeat.org/rest/findRistoranti/" + query, "ristoranteList");
        while (ristoIterator.hasNext()) {
            JsonNode jsonRistoNode = ristoIterator.next();
            Ristorante risto = mapper.convertValue(jsonRistoNode, Ristorante.class);
            results.add(risto);
        }
        return results;
    }

    private Iterator<JsonNode> restCall(String url, String rootJSON) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;
        Iterator<JsonNode> ristoIterator = null;
        try {
            response = httpclient.execute(httpget);
            Log.i(TAG, "Status:[" + response.getStatusLine().toString() + "]");
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                JsonNode readTree = mapper.readTree(instream).get(rootJSON);
                ristoIterator = readTree.getElements();
            }
        } catch (ClientProtocolException e) {
            Log.e("REST", "There was a protocol based error", e);
        } catch (IOException e) {
            Log.e("REST", "There was an IO Stream related error", e);
        }
        return ristoIterator;
    }
}
