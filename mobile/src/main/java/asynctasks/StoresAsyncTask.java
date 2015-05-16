package asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import interfaces.OnStoresTaskCompletedInterface;
import models.Store;

/**
 * Created by вягстос on 18/4/2015.
 */
public class StoresAsyncTask extends AsyncTask<Context, ArrayList<HashMap<String, String>>, String> {

    private static final String STORES_PATH = "https://myiorderapp.herokuapp.com/api/v1/stores";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    public OnStoresTaskCompletedInterface storesDelegate = null;

    public StoresAsyncTask() {
        this.storesDelegate = storesDelegate;
    }

    @Override
    protected String doInBackground(Context... urls) {
        return GET(STORES_PATH);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        try {
            // Getting JSON Array from URL
            JSONObject json = new JSONObject(result);

            JSONArray stores = json.getJSONArray("stores");

            ArrayList<Store> storeList = new ArrayList<Store>();

            for (int i = 0; i < stores.length(); i++) {
                Store store = new Store();
                JSONObject c = stores.getJSONObject(i);
                // Storing  JSON item in a Variable
                store.setId(c.getString(TAG_ID));
                store.setName(c.getString(TAG_NAME));
                store.setLatitude(c.getString(TAG_LATITUDE));
                store.setLongitude(c.getString(TAG_LONGITUDE));
                storeList.add(store);
            }
            if (storesDelegate != null)
                storesDelegate.onStoresTaskCompletedInterface(storeList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}