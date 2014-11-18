package mycompany.iorder.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import mycompany.iorder.R;

public class ProductDetails extends Order_Activity {

    int i=1;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

            Intent in = getIntent();
            position = in.getExtras().getInt("position");
            final String title = in.getStringExtra("title");
            final TextView mTextView2 = (TextView) findViewById(R.id.title);
            mTextView2.setText(title);

            /*if(getIntent().getStringExtra("title")!=null){

                final TextView mTextView2 = (TextView) findViewById(R.id.title);

                Intent in = getIntent();
                final String title = in.getStringExtra(("title"));
                mTextView2.setText(title);

            }*/


            final TextView mTextView3 = (TextView) findViewById(R.id.price);

            Intent intent = getIntent();
            //final String price[] = intent.getStringArrayExtra("price");
            mTextView3.setText(Integer.toString(position));

/*
            final TextView mTextView4 = (TextView) findViewById(R.id.costNum);

            Intent intent2 = getIntent();
            String cost = intent2.getStringExtra(("price"));
            mTextView4.setText(cost + "€");

            int myNum = 1;

            try {

                myNum = Integer.parseInt(mTextView4.getText().toString());
            } catch (NumberFormatException nfe) {

            }

            final TextView mTextView = (TextView) findViewById(R.id.counter);
            mTextView.setText(Integer.toString(i));

            final ImageButton button = (ImageButton) findViewById(R.id.imageButton);

            final int finalMyNum = myNum;
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    i = (i + 1);
                    int temp = i;
                    temp = temp * finalMyNum;
                    mTextView.setText(Integer.toString(i));
                    mTextView4.setText(Integer.toString(temp) + "€");
                }
            });

            final ImageButton button2 = (ImageButton) findViewById(R.id.imageButton2);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    if (i > 1) {
                        i = i - 1;
                        mTextView.setText(Integer.toString(i));
                        int temp = i;
                        temp = temp * finalMyNum;
                        mTextView4.setText(Integer.toString(temp) + "€");
                    }

                }
            });

            Button addOrder = (Button) findViewById(R.id.addToOrder);
            addOrder.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Intent intent = new Intent(ProductDetails.this, FinalOrder.class);
                    intent.putExtra("title", title);
                    intent.putExtra("price", price);
                    intent.putExtra("quantity", Integer.toString(i));
                    startActivity(intent);
                }
            });*/

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_details, menu);
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

    //connect ProductDetails with FinalOrder Activity
    /*public void Add_to_my_order(View view)
    {

        Intent intent = new Intent(this, FinalOrder.class);

        startActivity(intent);
    }*/
    /*public void Back(View view)
    {
        super.onBackPressed();

        //Intent intent = new Intent(this, Order_Activity.class);

        //startActivity(intent);
    }*/


    public void noSugar(View view)
    {

        final CheckBox checkBox = (CheckBox) findViewById(R.id.noSugar);
        if (checkBox.isChecked()){

            String sugarChoice = "noSugar";

        }
    }

    public void mediumSugar(View view)
    {

        final CheckBox checkBox = (CheckBox) findViewById(R.id.noSugar);
        if (checkBox.isChecked()){

            String sugarChoice = "mediumSugar";

        }
    }

    public void sweetSugar(View view)
    {

        final CheckBox checkBox = (CheckBox) findViewById(R.id.noSugar);
        if (checkBox.isChecked()){

            String sugarChoice = "sweetSugar";

        }
    }

    public void verySweetSugar(View view)
    {

        final CheckBox checkBox = (CheckBox) findViewById(R.id.noSugar);
        if (checkBox.isChecked()){

            String sugarChoice = "verySweetSugar";

        }
    }

}
