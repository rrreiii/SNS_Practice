package com.example.hirasawarei.sns_beta.myPage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hirasawarei on 18/08/17.
 */

public class DataParser extends AsyncTask<Void, Void, String>{

    Context ctx;
    GridView gridView;
    String jsonData;
    String id,userName, caption,postedTime,imageURL,theNumberOfGood;

    ArrayList<Post>  posts = new ArrayList<>();
    ProgressBar progressBar;

    public DataParser(Context ctx, String jsonData, GridView gridView) {
        this.ctx = ctx;
        this.jsonData = jsonData;
        this.gridView = gridView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.parseData();
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != "success" ) {
            Toast.makeText(ctx, "no photo", Toast.LENGTH_SHORT).show();
        } else {
            //BIND DATA TO GRIDVIEW

            GridViewAdapter adapter = new GridViewAdapter(ctx, posts);
            gridView.setAdapter(adapter);

        }
    }

    private String parseData()
    {
        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject;

            posts.clear();
            Post post = new Post();

            for(int i = 0; i < jsonArray.length(); i++ ){
                jsonObject = jsonArray.getJSONObject(i);
                id = jsonObject.getString("id");
                userName = jsonObject.getString("user_id");
                caption = jsonObject.getString("caption");
                postedTime = jsonObject.getString("posted_time");
                imageURL = jsonObject.getString("image_url");

                post = new Post();
                post.setId(Integer.parseInt(id));
                post.setUserName(userName);
                post.setCaption(caption);
                post.setPostedTime(postedTime);
                post.setImageURL(imageURL);
                posts.add(post);
            }

            return "success";

        } catch (JSONException e) {
            e.printStackTrace();
            return jsonData;
        }



    }
}
