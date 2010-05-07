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

import android.util.Log;

public class RESTUtils {

    private static final String TAG = "tag";

    public static final List<RistoranteAndDistance> findRistoOnCurrentPosition() {
        List<RistoranteAndDistance> results = new ArrayList<RistoranteAndDistance>(20);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(
                "http://www.youeat.org/rest/findCloseRistoranti/37.331689/-122.030731/900000000/20");
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            Log.i(TAG, "Status:[" + response.getStatusLine().toString() + "]");
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode readTree = mapper.readTree(instream).get("ristorantePositionAndDistanceList");
                Iterator<JsonNode> ristoIterator = readTree.getElements();
                while (ristoIterator.hasNext()) {
                    JsonNode jsonRistoNode = ristoIterator.next();
                    Ristorante risto = mapper.convertValue(jsonRistoNode.get("ristorante"), Ristorante.class);
                    Long distance = mapper.convertValue(jsonRistoNode.get("distanceInMeters"), Long.class);
                    RistoranteAndDistance ristoranteAndDistance = new RistoranteAndDistance(risto, distance);
                    results.add(ristoranteAndDistance);
                }
                Log.i(TAG, "Result of converstion risto founds: [" + results.size() + "]");
            }
        } catch (ClientProtocolException e) {
            Log.e("REST", "There was a protocol based error", e);
        } catch (IOException e) {
            Log.e("REST", "There was an IO Stream related error", e);
        }
        return results;
    }
}
