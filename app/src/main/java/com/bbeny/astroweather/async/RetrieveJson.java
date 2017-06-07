package com.bbeny.astroweather.async;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bbeny.astroweather.db.AstroDb;
import com.bbeny.astroweather.db.PlaceModel;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Beny on 07.06.2017.
 */

public class RetrieveJson extends AsyncTask<Void, Void, Void> {

    private AstroDb astroDb;

    public RetrieveJson (Context context){
        astroDb = new AstroDb(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        astroDb.open();
        try {
            savePlace(connection("select * from geo.places(1) where text=\"dmosin\""));
        } catch (Exception e) {
            Log.d("astsata", "excefafsaf");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        astroDb.close();
    }

    private boolean savePlace(String inputStream) throws Exception {
            JSONObject query = new JSONObject(inputStream).getJSONObject("query");
            int count = query.getInt("count");
            if(count > 0) {
                JSONObject result;
                JSONObject place = query.getJSONObject("results").getJSONObject("place");
                if(!place.isNull("locality1"))
                    result = place.getJSONObject("locality1");
                else if(!place.isNull("admin2"))
                    result = place.getJSONObject("admin2");
                else if(!place.isNull("admin1"))
                    result = place.getJSONObject("admin1");
                else if(!place.isNull("country"))
                    result = place.getJSONObject("country");
                else
                    return false;
                JSONObject centroid = place.getJSONObject("centroid");
                String woeid = result.getString("woeid");
                String content = result.getString("content");
                String latitude = centroid.getString("latitude");
                String longitude = centroid.getString("longitude");
                String timeZone = place.getJSONObject("timezone").getString("content");
                PlaceModel placeModel = new PlaceModel(null, woeid, content, latitude, longitude, timeZone);
                if(astroDb.insertPlace(placeModel))
                    Log.d("Place", "placeaasdas");
                else
                    Log.d("Placexx", "placeaasdas");

                return true;
            }
            return false;
    }

    private String connection(String query) {
        String address = "https://query.yahooapis.com/v1/public/yql?q=" + query + "&format=json";
        InputStream in = null;
        HttpsURLConnection urlConnection = null;
        try {
            System.out.println("ADDRESS: " + address);
            URL url = new URL(address);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(20000);
            urlConnection.setReadTimeout(20000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (urlConnection.getResponseCode() != HttpsURLConnection.HTTP_ACCEPTED)
                in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return "";
    }


    private String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;) sb.append((char) c);
        return sb.toString();
    }
}
