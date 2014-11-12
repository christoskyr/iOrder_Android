package mycompany.iorder.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;


import mycompany.iorder.R;


import android.view.View;
import android.content.Intent;

public class Sign_inActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


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

    //connect Sign_inActivity with OrderActivity
    public void sign_in(View view)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}
