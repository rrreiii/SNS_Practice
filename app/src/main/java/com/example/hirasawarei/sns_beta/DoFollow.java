package com.example.hirasawarei.sns_beta;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hirasawarei on 2017/09/29.
 */

public class DoFollow extends AsyncTask<String, Void, String> {

    Activity activity;
    String followOrNot;
    String idFollowing;
    String idFollowed;
    String url = "http://reiman.php.xdomain.jp/SNS_Beta/DoFollowConnecter.php";



    public DoFollow(Activity activity){
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... params) {
        followOrNot = params[0];
        idFollowing = params[1];
        idFollowed = params[2];
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(3000);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("followOrNot", "UTF-8") + "=" + URLEncoder.encode(followOrNot, "UTF-8") +"&"+
                    URLEncoder.encode("idFollowing", "UTF-8") + "=" + URLEncoder.encode(idFollowing, "UTF-8") +"&"+
                    URLEncoder.encode("idFollowed", "UTF-8") + "=" + URLEncoder.encode(idFollowed, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();

            if (result.equals("success")) {
                return "success";
            }else{
                return "failure";

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("success")){
            ((TextView)activity.findViewById(R.id.mBtEdit)).setText("Already Followed");
        }else{
        }

    }


}
