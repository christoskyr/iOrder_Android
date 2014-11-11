package mycompany.iorder.Jason;

/**
 * Created by iOrder on 10/11/2014.

*/
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("from_products_id_str")
    public String fromProductsIdStr;

    @SerializedName("from_products_title_str")
    public String fromProductsTitleStr;

    @SerializedName("from_products_price_str")
    public String fromProductsPriceStr;

    @SerializedName("from_store")
    public String fromStore;


    public Metadata metadata;
    public String text;
    public long id;
    public String source;

}
