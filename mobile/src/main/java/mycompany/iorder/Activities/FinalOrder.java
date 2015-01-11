package mycompany.iorder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
    private String toOrder;
    private String finalOrderString;


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
        orderedItems.setAdapter(orderedItemsAdapter);
        totalCost = 0;
        for (Product p : orderedItemsList) {
            totalCost += Double.parseDouble(p.getPrice()) * Double.parseDouble(p.getQuantity());
        }

        mTextView.setText(Double.toString(totalCost));

        Button chooseAnotherProduct = (Button) findViewById(R.id.button2);
        chooseAnotherProduct.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(FinalOrder.this, Order_Activity.class);
                startActivity(intent);
            }
        });

        Button addToStore = (Button) findViewById(R.id.button);
        addToStore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new HttpAsyncTask().execute("https://iorderapp.herokuapp.com/api/v1/orders/new_order");
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
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            // 3. build jsonObject
            JSONObject json = new JSONObject();
            json.accumulate("user_id", iorder.getUserId());
            json.accumulate("store_id", iorder.getStoreId());
            json.accumulate("auth_token", iorder.getAuthenticationToken());
            try {
                JSONArray productArray = new JSONArray();
                for (Product p : orderedItemsList) {
                    JSONObject productJson = new JSONObject();
                    productJson.put("id", p.getProductId());
                    productJson.put("quantity", p.getQuantity());
                    productJson.put("details", p.getDetails());
                    productArray.put(productJson);
                }
                json.put("", productArray);
            } catch (Exception jse) {
            }

            finalOrderString = json.toString();

            // ** Alternative way to convert Users object to JSON string using Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(finalOrderString);

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

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
