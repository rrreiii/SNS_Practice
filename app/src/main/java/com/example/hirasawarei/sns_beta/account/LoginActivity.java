package com.example.hirasawarei.sns_beta.account;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity";
    private SectionPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new LoginFragment());
        sectionPagerAdapter.addFragment(new RegisterFragment());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("LOGIN");
        tabLayout.getTabAt(1).setText("REGISTER");

    }



    public void setViewPager(int fragmentnumber){
        viewPager.setCurrentItem(fragmentnumber);
    }


    public void moveInto(View view) {
        Intent intent = new Intent(LoginActivity.this, TimeLineActivity.class);
        startActivity(intent);
    }
}
