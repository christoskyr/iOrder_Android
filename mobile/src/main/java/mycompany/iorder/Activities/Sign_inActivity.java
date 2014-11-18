package mycompany.iorder.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import models.User;
import mycompany.iorder.R;


import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sign_inActivity extends Activity implements View.OnClickListener {

    private EditText email_given, pass_given;
    private Button ok;

    // Progress Dialog
    private ProgressDialog pDialog;

    private static final String LOGIN_URL = "https://iorderweb.herokuapp.com/api/v1/users";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_given = ((EditText) findViewById(R.id.email_signin));
        pass_given = ((EditText) findViewById(R.id.pass_signin));

        if (email_given.equals("") || pass_given.equals(""))
          Toast.makeText(Sign_inActivity.this, R.string.error_all_fields_are_required, Toast.LENGTH_LONG).show();

        ok = (Button)findViewById(R.id.buttonSign_in);
        ok.setOnClickListener((View.OnClickListener) Sign_inActivity.this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonSign_in:
                new AttemptLogin().execute();
        }
    }

    public class JSONParser {
         InputStream is = null;

         JSONObject jObj = null;

         String json = "";
        public JSONParser() {
        }
        public JSONObject getJSONFromUrl(final String url) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");

                }
                is.close();
                json = sb.toString();

            } catch (Exception e) {

                Log.e("Buffer Error", "Error converting result " + e.toString());

            }
            try {

                jObj = new JSONObject(json);

            } catch (JSONException e) {

                Log.e("JSON Parser", "Error parsing data " + e.toString());

            }
            return jObj;
        }
        public JSONObject makeHttpRequest(String url, String method,
                                          List<NameValuePair> params) {
            try {
                if(method == "POST"){
                    DefaultHttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(url);

                    httpPost.setEntity(new UrlEncodedFormEntity(params));



                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();

                    is = httpEntity.getContent();



                }else if(method == "GET"){

                    // request method is GET

                    DefaultHttpClient httpClient = new DefaultHttpClient();

                    String paramString = URLEncodedUtils.format(params, "utf-8");

                    url += "?" + paramString;

                    HttpGet httpGet = new HttpGet(url);



                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();

                }
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                is.close();

                json = sb.toString();

            } catch (Exception e) {

                Log.e("Buffer Error", "Error converting result " + e.toString());

            }
            try {

                jObj = new JSONObject(json);

            } catch (JSONException e) {

                Log.e("JSON Parser", "Error parsing data " + e.toString());

            }
            return jObj;
        }

    }




    class AttemptLogin extends AsyncTask<String, String, String> {

        boolean failure = false;
        JSONParser jsonParser = new JSONParser();


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            pDialog = new ProgressDialog(Sign_inActivity.this);

            pDialog.setMessage("Attempting login...");

            pDialog.setIndeterminate(false);

            pDialog.setCancelable(true);

            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {

            int success;

            String email = email_given.getText().toString();

            String pass = pass_given.getText().toString();

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("email", email));

                params.add(new BasicNameValuePair("password", pass));



                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                Log.d("Login attempt", json.toString());
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    Log.d("Login Successful!", json.toString());

                    Intent i = new Intent(Sign_inActivity.this, Order_Activity.class);

                    finish();

                    startActivity(i);

                    return json.getString(TAG_MESSAGE);

                }else{

                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));

                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {

                e.printStackTrace();

            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if (file_url != null){

                Toast.makeText(Sign_inActivity.this, file_url, Toast.LENGTH_LONG).show();

            }
        }
    }


//*******************************
//Custom iOrder Code ************
//*******************************

    //connect Sign_inActivity with OrderActivity
    public void sign_in(View view)
    {
        Intent intent = new Intent(this, Order_Activity.class);
        startActivity(intent);
    }

}
