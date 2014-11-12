package models;

/**
 * Created by iOrder on 12/11/2014.
 */

import java.util.ArrayList;
import java.util.List;


public class StoresList {

    private List<Store> stores = new ArrayList<Store>();

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public StoresList withStores(List<Store> stores) {
        this.stores = stores;
        return this;
    }

}