package models;

import java.util.ArrayList;
import java.util.List;

public class Stores {

    private List<Store> stores = new ArrayList<Store>();

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public Stores withStores(List<Store> stores) {
        this.stores = stores;
        return this;
    }

}