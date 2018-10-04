package com.example.hirasawarei.sns_beta.uploadPosts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.account.AsyncAccountNetworkTask;
import com.example.hirasawarei.sns_beta.BottomNavigationViewHelper;
import com.example.hirasawarei.sns_beta.R;
import com.example.hirasawarei.sns_beta.friends.FriendsActivity;
import com.example.hirasawarei.sns_beta.myPage.MyPageActivity;
import com.example.hirasawarei.sns_beta.search.SearchActivity;
import com.example.hirasawarei.sns_beta.timeLine.TimeLineActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPhotoActivity extends AppCompatActivity implements View.OnClickListener {


    EditText etCaption;
    ImageView ivCamera;
    ImageView ivGallery;
    ImageView ivImage;
    Button btShare;

    private String caption;
    private String postedTime, image_name;
    private String userName, userId;
    private Bitmap imageBitmap;
    private Uri photoUri;
    private byte [] imageByteArray;
    private String encodedImageString;

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        ivImage = (ImageView) findViewById(R.id.ivImage);
        btShare = (Button) findViewById(R.id.tBtPost);
        etCaption = (EditText) findViewById(R.id.etCaption);
        ivCamera = (ImageView) findViewById(R.id.aIvCamera);
        ivGallery = (ImageView) findViewById(R.id.aIvGallery);

        ivImage.setOnClickListener(this);
        btShare.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        ivGallery.setOnClickListener(this);

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userName = pref.getString("UserName", null);
        userId = pref.getString("UserId", null);




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_viewbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_timeline:
                        Intent intent1 = new Intent(AddPhotoActivity.this, TimeLineActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(AddPhotoActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_add:
                        break;
                    case R.id.ic_friends:
                        Intent intent4 = new Intent(AddPhotoActivity.this, FriendsActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_my_page:
                        Intent intent5 = new Intent(AddPhotoActivity.this, MyPageActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });


    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.aIvCamera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);
                break;

            case R.id.aIvGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,  GALLERY_REQUEST_CODE);
                break;

            case R.id.tBtPost:
                if(imageBitmap == null){
                    Toast.makeText(this, "please pick a image from gallery", Toast.LENGTH_SHORT).show();
                    break;
                }
                caption = etCaption.getText().toString();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 5, byteArrayOutputStream);
                imageByteArray = byteArrayOutputStream.toByteArray();
                encodedImageString= Base64.encodeToString(imageByteArray,Base64.DEFAULT);
                postedTime = new SimpleDateFormat("yyyyMMdd__HHmmss", Locale.getDefault()).format(new Date());
                image_name = "IMG_" +userId+"_"+ postedTime + ".jpg";

                new AsyncPostUploader(this).execute(encodedImageString,userId,userName,caption,postedTime,image_name);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            photoUri= data.getData();

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                ivImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivImage.setImageBitmap(imageBitmap);
                //二回目だとおちる。
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){

                imageBitmap = (Bitmap) data.getExtras().get("data");
                ivImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivImage.setImageBitmap(imageBitmap);



        }else{
            Toast.makeText(this, "failed to get photo", Toast.LENGTH_SHORT).show();

        }
    }
}


