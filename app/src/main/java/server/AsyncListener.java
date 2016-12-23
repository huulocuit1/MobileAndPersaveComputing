package server;


import java.io.IOException;

public interface AsyncListener {
    void onAsyncComplete() throws IOException;
}
