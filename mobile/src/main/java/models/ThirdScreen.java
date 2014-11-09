package models;

/**
 * Created by iOrder on 24/10/2014.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import mycompany.iorder.Controller;
import mycompany.iorder.R;

public class ThirdScreen extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdscreen);

        TextView showCartContent    = (TextView) findViewById(R.id.showCart);

        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        int cartSize = aController.getCart().getCartSize();

        String showString = "";

/******** Show Cart Products on screen - Start ********/

        for(int i=0;i<cartSize;i++)
        {
            //Get product details
            String pName    = aController.getCart().getProducts(i).getProductName();
            int pPrice      = aController.getCart().getProducts(i).getProductPrice();
            String pDisc    = aController.getCart().getProducts(i).getProductDesc();

            showString += "Product Name : "+pName+"\n\n"+ "Price : "+pPrice+"\n\n"+ "Description : "+pDisc+"\n\n"+ "-----------------------------------";
        }


        showCartContent.setText(showString);

/******** Show Cart Products on screen - End ********/

    }
}
