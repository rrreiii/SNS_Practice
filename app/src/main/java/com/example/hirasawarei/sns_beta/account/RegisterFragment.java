package com.example.hirasawarei.sns_beta.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hirasawarei.sns_beta.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hirasawarei on 09/08/17.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {

    View view;
    Activity activity;

    EditText etUserName;
    EditText etEmail;
    EditText etPassword;
    EditText etComment;
    EditText etSiteURL;
    Button btRegister;
    TextView tvMovetoLogin;
    TextView tvOrizuru;
    ImageButton imageButton;

    Bitmap profileImage;
    byte[] imageByteArray;
    String base64ImageString;

    String name;
    String email;
    String password;
    String comment;
    String siteURL;
    String userId;
    String postedTime;
    String imageName;

    private final int CAMERA_REQUEST_CODE = 10;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register, container, false);
        activity = getActivity();

        etUserName = (EditText) view.findViewById(R.id.rEtUserName);
        etEmail = (EditText) view.findViewById(R.id.rEtEmail);
        etPassword = (EditText) view.findViewById(R.id.rEtPassword);
        etComment = (EditText) view.findViewById(R.id.rEtComment);
        etSiteURL = (EditText) view.findViewById(R.id.rEtURL);
        btRegister = (Button) view.findViewById(R.id.rBtRegister);
        tvMovetoLogin = (TextView) view.findViewById(R.id.rTvMoveToLogin);
        tvOrizuru = (TextView) view.findViewById(R.id.rTvOrizuru);
        imageButton = (ImageButton) view.findViewById(R.id.rImageButton);

        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "helvetica-normal.ttf");
        tvOrizuru.setTypeface(typeface);

        imageButton.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        tvMovetoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).setViewPager(0);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            profileImage = (Bitmap) data.getExtras().get("data");
            imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButton.setImageBitmap(profileImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rImageButton:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                break;

            case R.id.rBtRegister:
                name = etUserName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                comment = etComment.getText().toString();
                siteURL = etSiteURL.getText().toString();


                if (name == "" || email == "" || password == "" || profileImage == null) {
                    Toast.makeText(getActivity(), "please fill the forms with * mark", Toast.LENGTH_SHORT).show();
                } else {

                    if (comment == "") {
                        comment = "---";
                    }
                    if (siteURL == "") {
                        siteURL = "---";
                    }
                    String method = "Registration";
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    profileImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    imageByteArray = byteArrayOutputStream.toByteArray();
                    base64ImageString = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
                    postedTime = new SimpleDateFormat("yyyyMMdd__HHmmss", Locale.getDefault()).format(new Date());
                    imageName = "IMG_PROFILE" + name + "_" + postedTime + ".jpg";
                    AsyncAccountNetworkTask asyncAccountNetworkTask = new AsyncAccountNetworkTask(getActivity());
                    asyncAccountNetworkTask.execute(method, name, email, password, comment, siteURL, imageName, base64ImageString);
                }
                break;

            case R.id.rTvMoveToLogin:
                ((LoginActivity) getActivity()).setViewPager(0);
                break;

        }

    }
}
