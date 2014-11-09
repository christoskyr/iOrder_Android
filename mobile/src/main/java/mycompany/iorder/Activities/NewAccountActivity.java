package mycompany.iorder.Activities;

/*------------------------------

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


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
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}
*/





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import mycompany.iorder.AccountData;
import mycompany.iorder.Logger;
import mycompany.iorder.Exceptions.iOrderException;
import mycompany.iorder.net.WebServices;
import mycompany.iorder.AccountData;
import mycompany.iorder.Logger;
import mycompany.iorder.R;


public class NewAccountActivity extends Activity {

    private static final int ERROR = 1;
    private String name= null;
    private String password = null;
    private String repassword = null;
    private String email = null;
    private String error = null;



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

    private class CreateAccount extends AsyncTask<String, Void, Void> {

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
        protected Void doInBackground(String... data) {
            try {
                AccountData accountData = WebServices.getInstance().registerNewAccount(NewAccountActivity.this, data[0], data[1], data[2]);
                Logger.d("Response creating account: " + accountData.toString());
                getConfig().saveAccount(accountData);
            } catch (iOrderException e) {
                error = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            try{
                progressDialog.dismiss();
            }catch(Exception e){
            }
            if (error == null) {
                String message = getString(R.string.new_account_congratulations_text, email);
                Bundle bundle = new Bundle();
                bundle.putString("message", message);
                Intent intent = new Intent(NewAccountActivity.this, PermissionInformationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else
                showDialog(ERROR);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog pass = null;
        switch (id) {

            case ERROR:
                return new AlertDialog.Builder(NewAccountActivity.this).setIcon(R.drawable.error).setTitle(R.string.error_title).setMessage(error)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setCancelable(false).create();
        }
        return pass;
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