package activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

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

import models.Credentials;
import models.User;
import models.iOrder;
import mycompany.iorder.R;

public class Sign_inActivity extends Activity /*implements View.OnClickListener*/ {

    User user;

    private EditText email_given, pass_given;
    private Button ok;

    // Progress Dialog
    private ProgressDialog pDialog;

    private static final String LOGIN_URL = "https://myiorderapp.herokuapp.com/api/v1/users/sessions/signin";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_given = ((EditText) findViewById(R.id.email_signin));
        pass_given = ((EditText) findViewById(R.id.pass_signin));

        email_given.setText("christos@gmail.com");
        pass_given.setText("app123456");

        if (email_given.equals("") || pass_given.equals(""))
          Toast.makeText(Sign_inActivity.this, R.string.error_all_fields_are_required, Toast.LENGTH_LONG).show();

        ok = ((Button)findViewById(R.id.buttonSign_in));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonSign_in:
                        new HttpAsyncTask().execute();
                }
            }
        });
    }



    public String POST(String url, User users) {
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

            //jsonObject.accumulate("email", users.getEmail());
            //jsonObject.accumulate("password", users.getPassword());

            jsonObject.accumulate("email", email_given.getText());
            jsonObject.accumulate("password", pass_given.getText());

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
            if (inputStream != null && httpResponse.getStatusLine().getStatusCode()==201)
                result = convertInputStreamToString(inputStream);
            else
                result = "Authentication Failure";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }


    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            user = new User();

            user.setEmail(email_given.toString());
            user.setPassword(pass_given.toString());


            return POST(LOGIN_URL, user);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if(result != "Authentication Failure" && result != null)
            {
                iOrder iorder = ((iOrder) getApplicationContext());
//                iorder.setAuthenticationToken(result.substring(14, result.length()-4));
                String x = result.replace("\\","");
                Credentials credentials = new Gson().fromJson(x.substring(1,x.length()-1), Credentials.class);
                iorder.setUserId(credentials.getId().toString());
                iorder.setAuthenticationToken(credentials.getToken());
                Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
                sign_in();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Authentication Failure", Toast.LENGTH_LONG).show();
            }
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



//*******************************
//Custom iOrder Code ************
//*******************************

    //connect Sign_inActivity with OrderActivity
    public void sign_in()
    {
        //Intent intent = new Intent(this, ChooseShopActivity.class);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
