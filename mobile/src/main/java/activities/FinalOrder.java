package activities;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import adapters.OrderedItemsAdapter;
import models.Product;
import models.iOrder;
import mycompany.iorder.R;

public class FinalOrder extends Activity {

    private iOrder iorder;
    private ArrayList<Product> orderedItemsList;
    private ListView orderedItems;
    private OrderedItemsAdapter orderedItemsAdapter;
    private TextView mTextView;
    private double totalCost;
    private String finalOrderString;
    private String shopId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);

        mTextView = (TextView) findViewById(R.id.totalCost);
        iorder = ((iOrder) getApplicationContext());
        orderedItems = (ListView) findViewById(R.id.orderedItems);
        orderedItemsList = new ArrayList<Product>();
        orderedItemsList = iorder.getProducts();
        orderedItemsAdapter = new OrderedItemsAdapter(this, orderedItemsList);
        DataSetObserver dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Ref();
            }
        };
        orderedItemsAdapter.registerDataSetObserver(dataSetObserver);
        orderedItems.setAdapter(orderedItemsAdapter);
        for (Product p : orderedItemsList) {
            totalCost += Double.parseDouble(p.getPrice()) * Double.parseDouble(p.getQuantity());
        }
//        totalCost = OrderedItemsAdapter.refreshTotalCost();
        mTextView.setText(Double.toString(totalCost));


//        Button chooseAnotherProduct = (Button) findViewById(R.id.button2);
//        chooseAnotherProduct.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Intent intent = new Intent(FinalOrder.this, Order_Activity.class);
//                startActivity(intent);
//            }
//        });

        Button addToStore = (Button) findViewById(R.id.button);
        addToStore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new HttpAsyncTask().execute("https://myiorderapp.herokuapp.com/api/v1/orders/new_order");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.final_order, menu);
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

    public String POST(String url, iOrder iorder) {
        InputStream inputStream = null;
        String result = "";

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        JSONArray productArray = new JSONArray();

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_id", iorder.getUserId()));
            nameValuePairs.add(new BasicNameValuePair("store_id", iorder.getStoreId()));
            nameValuePairs.add(new BasicNameValuePair("auth_token", iorder.getAuthenticationToken()));
            nameValuePairs.add(new BasicNameValuePair("total", Double.toString(totalCost)));
            for (Product p : orderedItemsList) {
                JSONObject productJson = new JSONObject();
                try {
                     productJson.put("id", p.getProductId());
                     productJson.put("quantity", p.getQuantity());
                     productJson.put("details", p.getDetails());
                }catch(JSONException e){
                    e.printStackTrace();
                }
                productArray.put((productJson));
            }
            nameValuePairs.add(new BasicNameValuePair("order_details", new Gson().toJson(productArray.toString())));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            finalOrderString = content;
            StringEntity se = new StringEntity(finalOrderString);
            httppost.setEntity(se);
        }catch (IOException e) {
            // TODO Auto-generated catch block
        }


        return result;
    }


    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0], iorder);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    public void Ref() {
        Double newCost = 0.0;
        for (Product p : orderedItemsList) {
            newCost += Double.parseDouble(p.getPrice()) * Double.parseDouble(p.getQuantity());
        }
//        totalCost = OrderedItemsAdapter.refreshTotalCost();
        mTextView.setText(Double.toString(newCost));
    }
}
