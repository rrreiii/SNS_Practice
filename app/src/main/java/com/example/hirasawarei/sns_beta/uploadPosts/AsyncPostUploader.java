package com.example.hirasawarei.sns_beta.uploadPosts;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hirasawarei on 2017/09/21.
 */

public class AsyncPostUploader extends AsyncTask<String, Integer, String> {

    Activity activity;
    ProgressBar progressBar;
    String encodedImageString, userId,userName,  caption, postedTime, imgName;
    String data;
    String imageUploadConnection = "http://reiman.php.xdomain.jp/SNS_Beta/UploadImageConnecter.php";


    public AsyncPostUploader(Activity activity) {
        this.activity = activity;
        progressBar = (ProgressBar) activity.findViewById(R.id.aProgressBar);
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }
    @Override
    protected String doInBackground(String... params) {

        encodedImageString = params[0];
        userId = params[1];
        userName = params[2];
        caption = params[3];
        postedTime = params[4];
        imgName = params[5];


        try {
            URL url = new URL(imageUploadConnection);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            data = URLEncoder.encode("encodedImageString", "UTF-8") + "=" + URLEncoder.encode(encodedImageString, "UTF-8") + "&" +
                    URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
                    URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                    URLEncoder.encode("caption", "UTF-8") + "=" + URLEncoder.encode(caption, "UTF-8") + "&" +
                    URLEncoder.encode("postedTime", "UTF-8") + "=" + URLEncoder.encode(postedTime, "UTF-8") + "&" +
                    URLEncoder.encode("imgName", "UTF-8") + "=" + URLEncoder.encode(imgName, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(e.toString(), e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(ProgressBar.GONE);

    }
}
