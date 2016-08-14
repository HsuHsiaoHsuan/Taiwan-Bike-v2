package idv.funnybrain.bike;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by freeman on 8/13/16.
 */

public class DataDownloader {
    private static DataDownloader mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    private DataDownloader(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized DataDownloader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataDownloader(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
