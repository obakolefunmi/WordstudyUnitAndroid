package com.cuwordstudy.solomolaiye.wordstudyunit.Helpers;

import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Xml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class HttpDataHandler {
    static String stream = null;
    public HttpDataHandler() { }

    public String GetHttpData (String UrlString)
    {
        try
        {
            URL url = new URL(UrlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            if (urlConnection.getResponseCode() == 200)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
            }
        }
        catch (Exception ex) { }
        return stream;

    }

   // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void PostHttpData (String urlstring, String json)
    {
        try
        {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            byte[] _out = json.getBytes();
            int length = _out.length;
            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset-UTF-8");
           // urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            try{
                OutputStream str= urlConnection.getOutputStream();

                str.write(_out, 0, length);
            }catch(Exception ex) { }
            InputStream response = urlConnection.getInputStream();

        }catch(Exception ex) { }
    }
    public void PutHttpData(String urlstring, String newValue)
    {
        try
        {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);


            byte[] _out = newValue.getBytes();
            int length = _out.length;
            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset-UTF-8");
            // urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            try{OutputStream str= urlConnection.getOutputStream();


                str.write(_out, 0, length);
            }catch(Exception ex) { }
            InputStream response = urlConnection.getInputStream();

        }catch(Exception ex) { }
    }

    public void DeleteHttpData(String urlstring, String json)
    {
        try
        {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);

            byte[] _out = json.getBytes();
            int length = _out.length;
            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset-UTF-8");
            // urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            try{ OutputStream str= urlConnection.getOutputStream();


                str.write(_out);
            }catch(Exception ex) { }
            InputStream response = urlConnection.getInputStream();

        }catch(Exception ex) { }
    }


    }




