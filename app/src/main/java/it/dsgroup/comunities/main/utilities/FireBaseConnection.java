package it.dsgroup.comunities.main.utilities;
import com.loopj.android.http.*;

/**
 * Created by utente9.academy on 06/12/2017.
 */

public class FireBaseConnection {
    private static final String BASE_URL = "https://comunities-bc5e8.firebaseio.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
