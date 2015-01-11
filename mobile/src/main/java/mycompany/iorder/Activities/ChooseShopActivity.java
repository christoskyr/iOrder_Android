package mycompany.iorder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

import models.iOrder;
import mycompany.iorder.R;

public class ChooseShopActivity extends Activity {

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    ListView list;

    private ArrayList<HashMap<String, String>> storeList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shop);

        list = (ListView) findViewById(R.id.list);

        new HttpAsyncTask().execute("https://iorderapp.herokuapp.com/api/v1/stores");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//*******************************
//Custom iOrder Code ************
//*******************************

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();


            try {
                // Getting JSON Array from URL
                JSONObject json = new JSONObject(result);


                JSONArray stores = json.getJSONArray("stores");

                for (int i = 0; i < stores.length(); i++) {

                    JSONObject c = stores.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String id = c.getString(TAG_ID);
                    final String name = c.getString(TAG_NAME);


                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID, id);
                    map.put(TAG_NAME, name);
                    storeList.add(map);


                    final ListAdapter adapter = new SimpleAdapter(ChooseShopActivity.this, storeList,
                            R.layout.activity_single_store,
                            new String[]{TAG_ID, TAG_NAME}, new int[]{
                            R.id.id, R.id.title});


                    list.setAdapter(adapter);

                    AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    String store_id = storeList.get(position).get("id").toString();
                                    Intent i = new Intent(ChooseShopActivity.this, Order_Activity.class);
                                    i.putExtra("id", store_id);
                                    i.putExtra("name", storeList.get(position).get("name").toString());
                                    iOrder iorder = ((iOrder) getApplicationContext());
                                    iorder.setStoreId(store_id);
                                    startActivity(i);


                                }
                            });
                        }
                    };

                    list.setOnItemClickListener(onListClick);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
