package com.example.hirasawarei.sns_beta.search;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

public class SearchUserId extends AsyncTask<String, Void, String> {

    String enteredValue;
    Activity activity;

    public SearchUserId(Activity activity){
        this.activity = activity;
    }



    @Override
    protected String doInBackground(String... params) {
        enteredValue = params[0];

        try {
            URL url = new URL("http://reiman.php.xdomain.jp/SNS_Beta/SearchConnecter.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(3000);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("value", "UTF-8") + "=" + URLEncoder.encode(enteredValue, "UTF-8");
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

            if (result == "error") {
                return "error";
            }


            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("error")){
            Toast.makeText(activity, "can't find the user", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(activity, ProfileActivity.class);
            intent.putExtra("UserId", result);
            activity.startActivity(intent);
        }
    }


}
