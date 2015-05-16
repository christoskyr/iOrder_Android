package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import models.Product;
import models.iOrder;
import mycompany.iorder.R;

public class ProductDetails extends Order_Activity {

    private int i = 1;
    private int counter = 1;
    private static String title, price, cost, productId, sugarChoice;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4;
    private static double temp, myNum, finalMyNum;
    private static final String TAG_ID = "id";
    private ImageButton button, button2;
    private Button addOrder;
    private CheckBox noSugar, medSweet, sweet, verySweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        medSweet = (CheckBox) findViewById(R.id.mediumSweet);
        sweet = (CheckBox) findViewById(R.id.sweet);
        verySweet = (CheckBox) findViewById(R.id.verySweet);
        noSugar = (CheckBox) findViewById(R.id.noSugar);
        mTextView1 = (TextView) findViewById(R.id.counter);
        mTextView2 = (TextView) findViewById(R.id.title);
        mTextView3 = (TextView) findViewById(R.id.price);
        mTextView4 = (TextView) findViewById(R.id.costNum);
        button = (ImageButton) findViewById(R.id.imageButton);
        button2 = (ImageButton) findViewById(R.id.imageButton2);
        addOrder = (Button) findViewById(R.id.addToOrder);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        productId = intent.getStringExtra("id");
        price = intent.getStringExtra("price");
        cost = intent.getStringExtra(("price"));
        mTextView1.setText(Integer.toString(i));
        mTextView4.setText(cost);
        mTextView2.setText(title);
        mTextView3.setText(price);
        myNum = Double.parseDouble(mTextView4.getText().toString());
        finalMyNum = myNum;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Perform action on click
                i = (i + 1);
                counter = i;

                temp = Double.parseDouble(Integer.toString(i)) * finalMyNum;
                mTextView1.setText(Integer.toString(i));
                mTextView4.setText(Double.toString(temp) + "€");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (i > 1) {
                    i = i - 1;
                    counter = i;
                    mTextView.setText(Integer.toString(i));
                    double temp = i;
                    temp = temp * finalMyNum;
                    mTextView4.setText(Double.toString(temp) + "€");
                }
            }
        });

        noSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noSugar();
            }
        });
        medSweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediumSugar();
            }
        });
        verySweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verySweetSugar();
            }
        });
        sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweetSugar();
            }
        });

        addOrder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Add_to_my_order();
            }
        });
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Toast.makeText(getApplicationContext(), "16. onDestroy()", Toast.LENGTH_SHORT).show();

        title = null;
        price = null;

        mTextView2.setText(title);
        mTextView3.setText(price);
        mTextView4.setText("");

    }

    @Override
    public void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "5. onRestart()", Toast.LENGTH_SHORT).show();
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


    //connect ProductDetails with FinalOrder Activity
    public void Add_to_my_order() {
        Intent intent = new Intent(this, FinalOrder.class);

        ArrayList<Product> tempList;
        iOrder iorder = ((iOrder) getApplicationContext());
        Product productToOrder = new Product();
        productToOrder.setDetails(sugarChoice);
        productToOrder.setQuantity(Integer.toString(counter));
        productToOrder.setPrice(price);
        productToOrder.setTitle(title);
        productToOrder.setProductId(productId);
        if (iorder.getProducts() == null)
            tempList = new ArrayList<Product>();
        else
            tempList = iorder.getProducts();
        tempList.add(productToOrder);
        iorder.setProducts(tempList);

        startActivity(intent);
    }

    public void Back(View view) {
        super.onBackPressed();

        //Intent intent = new Intent(this, Order_Activity.class);
        //startActivity(intent);
    }


    public void noSugar() {
        if (noSugar.isChecked()) {
            sugarChoice = "No Sugar";
            medSweet.setChecked(false);
            sweet.setChecked(false);
            verySweet.setChecked(false);
        }
    }

    public void mediumSugar() {
        if (medSweet.isChecked()) {
            sugarChoice = "Medium Sugar";
            sweet.setChecked(false);
            verySweet.setChecked(false);
            noSugar.setChecked(false);
        }
    }

    public void sweetSugar() {
        if (sweet.isChecked()) {
            sugarChoice = "Sweet Sugar";
            medSweet.setChecked(false);
            verySweet.setChecked(false);
            noSugar.setChecked(false);
        }
    }

    public void verySweetSugar() {
        if (verySweet.isChecked()) {
            sugarChoice = "Very Sweet Sugar";
            medSweet.setChecked(false);
            sweet.setChecked(false);
            noSugar.setChecked(false);
        }
    }

}
