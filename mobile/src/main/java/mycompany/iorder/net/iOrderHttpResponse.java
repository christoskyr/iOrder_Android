package mycompany.iorder.net;

/**
 * Created by iOrder on 9/11/2014.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import mycompany.iorder.Logger;

public class iOrderHttpResponse {

    private StatusLine statusLine;
    private String responseAsString;
    private HttpResponse response;

    // public PreyHttpResponse(HttpMethod method){
    // this.statusLine = method.getStatusLine();
    //
    // try {
    // this.responseAsString =
    // convertStreamToString(method.getResponseBodyAsStream());
    // } catch (IOException e) {
    // Log.d(
    // "Can't receive body stream from http connection, setting response string as ''");
    // this.responseAsString = "";
    // }
    // }

    public iOrderHttpResponse(HttpResponse response) {
        this.response=response;
        try {
            if(response!=null){
                this.statusLine = response.getStatusLine();
                this.responseAsString = convertStreamToString(response.getEntity().getContent());
            }else{
                this.responseAsString = "";
            }
        } catch (IOException e) {
            Logger.d("Can't receive body stream from http connection, setting response string as ''");
            this.responseAsString = "";
        }
    }

    public iOrderHttpResponse(InputStream responseStream, StatusLine statusLine) {
        this.statusLine = statusLine;
        this.responseAsString = convertStreamToString(responseStream);
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getResponseAsString() {
        return responseAsString;
    }

    @Override
    public String toString() {

        return statusLine.toString() + " " + responseAsString;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

}*/
