package com.example.hirasawarei.sns_beta.myPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.hirasawarei.sns_beta.AsyncGetAccountInformation;
import com.example.hirasawarei.sns_beta.account.LoginActivity;
import com.example.hirasawarei.sns_beta.friends.FriendsActivity;
import com.example.hirasawarei.sns_beta.search.SearchActivity;
import com.example.hirasawarei.sns_beta.uploadPosts.AddPhotoActivity;
import com.example.hirasawarei.sns_beta.BottomNavigationViewHelper;
import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;

public class MyPageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    GridView gridView;
    String userId;
    TextView btEdit;


    final static String urlAddress = "http://reiman.php.xdomain.jp/SNS_Beta/GetPostsConnecter.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        btEdit = (TextView) findViewById(R.id.mBtEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userId = pref.getString("UserId", null);

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
                        Intent i = new Intent(MyPageActivity.this, MyPageActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 2000);
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_viewbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_timeline:
                        Intent intent1 = new Intent(MyPageActivity.this, TimeLineActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(MyPageActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_add:
                        Intent intent3 = new Intent(MyPageActivity.this, AddPhotoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_friends:
                        Intent intent5 = new Intent(MyPageActivity.this, FriendsActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.ic_my_page:
                        break;
                }
                return false;
            }
        });


        new AsyncGetAccountInformation(this,userId).execute("myPage");
        new Downloader(MyPageActivity.this, urlAddress, gridView,userId).execute();


    }
}

