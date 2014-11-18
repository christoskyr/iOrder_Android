package mycompany.iorder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import mycompany.iorder.R;

public class ChooseShopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shop);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_shop, menu);
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

    //connect ChooseShopActivity with OrderActivity
    public void stateShop1(View view)
    {

        final CheckBox checkBox = (CheckBox) findViewById(R.id.stateShop1);
        if (checkBox.isChecked()){

            Intent intent = new Intent(this, Order_Activity.class);
            startActivity(intent);

        }

        //Intent intent = new Intent(this, OrderActivity.class);
        //startActivity(intent);
    }


    //connect ChooseShopActivity with OrderActivity
    public void stateShop2(View view)
    {


        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.stateShop2);
        if(checkBox2.isChecked()){

            Intent intent = new Intent(this, Order_Activity.class);
            startActivity(intent);

        }
    }

}
