package com.example.hirasawarei.sns_beta.timeLine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hirasawarei.sns_beta.BottomNavigationViewHelper;
import com.example.hirasawarei.sns_beta.friends.FriendsActivity;
import com.example.hirasawarei.sns_beta.myPage.MyPageActivity;
import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.search.SearchActivity;
import com.example.hirasawarei.sns_beta.uploadPosts.AddPhotoActivity;

public class TimeLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_viewbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_timeline:
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(TimeLineActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_add:
                        Intent intent3 = new Intent(TimeLineActivity.this, AddPhotoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_friends:
                        Intent intent4 = new Intent(TimeLineActivity.this, FriendsActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_my_page:
                        Intent intent5 = new Intent(TimeLineActivity.this, MyPageActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }

}
