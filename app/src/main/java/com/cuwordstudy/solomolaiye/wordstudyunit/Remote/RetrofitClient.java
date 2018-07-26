package com.cuwordstudy.solomolaiye.wordstudyunit.Remote;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class RetrofitClient {
    public void PostHttpData (String urlstring, String json)
    {
        try
        {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("Authorization","key=AAAAJg9f1GA:APA91bFrCxlaYUnqgTL3CNs-SHsDwoioCtrJTzzLoMpUgYW-F3XOlHSe9zsLl2bqJxOpIkppGHQeagwUzX5GxN70WNq2FbCTZO9XUOOlLkk6DRRrWJj61xSiCfkEP65qTS6Gu7cZ6sHk");
            urlConnection.setDoOutput(true);
            byte[] _out = json.getBytes(StandardCharsets.UTF_8);
            int length = _out.length;
            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset-UTF-8");
            // urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.connect();
            try( OutputStream str= urlConnection.getOutputStream())
            {

                str.write(_out, 0, length);
            }catch(Exception ex) { }
            InputStream response = urlConnection.getInputStream();

        }catch(Exception ex) { }
    }

   }
