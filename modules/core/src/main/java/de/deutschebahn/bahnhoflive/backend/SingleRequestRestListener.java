package de.deutschebahn.bahnhoflive.backend;

import com.android.volley.Request;
import com.android.volley.VolleyError;

public class SingleRequestRestListener<T> extends BaseRestListener<T> {

    private Request<T> request;

    public void setRequest(Request<T> request) {
        this.request = request;
    }

    @Override
    public void onSuccess(T payload) {
        notifyRequestFinished();
    }

    protected final void notifyRequestFinished() {
        if (request != null) {
            onRequestFinished(request);
        }
    }

    protected void onRequestFinished(Request<T> request) {

    }

    @Override
    public void onFail(VolleyError reason) {
        notifyRequestFinished();
    }
}
