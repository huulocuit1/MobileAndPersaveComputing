package server;

import android.os.AsyncTask;

import java.io.IOException;


public class AsyncWay extends AsyncTask<String, String, String> {
    private AsyncListener listener;
    private String key;

    public AsyncWay(AsyncListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return DataServices.getRequest(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//            DataManager.getInstance().setWayJson(result);
        if (listener != null)
            try {
                listener.onAsyncComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
