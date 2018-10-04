package com.example.hirasawarei.sns_beta.search;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirasawarei.sns_beta.BottomNavigationViewHelper;
import com.example.hirasawarei.sns_beta.ProfileActivity;
import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.friends.FriendsActivity;
import com.example.hirasawarei.sns_beta.myPage.MyPageActivity;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;
import com.example.hirasawarei.sns_beta.uploadPosts.AddPhotoActivity;

public class SearchActivity extends AppCompatActivity {

    EditText etValue;
    ImageView btSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etValue = (EditText) findViewById(R.id.sEtSearch);
        btSearch = (ImageView) findViewById(R.id.sIvSearch);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_viewbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_timeline:
                        Intent intent1 = new Intent(SearchActivity.this, TimeLineActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        break;
                    case R.id.ic_add:
                        Intent intent2 = new Intent(SearchActivity.this, AddPhotoActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_friends:
                        Intent intent4 = new Intent(SearchActivity.this, FriendsActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_my_page:
                        Intent intent5 = new Intent(SearchActivity.this, MyPageActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchUserId searchUserId = new SearchUserId(SearchActivity.this);
                searchUserId.execute(etValue.getText().toString());

            }
        });
    }
}
