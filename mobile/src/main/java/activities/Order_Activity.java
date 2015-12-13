package activities;

import adapters.OrderedItemsAdapter;
import models.Product;
import models.iOrder;
import mycompany.iorder.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.google.gson.Gson;

public class Order_Activity extends Activity {

    EditText etResponse;
    TextView tvIsConnected;
    ListView list;
    private ListView favOrderedItems;
    private iOrder iorder;
    private ArrayList<Product> favOrderedItemsList;
    private String favOrderedItemsString;

    // Hashmap for ListView

    private ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

    //JSON Node Names
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PRICE = "price";

    public static String shopName, shopId, productId, userId;
    public TextView mTextView2, mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);

        list=(ListView)findViewById(R.id.list);

        Intent intent = getIntent();

        shopName = intent.getStringExtra("storeName");
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(shopName);

        shopId = intent.getStringExtra("storeId");
//        mTextView2 = (TextView) findViewById(R.id.textView2);
//        mTextView2.setText(shopId);

        iorder = ((iOrder) getApplicationContext());
        userId = iorder.getUserId();
        iorder.setStoreId(shopId);


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
        new HttpAsyncTask().execute("https://myiorderapp.herokuapp.com/api/v1/stores/"+shopId+"/products");

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
                                            R.id.id,R.id.titleText, R.id.priceText})
                                    {
//                                        @Override
//                                        public View getView (final int position, View convertView, ViewGroup parent)
//                                        {
//                                            View v = super.getView(position, convertView, parent);
//
//                                            ImageButton b=(ImageButton)v.findViewById(R.id.favButton);
//                                            b.setOnClickListener(new android.view.View.OnClickListener() {
//
//                                                @Override
//                                                public void onClick(View arg0) {
//
//                                                    iorder.setProductId(productList.get(position).get("id").toString());
//                                                    iorder = ((iOrder) getApplicationContext());
//                                                    productId = iorder.getProductId();
//
//                                                    new HttpAsyncTask2().execute("https://myiorderapp.herokuapp.com/api/v1/favorites/new_favorite");
//                                                }
//                                            });
//                                            return v;
//                                        }
                                    };


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

    public String POST(String url, iOrder iorder) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("shop_id", shopId);
            jsonObject.accumulate("product_id", productId);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Users object to JSON string using Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.d("HEllooooooo", "yearpdragons");

            return POST(urls[0], iorder);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }



}