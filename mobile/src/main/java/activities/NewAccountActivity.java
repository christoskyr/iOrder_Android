package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import mycompany.iorder.R;
import models.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.TextView;

public class NewAccountActivity extends Activity  {

    //private static final int ERROR = 1;
    private String name = null;
    private String password = null;
    private String password_confirmation = null;
    private String email = null;
    //private String error = null;

    TextView tvIsConnected;
    Button signupPost;

    //EditText name, email, password, password_confirmation;

    User user;

    //private NewAccount newAccountInstance = new NewAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Button ok = (Button) findViewById(R.id.button_sign_up);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                tvIsConnected = (TextView) findViewById(R.id.textView2);
                name = ((EditText) findViewById(R.id.name_signup)).getText().toString();
                email = ((EditText) findViewById(R.id.email_signup)).getText().toString();
                password = ((EditText) findViewById(R.id.pass_signup)).getText().toString();
                password_confirmation = ((EditText) findViewById(R.id.repassword)).getText().toString();
                signupPost = (Button) findViewById(R.id.button_sign_up);

                if (name.equals("") || email.equals("") || password.equals(""))
                    Toast.makeText(NewAccountActivity.this, R.string.error_all_fields_are_required, Toast.LENGTH_LONG).show();
                else if (!password.equals(password_confirmation))
                    Toast.makeText(NewAccountActivity.this, R.string.preferences_passwords_do_not_match, Toast.LENGTH_LONG).show();
                else
                    new HttpAsyncTask().execute("https://myiorderapp.herokuapp.com/api/v1/users/signup");
                    Intent intent = new Intent(NewAccountActivity.this, ChooseShopActivity.class);
                    startActivity(intent);


                // check if you are connected or not
                if (isConnected()) {
                    tvIsConnected.setBackgroundColor(0xFF00CC00);
                    tvIsConnected.setText("You are connected");


                } else {
                    tvIsConnected.setText("You are NOT connected");
                }

                //signupPost.setOnClickListener(this);
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
            jsonObject.accumulate("name", users.getName());
            jsonObject.accumulate("email", users.getEmail());
            jsonObject.accumulate("password", users.getPassword());
            jsonObject.accumulate("password_confirmation", users.getRepassword());

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

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            user = new User();
            user.setName(name.toString());
            user.setEmail(email.toString());
            user.setPassword(password.toString());
            user.setRepassword(password_confirmation.toString());

            return POST(urls[0], user);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

            //tvIsConnected.setText(name.getText().toString());
            //tvIsConnected.setText(email.getText().toString());
            //tvIsConnected.setText(password.getText().toString());
            tvIsConnected.setText(result.toString());
        }


    /*private boolean validate() {

        if(name.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(this, R.string.error_all_fields_are_required, Toast.LENGTH_LONG).show();
            return true;
        }
        if (!password.equals(password_confirmation)) {
            Toast.makeText(this, R.string.preferences_passwords_do_not_match, Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return false;
        }
    }*/

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
//*******************************
//Custom iOrder Code ************
//*******************************

        //connect NewAccountActivity with OrderActivity
   /* public void signUp(View view)
    {

        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}*/
