package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import mycompany.iorder.R;

public class SingleProductActivity extends Activity {

    // JSON node keys
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PRICE = "price";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_ID);
        String cost = in.getStringExtra(TAG_TITLE);
        String description = in.getStringExtra(TAG_PRICE);

        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.id);
        TextView lblCost = (TextView) findViewById(R.id.title);
        TextView lblDesc = (TextView) findViewById(R.id.price);

        lblName.setText(name);
        lblCost.setText(cost);
        lblDesc.setText(description);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_product, menu);
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
}
