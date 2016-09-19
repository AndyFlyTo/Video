package com.gcy.thread;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import confige.Config;

/**
 * Created by chenchen on 16-9-18.
 */
public class HttpSendNameThread implements Runnable{
    private String name;

    public HttpSendNameThread(String name){
        this.name=name;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(Config.URL);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(false);
            conn.setUseCaches(false);
            conn.connect();

//            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            writer.write("name "+"露馅脱\n");
//            Log.d("chen","lohj");
//            writer.flush();
//            writer.close();
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.println("name 露馅脱");
            pw.flush();
            pw.close();
            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str1;
                String message;
                while ((str1 = reader.readLine()) != null) {
                    message = str1;
                }
                reader.close();
            }
            conn.disconnect();
            Log.d("chen","louxiantuo");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }

    }
}
