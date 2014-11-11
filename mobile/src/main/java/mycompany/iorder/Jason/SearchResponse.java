package mycompany.iorder.Jason;

/**
 * Created by iOrder on 10/11/2014.
 */

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class SearchResponse {

    public List<Result> results;

    @SerializedName("products_Id")
    public int productsId;

    @SerializedName("products_Title")
    public int productsTitle;

    @SerializedName("products_Price")
    public int productsPrice;

    public String query;
    public int page;



}
