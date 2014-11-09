package mycompany.iorder.Exceptions;

/**
 * Created by iOrder on 9/11/2014.
 */
public class iOrderException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param detailMessage
     * @param throwable
     */
    public iOrderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        // TODO Auto-generated constructor stub
    }

    public iOrderException(String message) {
        super(message);
    }

}
