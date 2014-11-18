package mycompany.iorder.Activities;

import mycompany.iorder.R;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends Activity  {

    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;

    // Hashmap for ListView
    private ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "https://iorderweb.herokuapp.com/api/v1/stores/1/products";
    //JSON Node Names
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PRICE = "price";


    // contacts JSONArray
    JSONArray products = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        productList = new ArrayList<HashMap<String, String>>();
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONParse().execute();
            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        //private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ver = (TextView)findViewById(R.id.id);
            name = (TextView)findViewById(R.id.title);
            api = (TextView)findViewById(R.id.price);
            //pDialog = new ProgressDialog(OrderActivity.this);
            //pDialog.setMessage("Getting Data ...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JParser jParser = new JParser();
            // Getting JSON from URL
            JSONArray products = jParser.getJSONFromUrl(url);
            return products;
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            //pDialog.dismiss();
            //pDialog.dismiss();

            try {
                // Getting JSON Array from URL

                for(int i = 0; i < products.length(); i++){
                    JSONObject c = products.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String ver = c.getString(TAG_ID);
                    String name = c.getString(TAG_TITLE);
                    String api = c.getString(TAG_PRICE);
                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_ID, ver);
                    map.put(TAG_TITLE, name);
                    map.put(TAG_PRICE, api);
                    productList.add(map);
                    list=(ListView)findViewById(R.id.list);
                    ListAdapter adapter = new SimpleAdapter(OrderActivity.this, productList,
                            R.layout.activity_single_product,
                            new String[] { TAG_ID,TAG_TITLE, TAG_PRICE }, new int[] {
                            R.id.id,R.id.title, R.id.price});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(OrderActivity.this, "You Clicked at "+productList.get(+position).get("name"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


//*******************************
//Custom iOrder Code ************
//*******************************



}

