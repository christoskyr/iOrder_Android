package mycompany.iorder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;


import com.android.volley.VolleyLog;

import android.app.ProgressDialog;

import java.util.HashMap;

import java.util.Map;

import android.util.Log;

import com.android.volley.Request;

import mycompany.iorder.Controller;
import mycompany.iorder.R;


public class Sign_inActivity extends Activity {

    TextView content;
    EditText email, pass;
    String Name, Email, Login, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        content    =   (TextView)findViewById( R.id.content );

        email      =   (EditText)findViewById(R.id.email_signin);

        pass       =   (EditText)findViewById(R.id.pass_signin);


        Button saveme=(Button)findViewById(R.id.buttonSign_in);

        saveme.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {
                try{

                    // CALL GetText method to make post method call
                    GetText();
                }
                catch(Exception ex)
                {
                    content.setText(" url exeption! " );
                }
            }
        });

    }


    public void GetText()
    {

        final StringBuilder sb = new StringBuilder();
        final String text = "";

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        final String TAG = this.getClass().getSimpleName();
        String url = "https://iorderweb.herokuapp.com/api/v1/stores.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");

                return params;
            }

            /*text = sb.toString();*/


        };

        // Adding request to request queue
        Controller.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Show response on activity
        content.setText( text  );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
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

    //connect iOrderLogin with NewAccountActivity
    public void sign_in(View view)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}
