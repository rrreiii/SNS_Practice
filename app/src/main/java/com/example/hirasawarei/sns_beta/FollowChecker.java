package com.example.hirasawarei.sns_beta;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by hirasawarei on 2017/09/29.
 */

public class FollowChecker extends AsyncTask<String, Void, String> {


    Activity activity;
    String idFollowing;
    String idFollowed;


    public FollowChecker(Activity activity){
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... params) {
        idFollowing = params[0];
        idFollowed = params[1];
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
