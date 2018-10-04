package com.example.hirasawarei.sns_beta.account;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hirasawarei.sns_beta.R;

/**
 * Created by hirasawarei on 09/08/17.
 */

public class LoginFragment extends Fragment{

    EditText etEmail;
    EditText etPassword;
    Button btLogin;
    TextView tvMovetoRegister;
    TextView tvOrizuru;
    static View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = (EditText) view.findViewById(R.id.lEtEmail);
        etPassword = (EditText) view.findViewById(R.id.lEtPassword);
        btLogin = (Button) view.findViewById(R.id.lBtLogin);
        tvMovetoRegister = (TextView) view.findViewById(R.id.lTvMoveToRegister);

        tvOrizuru = (TextView) view.findViewById(R.id.lTvOrizuru);

        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "helvetica-normal.ttf");
        tvOrizuru.setTypeface(typeface);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String method = "Login";
                AsyncAccountNetworkTask asyncAccountNetworkTask = new AsyncAccountNetworkTask(getActivity());
                asyncAccountNetworkTask.execute(method,email,password);



            }
        });


        tvMovetoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity)getActivity()).setViewPager(1);

            }
        });

        return view;



    }


}
