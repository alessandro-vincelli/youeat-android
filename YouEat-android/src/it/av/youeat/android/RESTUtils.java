package it.av.youeat.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class RESTUtils {

	private static final String TAG = "tag";

	public static final String queryRESTurl(String url) {
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
				result = convertStreamToString(instream);
				Log.i(TAG, "Result of converstion: [" + result + "]");
			}
		} catch (ClientProtocolException e) {
			Log.e("REST", "There was a protocol based error", e);
		} catch (IOException e) {
			Log.e("REST", "There was an IO Stream related error", e);
		}

		return result;
	}

	private static final String convertStreamToString(InputStream instream)
			throws IOException {
		String result = "";
		StringBuilder sb = new StringBuilder();
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} finally {
			instream.close();
		}
		return result;
	}
}
