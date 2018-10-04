package com.example.hirasawarei.sns_beta.friends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hirasawarei.sns_beta.BottomNavigationViewHelper;
import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.myPage.MyPageActivity;
import com.example.hirasawarei.sns_beta.search.SearchActivity;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;
import com.example.hirasawarei.sns_beta.uploadPosts.AddPhotoActivity;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_viewbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_timeline:
                        Intent intent1 = new Intent(FriendsActivity.this, TimeLineActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(FriendsActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_add:
                        Intent intent3 = new Intent(FriendsActivity.this, AddPhotoActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_friends:
                        break;
                    case R.id.ic_my_page:
                        Intent intent5 = new Intent(FriendsActivity.this, MyPageActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }
}
