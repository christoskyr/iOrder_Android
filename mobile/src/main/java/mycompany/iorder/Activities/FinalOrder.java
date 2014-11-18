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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import models.Order;
import models.User;
import mycompany.iorder.R;

public class FinalOrder extends Activity {

    private String total_cost = null;

    Button addToStore;

    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);


        final TextView mTextView = (TextView) findViewById(R.id.textView);

        Intent in = getIntent();
        String title = in.getStringExtra(("title"));
        String quantity = in.getStringExtra(("quantity"));
        String price = in.getStringExtra(("price"));
        mTextView.setText(title);

        final TextView mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView2.setText(quantity);
        int quantity_num =Integer.parseInt(mTextView2.getText().toString());

        final TextView mTextView3 = (TextView) findViewById(R.id.textView3);
        mTextView3.setText(price);
        int price_num = Integer.parseInt(mTextView3.getText().toString());

        int temp2 = quantity_num*price_num;
        final TextView mTextView6 = (TextView) findViewById(R.id.textView6);
        mTextView6.setText(temp2);

        Button chooseAnotherProduct = (Button) findViewById(R.id.button2);
        chooseAnotherProduct.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(FinalOrder.this, Order_Activity.class);
                startActivity(intent);
            }
        });
/*
        Button addToStore = (Button) findViewById(R.id.button);
        addToStore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String total_cost = ((TextView) findViewById(R.id.textView6)).getText().toString();
                new HttpAsyncTask().execute("https://iorderweb.herokuapp.com/api/v1/orders");
            }
        });*/

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

/*
    public String POST(String url, Order orders) {
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
            jsonObject.accumulate("total_cost", orders.getTotal_cost());


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

    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            order = new Order();
            order.setTotal_cost(total_cost.toString());


            return POST("https://iorderweb.herokuapp.com/api/v1/orders", order);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

            //tvIsConnected.setText(name.getText().toString());
            //tvIsConnected.setText(email.getText().toString());
            //tvIsConnected.setText(password.getText().toString());
            //tvIsConnected.setText(result.toString());
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

    }*/
}
