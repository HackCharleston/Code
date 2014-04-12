package org.lolt.historiclandscapes.app;
        import android.util.Log;

        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;
        import com.loopj.android.http.TextHttpResponseHandler;

public class Client {
    private final static String BASE_URL = "http://www.johnzeringue.com/lolt/";

    private final static AsyncHttpClient client = new AsyncHttpClient();
    private final static int time= 40000;

    public static void get(String url, RequestParams params) {
        client.setTimeout(time);

        get(url, params, new DebugHttpResponseHandler());
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(time);

        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params) {
        client.setTimeout(time);

        post(url, params, new DebugHttpResponseHandler());
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(time);

        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static class DebugHttpResponseHandler extends
            TextHttpResponseHandler {
        @Override
        public void onSuccess(String content) {
            Log.d(this.getClass().getName(),
                    String.format("Content: %s", content));
        }

        @Override
        public void onFailure(String responseBody, Throwable e) {
            Log.e(this.getClass().getName(), String.format(
                    "Response Body: %s\nError Message: %s", responseBody,
                    e.getMessage()));
        }
    }
}
