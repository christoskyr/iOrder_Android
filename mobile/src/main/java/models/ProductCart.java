package models;

import java.util.ArrayList;
/**
 * Created by iOrder on 23/10/2014.
 */
public class ProductCart {

    private  ArrayList<Products> cartProducts = new ArrayList<Products>();


    public Products getProducts(int pPosition) {

        return cartProducts.get(pPosition);
    }

    public void setProducts(Products Products) {

        cartProducts.add(Products);

    }

    public int getCartSize() {

        return cartProducts.size();

    }

    public boolean checkProductInCart(Products aProduct) {

        return cartProducts.contains(aProduct);

    }
}
