package mycompany.iorder.net;

/**
 * Created by iOrder on 11/11/2014.
 */

import java.io.IOException;
import java.io.InputStream;

public class Utils {


    public static void closeStreamQuietly(InputStream inputStream) {

        try {

            if (inputStream != null) {

                inputStream.close();

            }

        } catch (IOException e) {

            // ignore exception

        }

    }
}
