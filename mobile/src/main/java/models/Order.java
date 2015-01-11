package models;

/**
 * Created by ΧΡΗΣΤΟΣ on 17/11/2014.
 */
public class Order {

    private String total_cost;

    public Order(){}

    public Order (String total_cost) {this.total_cost = total_cost;}

    public String getTotal_cost() {return this.total_cost;}

    public void setTotal_cost(String total_cost){
        this.total_cost = total_cost;
    }


}
