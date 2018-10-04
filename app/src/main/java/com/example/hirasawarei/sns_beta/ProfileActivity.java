package com.example.hirasawarei.sns_beta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.hirasawarei.sns_beta.account.LoginActivity;
import com.example.hirasawarei.sns_beta.friends.*;
import com.example.hirasawarei.sns_beta.myPage.Downloader;
import com.example.hirasawarei.sns_beta.myPage.MyPageActivity;
import com.example.hirasawarei.sns_beta.search.SearchActivity;
import com.example.hirasawarei.sns_beta.search.SearchUserId;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;
import com.example.hirasawarei.sns_beta.uploadPosts.AddPhotoActivity;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    GridView gridView;
    String userId;
    TextView tvEdit;

    String idFollowing, idFollowed;

    final static String urlAddress = "http://reiman.php.xdomain.jp/SNS_Beta/GetPostsConnecter.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tvEdit= (TextView) findViewById(R.id.mBtEdit);
        tvEdit.setText("FOLLOW!!!");

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");

        tvEdit = (TextView) findViewById(R.id.mBtEdit);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                idFollowing= pref.getString("UserId", null);
                idFollowed = userId;
                DoFollow doFollow = new DoFollow(ProfileActivity.this);
                doFollow.execute("true",idFollowing,idFollowed);


            }
        });

        gridView = (GridView) findViewById(R.id.gridView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Intent i = new Intent(ProfileActivity.this, ProfileActivity.class);
                        i.putExtra("UserId", userId);
                        startActivity(i);
                        finish();
                    }
                }, 2000);
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_viewbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_timeline:
                        Intent intent1 = new Intent(ProfileActivity.this, TimeLineActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(ProfileActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_add:
                        Intent intent3 = new Intent(ProfileActivity.this, AddPhotoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_friends:
                        Intent intent4 = new Intent(ProfileActivity.this, FriendsActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_my_page:
                        Intent intent5 = new Intent(ProfileActivity.this, MyPageActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });


        new AsyncGetAccountInformation(this,userId).execute("myPage");
        new Downloader(ProfileActivity.this, urlAddress, gridView,userId).execute();


    }
}
