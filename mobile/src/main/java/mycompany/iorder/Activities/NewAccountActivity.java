package mycompany.iorder.Activities;

/*

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mycompany.iorder.Jason.JsonParsingActivity;
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

    //connect Register with Order Activity
    public void signUp(View view)
    {
        Intent intent = new Intent(this, JsonParsingActivity.class);
        startActivity(intent);
    }
}


*/


/*------------------------------------------------FOLLOWING https://github.com/prey/prey-android-client/tree/master/src/com/prey/json/parser-------------------------------*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

import mycompany.iorder.R;
import mycompany.iorder.JSONParser;
import mycompany.iorder.Jason.Result;
import models.Products;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


public class NewAccountActivity extends Activity {

    private static final int ERROR = 1;
    private String name= null;
    private String password = null;
    private String repassword = null;
    private String email = null;
    private String error = null;

    private NewAccount newAccountInstance = new NewAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);




        Button ok = (Button) findViewById(R.id.button_sign_up);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                name = ((EditText) findViewById(R.id.name_signup)).getText().toString();
                email = ((EditText) findViewById(R.id.email_signup)).getText().toString();
                password = ((EditText) findViewById(R.id.pass_signup)).getText().toString();
                repassword = ((EditText) findViewById(R.id.repassword)).getText().toString();

                if (name.equals("") || email.equals("") || password.equals(""))
                    Toast.makeText(NewAccountActivity.this, R.string.error_all_fields_are_required, Toast.LENGTH_LONG).show();
                else if (!password.equals(repassword))
                    Toast.makeText(NewAccountActivity.this, R.string.preferences_passwords_do_not_match, Toast.LENGTH_LONG).show();
                else {
                    new CreateAccount().execute(name, email, password);
                }
            }
        });

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

    }

    private class CreateAccount extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(NewAccountActivity.this);
            progressDialog.setMessage(NewAccountActivity.this.getText(R.string.creating_account_please_wait).toString());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... data) {

            return newAccountInstance.returnJSONObj();

        }

        @Override
        protected void onPostExecute(JSONObject unused) {


            this.progressDialog.cancel();

            //Toast.makeText(this, Products.getProductName(), Toast.LENGTH_LONG).show();
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
    }

//*******************************
//Custom iOrder Code ************
//*******************************

    //connect Register with Order Activity
    public void signUp(View view)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}