package com.example.hirasawarei.sns_beta.myPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedInputStream;
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
import java.nio.charset.Charset;

/**
 * Created by hirasawarei on 18/08/17.
 */

public class Downloader extends AsyncTask<Void,Void,String>{

    Context ctx;
    String urlAddress;
    GridView gv;
    String userId;

    public Downloader(Context ctx, String urlAddress, GridView gv, String userId){
        this.ctx = ctx;
        this.urlAddress = urlAddress;
        this.gv = gv;
        this.userId = userId;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        if(jsonData==null)
        {
            Toast.makeText(ctx, "Unsuccessful, No Data retrieved", Toast.LENGTH_SHORT).show();
        }else {
            //Parse
            DataParser parser = new DataParser(ctx,jsonData,gv);
            parser.execute();

        }

    }


    private String downloadData()
    {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            String data = URLEncoder.encode("UserId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();


            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuffer jsonData = new StringBuffer();
            String line;

            while((line = bufferedReader.readLine() ) != null){
                jsonData.append(line + "\n");
            }
            return jsonData.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}