package com.boxed_up.library;

import java.net.URL;

public class FileSystem {
    
    public static URL getFile(String filePath){
        return Thread.currentThread().getContextClassLoader().getResource(filePath);
    }

    public static URL getFile(String parent, String child){
        return getFile(parent+"/"+child);
    }
}