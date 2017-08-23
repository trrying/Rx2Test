package com.owm.rx2test.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by owm on 2017/8/23.
 */

public class NetUtils {

    public static String connection(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10 * 1000);
        connection.setRequestMethod("GET");
        connection.connect();
        if (connection.getResponseCode() == 200) {
            throw new Exception("response code == 200");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = connection.getInputStream();
        byte[] buffer = new byte[1024*8];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0 , length);
        }
        outputStream.flush();
        return new String(outputStream.toByteArray());
    }

}
