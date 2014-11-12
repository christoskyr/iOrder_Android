package mycompany.iorder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mycompany.iorder.R;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    //connect LoginActivity with NewAccountActivity
    public void newAccount(View view)
    {
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
    }

    //connect LoginActivity with Sign_inActivity
    public void sign_in_via_iorder(View view)
    {
        Intent intent = new Intent(this, Sign_inActivity.class);
        startActivity(intent);
    }
}
