package mycompany.iorder.Activities;

/*  Arxiko arxeio

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mycompany.iorder.R;

public class NewAccountActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_account, menu);
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

    //connect NewAccountActivity with Order Activity
    public void signUp(View view)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}


*/


/*------------------------------------------------FOLLOWING http://hmkcode.com/android-send-json-data-to-server/-------------------------------*/
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

import android.view.View.OnClickListener;
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
                    new HttpAsyncTask().execute("https://iorderweb.herokuapp.com/api/v1/users");


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
/*
        //Hack to fix password hint's typeface: http://stackoverflow.com/questions/3406534/password-hint-font-in-android
        EditText password = (EditText) findViewById(R.id.pass_signup);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        password = (EditText) findViewById(R.id.repassword);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        //To avoid setting these Imeoptions on each layout :)
        EditText name = (EditText) findViewById(R.id.name_signup);
        name.setImeOptions(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS | EditorInfo.TYPE_TEXT_VARIATION_PERSON_NAME);

        EditText email = (EditText) findViewById(R.id.email_signup);
        email.setImeOptions(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

    }*/

   /* private class CreateAccount extends AsyncTask<String, String, String> {

        //ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            //progressDialog = new ProgressDialog(NewAccountActivity.this);
            //progressDialog.setMessage(NewAccountActivity.this.getText(R.string.creating_account_please_wait).toString());
            //progressDialog.setIndeterminate(true);
            //progressDialog.setCancelable(false);
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return "Excecuted";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.textView2);
            txt.setText("Excecuted"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_account, menu);
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
    }*/

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

    //@Override
    /*public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_sign_up:

                new HttpAsyncTask().execute("https://iorderweb.herokuapp.com/api/v1/users");

        }
        //Intent intent = new Intent(this, OrderActivity.class);
        //startActivity(intent);


    }*/

    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            user = new User();
            user.setName(name.toString());
            user.setEmail(email.toString());
            user.setPassword(password.toString());
            user.setRepassword(password_confirmation.toString());

            return POST("https://iorderweb.herokuapp.com/api/v1/users", user);
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
