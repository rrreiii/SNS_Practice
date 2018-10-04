package com.example.hirasawarei.sns_beta;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.myPage.DataParser;
import com.squareup.picasso.Picasso;

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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hirasawarei on 2017/09/29.
 */

public class AsyncGetAccountInformation extends AsyncTask<String, Void, String> {

    private Activity activity;
    private String method;
    private AccountInformation accountInformation;
    private String connecterURL = "http://reiman.php.xdomain.jp/SNS_Beta/GetAccountConnecter.php";
    private String userId;

    public AsyncGetAccountInformation(Activity activity, String userId ){
        this.activity = activity;
        this.userId = userId;
    }


    @Override
    protected String doInBackground(String... params) {
        this.method = params[0];



        if (method.equals("myPage")) {

            URL url = null;
            try {
                url = new URL(connecterURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(3000);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") +"&"+
                        URLEncoder.encode("UserId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
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
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String jsonString = stringBuilder.toString();

                if (jsonString == "error") {
                    return "error";
                }

                JSONObject jsonObject = new JSONObject(jsonString);
                accountInformation = new AccountInformation();
                accountInformation.setUserId(jsonObject.getString("Id"));
                accountInformation.setUserName(jsonObject.getString("UserName"));
                accountInformation.setComment(jsonObject.getString("Comment"));
                accountInformation.setSiteURL(jsonObject.getString("SiteURL"));
                accountInformation.setProfileImageURL(jsonObject.getString("ProfileImageURL"));
                accountInformation.setPosts(jsonObject.getString("Posts"));
                accountInformation.setFollowings(jsonObject.getString("Followings"));
                accountInformation.setFollowers(jsonObject.getString("Followers"));
                return "success";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "failed to get myPage data";
    }


    @Override
    protected void onPostExecute(String result) {
        Log.d(result, "onPostExecute: " + result);
        ((TextView)activity.findViewById(R.id.mTvTopName)).setText(accountInformation.getUserName());
        ((TextView)activity.findViewById(R.id.mTvName)).setText(accountInformation.getUserName());
        ((TextView)activity.findViewById(R.id.mTvComment)).setText(accountInformation.getComment());
        ((TextView)activity.findViewById(R.id.mTvSiteURL)).setText(accountInformation.getSiteURL());
        ((TextView)activity.findViewById(R.id.mTvPosts)).setText(accountInformation.getPosts());
        ((TextView)activity.findViewById(R.id.mTvFollowers)).setText(accountInformation.getFollowers());
        ((TextView)activity.findViewById(R.id.mTvFollowing)).setText(accountInformation.getFollowings());
        String profileURL = accountInformation.getProfileImageURL();
        Log.e("          ", "--------"+profileURL);
        Picasso.with(activity).load(profileURL).placeholder(R.drawable.placeholder).into(((CircleImageView)activity.findViewById(R.id.mCircleImageView)));




    }
}
