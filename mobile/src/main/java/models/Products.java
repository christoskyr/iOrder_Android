package models;

/**
 * Created by iOrder on 23/10/2014.
 */
public class Products {

    private String productName;
    private String productDesc;
    private int productPrice;

    public Products(String productName,String productDesc,int productPrice)
    {
        this.productName  = productName;
        this.productDesc  = productDesc;
        this.productPrice = productPrice;
    }


    public String getProductName() {

        return productName;
    }

    public String getProductDesc() {

        return productDesc;
    }

    public int getProductPrice() {

        return productPrice;
    }
}
