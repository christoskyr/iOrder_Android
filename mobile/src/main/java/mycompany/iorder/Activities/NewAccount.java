package mycompany.iorder.Activities;

import org.json.JSONObject;

import mycompany.iorder.JSONParser;
import mycompany.iorder.net.HttpRetriever;

/**
 * Created by iOrder on 11/11/2014.
 */
public class NewAccount {

    private static final String IORDER_API_BASE_URL = "https://iorderweb.herokuapp.com/api/v1/products";

    private HttpRetriever httpRetriever = new HttpRetriever();
    private JSONParser jsonParser = new JSONParser();

    public JSONObject returnJSONObj()
    {
        return jsonParser.getJSONFromUrl(IORDER_API_BASE_URL);
    }



 }
