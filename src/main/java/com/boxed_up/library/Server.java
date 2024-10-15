package com.boxed_up.library;

import com.sun.net.httpserver.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import java.io.IOException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Server {

    public static final int THREAD_POOL_SIZE = 20;

    HttpServer server;

    public Server() throws IOException {
        server = HttpServer.create(new InetSocketAddress(80), 0);
    
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        server.setExecutor(threadPool);
        server.start();

        OutputLog.getInstance().log("Http Server Started");
    }

    public void createContext(String path, HttpHandler handler){
        server.createContext(path,handler);
    }

    public void close(){
        server.stop(0);
        OutputLog.getInstance().log("Http Server Closed");
    }

    public static void generateQR(String options) throws IOException, UnknownHostException, URISyntaxException {
        String ipAddr = InetAddress.getLocalHost().getHostAddress();

        //InetAddress.getLoopbackAddress().getHostAddress();

        URL serverUrl = new URI("https://quickchart.io/qr?text=http://"+ipAddr+"/&"+options).toURL();
        HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

        // Get a readable channel from url connection
        ReadableByteChannel httpResponseBody = Channels.newChannel(urlConnection.getInputStream());

        // Create the file channel to save file
        FileOutputStream destination = new FileOutputStream("qrcode.png");
        FileChannel fileChannelForDownloadedFile = destination.getChannel();

        // Save the contents of HTTP response to local file
        fileChannelForDownloadedFile.transferFrom(httpResponseBody, 0, Long.MAX_VALUE);

        destination.close();
    }
}
