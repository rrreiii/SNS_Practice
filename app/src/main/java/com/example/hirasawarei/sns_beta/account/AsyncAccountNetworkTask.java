package com.example.hirasawarei.sns_beta.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;

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
 * Created by hirasawarei on 10/08/17.
 */

public class AsyncAccountNetworkTask extends AsyncTask<String, Void, String> {


    Activity activity;
    String userId;
    String userName;

    public AsyncAccountNetworkTask(Activity activity) {
        this.activity = activity ;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String method = params[0];

        if (method.equals("Registration")) {
            String userName = params[1];
            String email = params[2];
            String password = params[3];
            String comment = params[4];
            String siteURL = params[5];
            String imageName = params[6];
            String imageString = params[7];

            try {
                URL url = new URL("http://reiman.php.xdomain.jp/SNS_Beta/Register_Connection.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setConnectTimeout(3000);
                OutputStream os = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(comment, "UTF-8") + "&" +
                        URLEncoder.encode("siteURL", "UTF-8") + "=" + URLEncoder.encode(siteURL, "UTF-8") + "&" +
                        URLEncoder.encode("imageName", "UTF-8") + "=" + URLEncoder.encode(imageName, "UTF-8") + "&" +
                        URLEncoder.encode("imageString", "UTF-8") + "=" + URLEncoder.encode(imageString, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String result = stringBuilder.toString();
                Log.d("post result", "doInBackground:" + result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

                //ここでただサクセスかえすんじゃなくて、インサートの成功の可否で分岐させたい
                //phpの方になんかしら関数加えて加えてバッファリーダーで受け取って分岐かな

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (method.equals("Login")) {
            String email = params[1];
            String password = params[2];
            try {
                URL url = new URL("http://reiman.php.xdomain.jp/SNS_Beta/Login_Connection.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(3000);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                String jsonString = stringBuilder.toString();

                if (jsonString == "error") {
                    return "error";
                }

                JSONObject jsonObject = new JSONObject(jsonString);
                String result = jsonObject.getString("result");
                userId = jsonObject.getString("UserId");
                userName = jsonObject.getString("UserName");

                Log.d("Login", "UserId: " + userId);
                Log.d("Login", "UserName: " + userName);

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }


    @Override
    protected void onPostExecute(String result) {


//        if (result.equals("Registration is success")) {
//            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
//            ((LoginActivity) activity).setViewPager(0);
//        } else {


        if (result.equals("registration success")) {
            ((LoginActivity) activity).setViewPager(0);
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
            
        } else if (result.equals("login success")) {
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
            SharedPreferences data = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = data.edit();
            editor.putString("UserId",userId);
            editor.putString("UserName",userName);
            editor.apply();
            Intent intent = new Intent(activity, TimeLineActivity.class);
            activity.startActivity(intent);
            

        } else {
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
        }

//        }
    }


}
