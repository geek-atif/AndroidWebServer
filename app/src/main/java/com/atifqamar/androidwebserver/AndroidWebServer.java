package com.atifqamar.androidwebserver;

import android.content.Context;
import fi.iki.elonen.NanoHTTPD;

public class AndroidWebServer extends NanoHTTPD {
    private String TAG = "com.atifqamar.androidwebserver.AndroidWebServer";
    private Context context;

    public AndroidWebServer(int port , Context  mContext) {
        super(port);
        this.context = mContext;
    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        return NanoHTTPD.newFixedLengthResponse("Welcome to Android Local Server");
    }

}
