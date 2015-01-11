package mycompany.iorder.Activities;

import mycompany.iorder.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class Order_Activity extends Activity {

    EditText etResponse;
    TextView tvIsConnected;
    ListView list;

    // Hashmap for ListView

    private ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

    //JSON Node Names
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PRICE = "price";

    public static String shopName, shopId, productId;
    public TextView mTextView2, mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);

        list=(ListView)findViewById(R.id.list);

        Intent intent = getIntent();

        shopName = intent.getStringExtra("name");
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(shopName);

        shopId = intent.getStringExtra("id");
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView2.setText(shopId);

        // get reference to the views
        //etResponse = (EditText) findViewById(R.id.etResponse);
        //tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not
        //if(isConnected()){
         //   tvIsConnected.setBackgroundColor(0xFF00CC00);
        //    tvIsConnected.setText("You are connected");
       // }
       // else{
         //   tvIsConnected.setText("You are NOT connected");
       // }


        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("https://iorderapp.herokuapp.com/api/v1/stores/"+shopId+"/products");

    }


    public static String GET(String url){
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
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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
                                String str = "";

                                JSONArray products = json.getJSONArray("products");

                                for(int i = 0; i < products.length(); i++){

                                    JSONObject c = products.getJSONObject(i);

                                    // Storing  JSON item in a Variable
                                    String id = c.getString(TAG_ID);
                                    final String title = c.getString(TAG_TITLE);
                                    final String price = c.getString(TAG_PRICE);

                                    // Adding value HashMap key => value
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put(TAG_ID, id);
                                    map.put(TAG_TITLE, title);
                                    map.put(TAG_PRICE, price);
                                    productList.add(map);


                                    final ListAdapter adapter = new SimpleAdapter(Order_Activity.this, productList,
                                            R.layout.activity_single_product,
                                            new String[] { TAG_ID,TAG_TITLE, TAG_PRICE }, new int[] {
                                            R.id.id,R.id.title, R.id.price});


                                    list.setAdapter(adapter);

                                    AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){

                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view,
                                                                final int position, long id) {

                                            runOnUiThread(new Runnable() {
                                                public void run() {


                                                    Intent i = new Intent(Order_Activity.this, ProductDetails.class);
                                                    i.putExtra("title", productList.get(position).get("title").toString());
                                                    i.putExtra("price", productList.get(position).get("price").toString());
                                                    i.putExtra("id", productList.get(position).get("id").toString());

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